package com.youcode.product_managementV2.service.Interface;

import com.youcode.product_managementV2.dto.request.ProductRequestDto;
import com.youcode.product_managementV2.dto.response.ProductResponseDto;
import com.youcode.product_managementV2.dto.update.ProductUpdateDto;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductResponseDto> getAllProducts(int page, int size, String sortBy, String sortDir);
    Page<ProductResponseDto> searchProductsByDesignation(String designation, int page, int size, String sortBy, String sortDir);
    Page<ProductResponseDto> getProductsByCategory(Long categoryId, int page, int size);
    Page<ProductResponseDto> filterProductsByCategory(Long categoryId, int page, int size, String sortBy, String sortDir);
    void addProduct(ProductRequestDto productRequestDto);
    void updateProduct(Long id, ProductUpdateDto productUpdateDto);
    void deleteProduct(Long id);

}
