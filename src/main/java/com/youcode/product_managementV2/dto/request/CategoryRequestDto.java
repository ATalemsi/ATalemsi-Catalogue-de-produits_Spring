package com.youcode.product_managementV2.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequestDto {

    @NotBlank(message = "name should be not blank")
    @NotNull(message = "name should be not null")
    private String name;

    @NotBlank(message = "description should be not blank")
    @NotNull(message = "description should be not null")
    private String description;
}
