package com.youcode.product_managementV2.dto.response;

import com.youcode.product_managementV2.Entity.Category;
import com.youcode.product_managementV2.Entity.Product;
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

    private String categoryName;

    private User user;


    public static ProductResponseDto fromProduct(Product product) {
        return ProductResponseDto.builder()
                .id(product.getId())
                .designation(product.getDesignation())
                .prix(product.getPrix())
                .quantite(product.getQuantite())
                .categoryName(product.getCategory() != null ? product.getCategory().getName() : null)
                .build();
    }
}
