package com.dhimmhorisom.orderflow.service;

import com.dhimmhorisom.orderflow.dto.ProductRequestDTO;
import com.dhimmhorisom.orderflow.dto.ProductResponseDTO;
import com.dhimmhorisom.orderflow.entity.Category;
import com.dhimmhorisom.orderflow.entity.Product;
import com.dhimmhorisom.orderflow.exception.CategoryNotFoundException;
import com.dhimmhorisom.orderflow.exception.ProductNotFoundException;
import com.dhimmhorisom.orderflow.repository.CategoryRepository;
import com.dhimmhorisom.orderflow.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponseDTO create(ProductRequestDTO dto) {
        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));

        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setActive(dto.active());
        product.setCategory(category);

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProductResponseDTO findById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        return toResponse(product);
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Produto não encontrado"));

        Category category = categoryRepository.findById(dto.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));

        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());
        product.setActive(dto.active());
        product.setCategory(category);

        Product updated = productRepository.save(product);
        return toResponse(updated);
    }

    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Produto não encontrado");
        }

        productRepository.deleteById(id);
    }

    private ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getActive(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }
}