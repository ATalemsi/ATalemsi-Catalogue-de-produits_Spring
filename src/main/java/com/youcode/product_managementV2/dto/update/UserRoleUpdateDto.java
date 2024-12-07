package com.youcode.product_managementV2.dto.update;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRoleUpdateDto {

    @NotNull(message = "Role ID is required")
    private Long roleId;
}
