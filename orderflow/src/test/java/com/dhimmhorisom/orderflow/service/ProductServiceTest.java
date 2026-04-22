package com.dhimmhorisom.orderflow.service;

import com.dhimmhorisom.orderflow.dto.ProductRequestDTO;
import com.dhimmhorisom.orderflow.dto.ProductResponseDTO;
import com.dhimmhorisom.orderflow.entity.Category;
import com.dhimmhorisom.orderflow.entity.Product;
import com.dhimmhorisom.orderflow.exception.CategoryNotFoundException;
import com.dhimmhorisom.orderflow.exception.ProductNotFoundException;
import com.dhimmhorisom.orderflow.repository.CategoryRepository;
import com.dhimmhorisom.orderflow.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService service;

    private Category category;
    private ProductRequestDTO dto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Eletrônicos");

        dto = new ProductRequestDTO(
                "Notebook",
                "Notebook Gamer",
                new BigDecimal("5000.00"),
                10,
                true,
                1L
        );
    }

    @Test
    void shouldCreateProductSuccessfully() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product product = invocation.getArgument(0);
            product.setId(1L);
            return product;
        });

        ProductResponseDTO response = service.create(dto);

        assertNotNull(response);
        assertEquals("Notebook", response.name());
        assertEquals("Eletrônicos", response.categoryName());
        assertEquals(new BigDecimal("5000.00"), response.price());

        verify(categoryRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(
                CategoryNotFoundException.class,
                () -> service.create(dto)
        );

        verify(productRepository, never()).save(any(Product.class));
    }
}