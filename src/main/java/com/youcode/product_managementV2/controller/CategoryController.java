package com.youcode.product_managementV2.controller;

import com.youcode.product_managementV2.dto.request.CategoryRequestDto;
import com.youcode.product_managementV2.dto.response.CategoryResponseDto;
import com.youcode.product_managementV2.dto.response.ProductResponseDto;
import com.youcode.product_managementV2.dto.update.CategoryUpdateDto;
import com.youcode.product_managementV2.service.Interface.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryController {



    @Autowired
    private CategoryService categoryService;

    @GetMapping("/user/categories")
    public ResponseEntity<Page<CategoryResponseDto>> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching all categories with page: {} and size: {}", page, size);
        Page<CategoryResponseDto> categories = categoryService.getAllCategories(page, size);
        log.info("Fetched {} categories", categories.getContent().size());
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/user/categories/search")
    public ResponseEntity<Page<CategoryResponseDto>> searchCategoriesByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {
        log.info("Searching categories by name: {}, page: {}, size: {}, sortBy: {}, sortDir: {}",
                name, page, size, sortBy, sortDir);
        Page<CategoryResponseDto> categories = categoryService.searchCategoriesByName(name, page, size, sortBy, sortDir);
        if (categories.isEmpty()) {
            log.warn("No categories found for name: {}", name);
            return ResponseEntity.notFound().build();
        }
        log.info("Found {} categories for name: {}", categories.getContent().size(), name);
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/user/categories/{id}/products")
    public ResponseEntity<Page<ProductResponseDto>> getProductsInCategory(
            @PathVariable Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "designation") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        log.info("Fetching products for category ID: {}, page: {}, size: {}, sortBy: {}, sortDir: {}",
                id, page, size, sortBy, sortDir);

        Page<ProductResponseDto> products = categoryService.getProductsInCategory(id, page, size, sortBy, sortDir);

        if (products.isEmpty()) {
            log.warn("No products found for category ID: {}", id);
            return ResponseEntity.ok(Page.empty());
        }

        log.info("Found {} products for category ID: {}", products.getContent().size(), id);
        return ResponseEntity.ok(products);
    }
    @PostMapping("/admin/categories")
    public ResponseEntity<String> addCategory(@RequestBody @Valid CategoryRequestDto categoryDto) {
        log.info("Adding a new category: {}", categoryDto);
        categoryService.addCategory(categoryDto);
        log.info("Category added successfully: {}", categoryDto.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body("Category added successfully.");
    }

    @PutMapping("/admin/categories/{id}")
    public ResponseEntity<String> updateCategory(
            @PathVariable Long id,
            @RequestBody  CategoryUpdateDto categoryDto
    ) {
        log.info("Updating category with ID: {}", id);
        try {
            categoryService.updateCategory(id, categoryDto);
            log.info("Category updated successfully: {}", categoryDto.getName());
            return ResponseEntity.ok("Category updated successfully.");
        } catch (RuntimeException e) {
            log.error("Error updating category with ID: {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/admin/categories/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        log.info("Deleting category with ID: {}", id);
        try {
            categoryService.deleteCategory(id);
            log.info("Category with ID: {} deleted successfully.", id);
            return ResponseEntity.ok("Category deleted successfully.");
        } catch (RuntimeException e) {
            log.error("Error deleting category with ID: {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
