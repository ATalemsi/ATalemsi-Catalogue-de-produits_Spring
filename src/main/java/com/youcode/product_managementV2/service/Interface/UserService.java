package com.youcode.product_managementV2.service.Interface;

import com.youcode.product_managementV2.dto.request.UserRequestDto;
import com.youcode.product_managementV2.Entity.User;

public interface UserService {
    User registerUser(UserRequestDto userRequestDTO);

}
