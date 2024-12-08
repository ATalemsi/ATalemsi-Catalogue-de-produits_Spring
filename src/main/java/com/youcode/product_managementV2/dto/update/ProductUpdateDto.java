package com.youcode.product_managementV2.dto.update;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductUpdateDto {

    private String designation;

    private Double prix;

    private Integer quantite;
}
