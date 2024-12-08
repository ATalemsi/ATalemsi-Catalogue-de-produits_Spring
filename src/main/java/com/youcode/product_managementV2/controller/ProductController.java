package com.youcode.product_managementV2.controller;

import com.youcode.product_managementV2.dto.request.ProductRequestDto;
import com.youcode.product_managementV2.dto.response.ProductResponseDto;
import com.youcode.product_managementV2.dto.update.ProductUpdateDto;
import com.youcode.product_managementV2.service.Interface.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("/user/products")
    public ResponseEntity<Page<ProductResponseDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "designation") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        log.info("Fetching products with pagination, page: {}, size: {}, sortBy: {}, sortDir: {}", page, size, sortBy, sortDir);
        Page<ProductResponseDto> products = productService.getAllProducts(page, size, sortBy, sortDir);

        if (products.isEmpty()) {
            log.warn("No products found");
            return ResponseEntity.notFound().build();
        }

        log.info("Found {} products", products.getTotalElements());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/products/search")
    public ResponseEntity<Page<ProductResponseDto>> searchProductsByDesignation(
            @RequestParam String designation,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "designation") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        log.info("Searching products by designation: {}, page: {}, size: {}, sortBy: {}, sortDir: {}", designation, page, size, sortBy, sortDir);
        Page<ProductResponseDto> products = productService.searchProductsByDesignation(designation, page, size, sortBy, sortDir);

        if (products.isEmpty()) {
            log.warn("No products found for designation: {}", designation);
            return ResponseEntity.notFound().build();
        }

        log.info("Found {} products for designation: {}", products.getTotalElements(), designation);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/user/products/category/{categoryId}")
    public ResponseEntity<Page<ProductResponseDto>> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Fetching products by category ID: {}, page: {}, size: {}", categoryId, page, size);
        Page<ProductResponseDto> products = productService.getProductsByCategory(categoryId, page, size);

        if (products.isEmpty()) {
            log.warn("No products found for category ID: {}", categoryId);
            return ResponseEntity.notFound().build();
        }

        log.info("Found {} products for category ID: {}", products.getTotalElements(), categoryId);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/user/products/category/{categoryId}/filter")
    public ResponseEntity<Page<ProductResponseDto>> filterProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "designation") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        log.info("Filtering products by category ID: {}, page: {}, size: {}, sortBy: {}, sortDir: {}", categoryId, page, size, sortBy, sortDir);
        Page<ProductResponseDto> products = productService.filterProductsByCategory(categoryId, page, size, sortBy, sortDir);

        if (products.isEmpty()) {
            log.warn("No products found for category ID: {}", categoryId);
            return ResponseEntity.notFound().build();
        }

        log.info("Found {} filtered products for category ID: {}", products.getTotalElements(), categoryId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/admin/products")
    public ResponseEntity<Void> addProduct(@RequestBody ProductRequestDto productRequestDto) {
        log.info("Adding new product: {}", productRequestDto);
        productService.addProduct(productRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/admin/products/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDto productUpdateDto) {
        log.info("Updating product with ID: {}", id);
        productService.updateProduct(id, productUpdateDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/admin/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        log.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
