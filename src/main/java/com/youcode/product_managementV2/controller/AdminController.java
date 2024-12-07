package com.youcode.product_managementV2.controller;

import com.youcode.product_managementV2.dto.response.UserResponseDto;
import com.youcode.product_managementV2.dto.update.UserRoleUpdateDto;
import com.youcode.product_managementV2.service.Interface.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> listAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<UserResponseDto> updateUserRole(
            @PathVariable Long id,
            @RequestBody @Valid UserRoleUpdateDto userRoleUpdateDto) {
        UserResponseDto updatedUser = userService.updateUserRole(id, userRoleUpdateDto);
        return ResponseEntity.ok(updatedUser);
    }

}
