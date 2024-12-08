package com.youcode.product_managementV2.ProductTest;

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
import com.youcode.product_managementV2.service.Impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private HttpServletRequest httpServletRequest;

    @Mock
    private HttpSession httpSession;

    @Mock
    private Page<Product> productPage;

    private ProductRequestDto productRequestDto;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create and mock HttpServletRequest
        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.getSession().setAttribute("user_id", 1L); // Mock user_id in session

        // Set request attributes for the current thread
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));

        // Setting up mock Category and User
        Category category = Category.builder().id(1L).name("Category 1").build();
        User user = User.builder().id(1L).login("User_1").build();

        // Mock the userRepository to return the mock user when findById is called
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Setting up ProductRequestDto for testAddProduct
        productRequestDto = ProductRequestDto.builder()
                .designation("Product 1")
                .prix(100.0)
                .quantite(10)
                .categoryId(1L)
                .build();

        // Setting up Product for test methods
        product = Product.builder()
                .id(1L)
                .designation("Product 1")
                .prix(100.0)
                .quantite(10)
                .category(category)
                .user(user)
                .build();

        // Setting up ProductPage for pagination tests
        productPage = new PageImpl<>(List.of(product));

        // Mocking productMapper to return the mock product when toEntity is called
        when(productMapper.toEntity(any(ProductRequestDto.class))).thenReturn(product);
    }

    @Test
    void testAddProduct() {
        // Arrange
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(product.getCategory()));
        when(productMapper.toEntity(any(ProductRequestDto.class))).thenReturn(product); // Mocking the mapper
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        productService.addProduct(productRequestDto);  // No return value

        // Assert
        verify(productRepository, times(1)).save(any(Product.class));
        verify(categoryRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);  // Ensure userRepository.findById() is called
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10, Sort.by("designation").ascending());
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // Act
        Page<ProductResponseDto> result = productService.getAllProducts(0, 10, "designation", "asc");

        // Assert
        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void testSearchProductsByDesignation() {
        // Arrange
        String designation = "Product";
        Pageable pageable = PageRequest.of(0, 10, Sort.by("designation").ascending());
        when(productRepository.findByDesignationContainingIgnoreCase(designation, pageable)).thenReturn(productPage);

        // Act
        Page<ProductResponseDto> result = productService.searchProductsByDesignation(designation, 0, 10, "designation", "asc");

        // Assert
        assertNotNull(result);
        assertFalse(result.getContent().isEmpty());
        verify(productRepository, times(1)).findByDesignationContainingIgnoreCase(designation, pageable);
    }

    @Test
    void testUpdateProduct() {
        // Arrange: Set up the mock product and the update details
        Long productId = 1L;
        Product updatedProduct = Product.builder()
                .id(productId)
                .designation("Updated Product")
                .prix(120.0)
                .quantite(15)
                .build();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        // Act: Call the updateProduct method
        productService.updateProduct(productId, ProductUpdateDto.builder()
                .prix(125.0)
                .designation(updatedProduct.getDesignation())
                .quantite(updatedProduct.getQuantite())
                .build());

        // Assert: Verify that the save method was called, and the product was updated correctly
        verify(productRepository, times(1)).save(any(Product.class));
        assertEquals("Updated Product", updatedProduct.getDesignation());
        assertEquals(120.0, updatedProduct.getPrix());
        assertEquals(15, updatedProduct.getQuantite());
    }

    @Test
    void testDeleteProduct() {
        // Arrange: Set up mock for product deletion
        Long productId = 1L;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        doNothing().when(productRepository).deleteById(productId);

        // Act: Call the deleteProduct method
        productService.deleteProduct(productId);

        // Assert: Verify that the deleteById method was called
        verify(productRepository, times(1)).deleteById(productId);
    }
}
