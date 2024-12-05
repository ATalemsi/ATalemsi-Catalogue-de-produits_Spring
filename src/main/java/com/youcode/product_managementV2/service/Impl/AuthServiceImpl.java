package com.youcode.product_managementV2.service.Impl;

import com.youcode.product_managementV2.Entity.User;
import com.youcode.product_managementV2.Entity.UserRole;
import com.youcode.product_managementV2.dto.request.UserRequestDto;
import com.youcode.product_managementV2.dto.response.UserResponseDto;
import com.youcode.product_managementV2.mapper.UserMapper;
import com.youcode.product_managementV2.repository.UserRepository;
import com.youcode.product_managementV2.repository.UserRoleRepository;
import com.youcode.product_managementV2.service.Interface.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final HttpSession httpSession;

    public AuthServiceImpl(UserRepository userRepository, UserRoleRepository userRoleRepository,
                           PasswordEncoder passwordEncoder, UserMapper userMapper, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, HttpSession httpSession) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.httpSession = httpSession;
    }
    @Override
    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);

        Optional<UserRole> role = userRoleRepository.findByRoleName(userRequestDto.getRoleName());
        if (role.isEmpty()) {
            throw new IllegalArgumentException("Invalid role: " + userRequestDto.getRoleName());
        }
        user.setRole(role.get());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setActive(true);

        userRepository.save(user);

        return userMapper.toResponseDto(user);
    }

    @Override
    public UserResponseDto login(UserRequestDto userRequestDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userRequestDto.getLogin(), userRequestDto.getPassword());


        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        UserDetails userDetails = userDetailsService.loadUserByUsername(userRequestDto.getLogin());

        User user = userRepository.findByLogin(userRequestDto.getLogin())
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + userRequestDto.getLogin()));


        String sessionId = httpSession.getId();

        UserResponseDto userResponseDto = userMapper.toResponseDto(user);
        userResponseDto.setSessionId(sessionId);

        return userResponseDto;
    }

    @Override
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        return userMapper.toResponseDto(user);
    }
}
