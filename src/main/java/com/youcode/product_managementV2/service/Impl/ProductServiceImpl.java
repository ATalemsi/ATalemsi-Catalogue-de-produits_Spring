package com.youcode.product_managementV2.service.Impl;

import com.youcode.product_managementV2.Entity.Category;
import com.youcode.product_managementV2.Entity.Product;
import com.youcode.product_managementV2.Entity.User;
import com.youcode.product_managementV2.dto.request.ProductRequestDto;
import com.youcode.product_managementV2.dto.response.ProductResponseDto;
import com.youcode.product_managementV2.dto.update.ProductUpdateDto;
import com.youcode.product_managementV2.mapper.ProductMapper;
import com.youcode.product_managementV2.repository.CategoryRepository;
import com.youcode.product_managementV2.repository.ProductRepository;
import com.youcode.product_managementV2.repository.UserRepository;
import com.youcode.product_managementV2.service.Interface.CategoryService;
import com.youcode.product_managementV2.service.Interface.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<ProductResponseDto> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(
                page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDto> searchProductsByDesignation(String designation, int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(
                page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );
        return productRepository.findByDesignationContainingIgnoreCase(designation, pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDto> getProductsByCategory(Long categoryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(productMapper::toDto);
    }

    @Override
    public Page<ProductResponseDto> filterProductsByCategory(Long categoryId, int page, int size, String sortBy, String sortDir) {
        Pageable pageable = PageRequest.of(
                page, size,
                sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()
        );
        return productRepository.findByCategoryId(categoryId, pageable)
                .map(productMapper::toDto);
    }

    @Override
    public void addProduct(ProductRequestDto productRequestDto) {
        Category category = categoryRepository.findById(productRequestDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Product product = productMapper.toEntity(productRequestDto);
        product.setCategory(category);

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Long userId = (Long) request.getSession().getAttribute("user_id");  // Assuming the user_id is stored in the session

        if (userId == null) {
            throw new RuntimeException("User not found in session");
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        product.setUser(user);
        productRepository.save(product);
    }

    @Override
    public void updateProduct(Long id, ProductUpdateDto productUpdateDto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));


        productMapper.updateProductFromDto(productUpdateDto , product);

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
