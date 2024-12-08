package com.youcode.product_managementV2.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryResponseDto {
    private String name;
    private String description;

}
