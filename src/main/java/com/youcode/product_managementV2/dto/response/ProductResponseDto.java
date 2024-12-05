package com.youcode.product_managementV2.dto.response;

import com.youcode.product_managementV2.Entity.Category;
import com.youcode.product_managementV2.Entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
    private Long id;

    private String designation;

    private Double prix;

    private Integer quantite;

    private Category category;

    private User user;

}
