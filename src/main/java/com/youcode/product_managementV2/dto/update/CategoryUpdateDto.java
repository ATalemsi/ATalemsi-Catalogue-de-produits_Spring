package com.youcode.product_managementV2.dto.update;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryUpdateDto {

    private String name;


    private String description;
}
