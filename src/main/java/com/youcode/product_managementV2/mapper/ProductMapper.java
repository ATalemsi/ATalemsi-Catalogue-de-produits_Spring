package com.youcode.product_managementV2.mapper;

import com.youcode.product_managementV2.Entity.Category;
import com.youcode.product_managementV2.Entity.Product;
import com.youcode.product_managementV2.dto.request.CategoryRequestDto;
import com.youcode.product_managementV2.dto.request.ProductRequestDto;
import com.youcode.product_managementV2.dto.response.CategoryResponseDto;
import com.youcode.product_managementV2.dto.response.ProductResponseDto;
import com.youcode.product_managementV2.dto.update.ProductUpdateDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toEntity(ProductRequestDto productRequestDto);

    ProductResponseDto toDto(Product product);
    void updateProductFromDto(ProductUpdateDto ProductRequestDto, @MappingTarget Product product);
}

