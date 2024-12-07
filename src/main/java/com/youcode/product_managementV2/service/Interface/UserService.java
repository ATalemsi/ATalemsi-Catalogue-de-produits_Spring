package com.youcode.product_managementV2.service.Interface;

import com.youcode.product_managementV2.dto.request.UserRequestDto;
import com.youcode.product_managementV2.dto.response.UserResponseDto;
import com.youcode.product_managementV2.dto.update.UserRoleUpdateDto;

import java.util.List;

public interface UserService {
    UserResponseDto registerUser(UserRequestDto userRequestDTO);
    UserResponseDto login(UserRequestDto userRequestDTO);
    UserResponseDto getUserById(Long id);
    List<UserResponseDto> getAllUsers();
    UserResponseDto updateUserRole(Long userId, UserRoleUpdateDto userRoleUpdateDto);

}
