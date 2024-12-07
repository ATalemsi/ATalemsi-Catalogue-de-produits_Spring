package com.youcode.product_managementV2.service.Impl;

import com.youcode.product_managementV2.Entity.Category;
import com.youcode.product_managementV2.dto.request.CategoryRequestDto;
import com.youcode.product_managementV2.dto.response.CategoryResponseDto;
import com.youcode.product_managementV2.dto.response.ProductResponseDto;
import com.youcode.product_managementV2.dto.update.CategoryUpdateDto;
import com.youcode.product_managementV2.mapper.CategoryMapper;
import com.youcode.product_managementV2.repository.CategoryRepository;
import com.youcode.product_managementV2.repository.ProductRepository;
import com.youcode.product_managementV2.service.Interface.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public Page<CategoryResponseDto> getAllCategories(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toDTO); // Use mapper for conversion
    }

    @Override
    public Page<CategoryResponseDto> searchCategoriesByName(String name, int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(
                page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );
        return categoryRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(categoryMapper::toDTO);
    }

    @Override
    public Page<ProductResponseDto> getProductsInCategory(Long categoryId, int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(
                page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(ProductResponseDto::fromProduct);
    }

    @Override
    public void addCategory(CategoryRequestDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto); // Use mapper to map DTO to entity
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long id, CategoryUpdateDto categoryDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Map updated fields using the mapper (or manually set them if partial updates)
        categoryMapper.updateCategoryFromDto(categoryDto, category); // Assuming a method in the mapper

        categoryRepository.save(category);

    }
    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
