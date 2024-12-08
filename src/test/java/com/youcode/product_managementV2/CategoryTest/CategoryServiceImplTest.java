package com.youcode.product_managementV2.CategoryTest;

import com.youcode.product_managementV2.Entity.Category;
import com.youcode.product_managementV2.dto.request.CategoryRequestDto;
import com.youcode.product_managementV2.dto.response.CategoryResponseDto;
import com.youcode.product_managementV2.dto.update.CategoryUpdateDto;
import com.youcode.product_managementV2.mapper.CategoryMapper;
import com.youcode.product_managementV2.repository.CategoryRepository;
import com.youcode.product_managementV2.repository.ProductRepository;
import com.youcode.product_managementV2.service.Impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryServiceImplTest {


    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
    }

    @Test
    void testGetAllCategories() {
        Category category = Category.builder()
                .id(1L)
                .name("Category 1")
                .description("Category 1 description")
                .build();
        List<Category> categories = List.of(category);
        Page<Category> categoryPage = new PageImpl<>(categories, PageRequest.of(0, 10), 1);

        when(categoryRepository.findAll(any(Pageable.class))).thenReturn(categoryPage);
        when(categoryMapper.toDTO(any(Category.class))).thenReturn(CategoryResponseDto.builder()
                .name("Category 1")
                .description("Category 1 description")
                .build());

        Page<CategoryResponseDto> result = categoryService.getAllCategories(0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(categoryRepository).findAll(any(Pageable.class));
    }

    @Test
    void testSearchCategoriesByName() {
        Category category = Category.builder()
                .id(1L)
                .name("Category 1")
                .description("Category 1 description")
                .build();
        List<Category> categories = List.of(category);
        Page<Category> categoryPage = new PageImpl<>(categories, PageRequest.of(0, 10), 1);

        // Use eq() for the raw value "Category"
        when(categoryRepository.findByNameContainingIgnoreCase(eq("Category"), any(Pageable.class)))
                .thenReturn(categoryPage);
        when(categoryMapper.toDTO(any(Category.class)))
                .thenReturn(CategoryResponseDto.builder()
                        .name("Category 1")
                        .description("Category 1 description")
                        .build());

        Page<CategoryResponseDto> result = categoryService.searchCategoriesByName("Category", 0, 10, "name", "asc");

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        verify(categoryRepository).findByNameContainingIgnoreCase(eq("Category"), any(Pageable.class));
    }

    @Test
    void testAddCategory() {
        CategoryRequestDto categoryDto = CategoryRequestDto.builder()
                .name("New Category")
                .description("New Category description")
                .build();

        Category category = Category.builder()
                .name("New Category")
                .description("New Category description")
                .build();

        when(categoryMapper.toEntity(any(CategoryRequestDto.class))).thenReturn(category);

        categoryService.addCategory(categoryDto);

        verify(categoryRepository).save(category);
    }

    @Test
    void testUpdateCategory() {
        CategoryUpdateDto categoryUpdateDto = CategoryUpdateDto.builder()
                .name("Updated Category")
                .description("Updated Category description")
                .build();

        Category existingCategory = Category.builder()
                .id(1L)
                .name("Old Category")
                .description("Old Category description")
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));

        // Update the category object manually during the mock to simulate the behavior of the actual method
        doAnswer(invocation -> {
            CategoryUpdateDto dto = invocation.getArgument(0);
            Category category = invocation.getArgument(1);
            category.setName(dto.getName());
            category.setDescription(dto.getDescription());
            return null;
        }).when(categoryMapper).updateCategoryFromDto(any(CategoryUpdateDto.class), any(Category.class));

        categoryService.updateCategory(1L, categoryUpdateDto);

        // Verifying that the categoryMapper was called to update the category object
        verify(categoryMapper).updateCategoryFromDto(any(CategoryUpdateDto.class), any(Category.class));

        // Verifying that the category was saved in the repository
        verify(categoryRepository).save(existingCategory);

        // Asserting that the category details were updated correctly
        assertEquals("Updated Category", existingCategory.getName());
        assertEquals("Updated Category description", existingCategory.getDescription());
    }


    @Test
    void testDeleteCategory() {
        Category category = Category.builder()
                .id(1L)
                .name("Category to delete")
                .description("Category to delete description")
                .build();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository).deleteById(1L);
    }
}
