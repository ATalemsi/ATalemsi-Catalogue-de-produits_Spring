package com.youcode.product_managementV2.dto.update;


import lombok.Data;

@Data
public class ProductUpdateDto {

    private String designation;

    private Double prix;

    private Integer quantite;
}
