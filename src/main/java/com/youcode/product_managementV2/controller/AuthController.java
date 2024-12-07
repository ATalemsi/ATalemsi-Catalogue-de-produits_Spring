package com.youcode.product_managementV2.controller;

import com.youcode.product_managementV2.dto.request.UserRequestDto;
import com.youcode.product_managementV2.dto.response.UserResponseDto;
import com.youcode.product_managementV2.service.Impl.AuthServiceImpl;
import com.youcode.product_managementV2.service.Interface.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService authService;

    public AuthController(UserService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto responseDto = authService.registerUser(userRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody  UserRequestDto loginRequest, HttpServletRequest request) {
        try {
            UserResponseDto response = authService.login(loginRequest);

            log.info("User logged in successfully with sessionId: {}", response.getSessionId());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Login failed for user: {}. Error: {}", loginRequest.getLogin(), e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long id) {
        UserResponseDto responseDto = authService.getUserById(id);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        if (request.getSession(false) == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session found.");
        }

        SecurityContextHolder.clearContext();
        request.getSession().invalidate();

        log.info("User logged out successfully.");
        return ResponseEntity.ok("Successfully logged out");
    }
}
