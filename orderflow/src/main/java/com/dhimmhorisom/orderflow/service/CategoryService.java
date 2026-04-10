package com.dhimmhorisom.orderflow.service;

import com.dhimmhorisom.orderflow.dto.CategoryRequestDTO;
import com.dhimmhorisom.orderflow.dto.CategoryResponseDTO;
import com.dhimmhorisom.orderflow.entity.Category;
import com.dhimmhorisom.orderflow.exception.CategoryNotFoundException;
import com.dhimmhorisom.orderflow.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public CategoryResponseDTO create(CategoryRequestDTO dto) {
        Category category = new Category();
        category.setName(dto.name());
        category.setDescription(dto.description());

        Category saved = repository.save(category);

        return new CategoryResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getDescription()
        );
    }

    public List<CategoryResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(category -> new CategoryResponseDTO(
                        category.getId(),
                        category.getName(),
                        category.getDescription()
                ))
                .toList();
    }

    public CategoryResponseDTO findById(Long id) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));

        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public CategoryResponseDTO update(Long id, CategoryRequestDTO dto) {
        Category category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoria não encontrada"));

        category.setName(dto.name());
        category.setDescription(dto.description());

        Category updated = repository.save(category);

        return new CategoryResponseDTO(
                updated.getId(),
                updated.getName(),
                updated.getDescription()
        );
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new CategoryNotFoundException("Categoria não encontrada");
        }

        repository.deleteById(id);
    }
}