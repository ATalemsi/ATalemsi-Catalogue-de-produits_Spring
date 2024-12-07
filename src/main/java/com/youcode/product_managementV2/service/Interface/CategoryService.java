package com.youcode.product_managementV2.service.Interface;

import com.youcode.product_managementV2.dto.request.CategoryRequestDto;
import com.youcode.product_managementV2.dto.response.CategoryResponseDto;
import com.youcode.product_managementV2.dto.response.ProductResponseDto;
import com.youcode.product_managementV2.dto.update.CategoryUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface CategoryService {
    Page<CategoryResponseDto> getAllCategories(int page, int size);
    Page<CategoryResponseDto> searchCategoriesByName(String name, int page, int size, String sortBy, String sortDir);
    Page<ProductResponseDto> getProductsInCategory(Long categoryId, int page, int size, String sortBy, String sortDir);
    void addCategory(CategoryRequestDto categoryDto);
    void updateCategory(Long id, CategoryUpdateDto categoryDto);
    void deleteCategory(Long id);


}
