package com.youcode.product_managementV2.AuthTest;

import com.youcode.product_managementV2.Entity.User;
import com.youcode.product_managementV2.Entity.UserRole;
import com.youcode.product_managementV2.dto.request.UserRequestDto;
import com.youcode.product_managementV2.dto.response.UserResponseDto;
import com.youcode.product_managementV2.dto.update.UserRoleUpdateDto;
import com.youcode.product_managementV2.mapper.UserMapper;
import com.youcode.product_managementV2.repository.UserRepository;
import com.youcode.product_managementV2.repository.UserRoleRepository;
import com.youcode.product_managementV2.service.Impl.AuthServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserRoleRepository userRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private HttpSession httpSession;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    @Test
    void testRegisterUser() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .login("user1")
                .password("password")
                .roleName("USER")
                .build();

        User user = User.builder()
                .login("user1")
                .password("password")
                .active(true)
                .build();

        UserRole role = UserRole.builder()
                .roleName("USER")
                .build();

        UserResponseDto responseDto = UserResponseDto.builder().build();

        when(userMapper.toEntity(userRequestDto)).thenReturn(user);
        when(userRoleRepository.findByRoleName("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);

        UserResponseDto result = authService.registerUser(userRequestDto);

        assertNotNull(result);
        verify(userRepository).save(user);
    }

    @Test
    void testLogin() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .login("user1")
                .password("password")
                .build();

        User user = User.builder()
                .login("user1")
                .password("encodedPassword")
                .build();

        UserResponseDto responseDto = UserResponseDto.builder().build();

        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(userRepository.findByLogin("user1")).thenReturn(Optional.of(user));
        when(userDetailsService.loadUserByUsername("user1")).thenReturn(null);
        when(httpSession.getId()).thenReturn("session123");
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);

        SecurityContextHolder.setContext(securityContext);

        UserResponseDto result = authService.login(userRequestDto);

        assertNotNull(result);
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(httpSession).setAttribute(eq("user_id"), any());
    }

    @Test
    void testGetUserById() {
        User user = User.builder()
                .id(1L)
                .login("user1")
                .build();

        UserResponseDto responseDto = UserResponseDto.builder().build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);

        UserResponseDto result = authService.getUserById(1L);

        assertNotNull(result);
        verify(userRepository).findById(1L);
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(
                User.builder().login("user1").build(),
                User.builder().login("user2").build()
        );

        List<UserResponseDto> responseDtos = List.of(
                UserResponseDto.builder().build(),
                UserResponseDto.builder().build()
        );

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toResponseDto(any())).thenReturn(UserResponseDto.builder().build());

        List<UserResponseDto> result = authService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void testUpdateUserRole() {
        User user = User.builder()
                .id(1L)
                .login("user1")
                .build();

        UserRole role = UserRole.builder()
                .id(1L)
                .roleName("ROLE_ADMIN")
                .build();

        UserRoleUpdateDto userRoleUpdateDto = UserRoleUpdateDto.builder()
                .roleId(1L)
                .build();

        UserResponseDto responseDto = UserResponseDto.builder().build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(role));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(responseDto);

        UserResponseDto result = authService.updateUserRole(1L, userRoleUpdateDto);

        assertNotNull(result);
        verify(userRepository).save(user);
    }
}
