package com.youcode.product_managementV2.service.Interface;

import com.youcode.product_managementV2.dto.request.UserRequestDto;
import com.youcode.product_managementV2.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto registerUser(UserRequestDto userRequestDTO);
    UserResponseDto login(UserRequestDto userRequestDTO);
    UserResponseDto getUserById(Long id);

}
