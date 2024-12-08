package com.youcode.product_managementV2.dto.request;

import com.youcode.product_managementV2.Entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequestDto {

    @NotBlank(message = "designation should be not blank")
    @NotNull(message = "designation should be not null")
    private String designation;

    @NotNull(message = "Le prix unitaire est obligatoire.")
    @Positive(message = "Le prix unitaire doit être un nombre positif.")
    private Double prix;

    @NotNull(message = "La quantité vendue est obligatoire.")
    @Positive(message = "La quantité vendue doit être un nombre positif.")
    private Integer quantite;


    @NotNull(message = "categorie not be null")
    private Long categoryId;
}
