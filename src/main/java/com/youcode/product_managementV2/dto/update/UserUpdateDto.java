package com.youcode.product_managementV2.dto.update;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserUpdateDto {

    private String login;


    private String password;


    private Boolean active;


    private Long roleId;
}
