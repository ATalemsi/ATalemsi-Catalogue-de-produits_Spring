package com.youcode.product_managementV2.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class UserRequestDto {
    @NotEmpty(message = "Login cannot be empty")
    @Size(min = 5, max = 50, message = "Login must be between 5 and 50 characters")
    private String login;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotNull(message = "Active status cannot be null")
    private Boolean active;

    @NotNull(message = "Roles cannot be null")
    private String roleName; // Ensure this is present
}