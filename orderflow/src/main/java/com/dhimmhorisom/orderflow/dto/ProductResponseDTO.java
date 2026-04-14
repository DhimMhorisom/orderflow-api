package com.dhimmhorisom.orderflow.dto;

import java.math.BigDecimal;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Boolean active,
        Long categoryId,
        String categoryName
) {}