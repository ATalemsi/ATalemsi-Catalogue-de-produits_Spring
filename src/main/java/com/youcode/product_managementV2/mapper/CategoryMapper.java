package com.youcode.product_managementV2.mapper;


import com.youcode.product_managementV2.Entity.Category;
import com.youcode.product_managementV2.dto.request.CategoryRequestDto;
import com.youcode.product_managementV2.dto.response.CategoryResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryResponseDto toDTO(Category category);
    Category toEntity(CategoryRequestDto categoryRequestDTO);
}
