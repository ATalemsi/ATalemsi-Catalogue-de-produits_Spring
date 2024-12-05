package com.youcode.product_managementV2.mapper;

import com.youcode.product_managementV2.Entity.Product;
import com.youcode.product_managementV2.dto.request.ProductRequestDto;
import com.youcode.product_managementV2.dto.response.ProductResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponseDto toEntity(ProductRequestDto productRequestDto);

    ProductRequestDto toDto(Product product);
}

