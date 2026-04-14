package com.dhimmhorisom.orderflow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProductRequestDTO(

        @NotBlank(message = "O nome do produto é obrigatório")
        @Size(max = 150, message = "O nome do produto deve ter no máximo 150 caracteres")
        String name,

        @Size(max = 255, message = "A descrição deve ter no máximo 255 caracteres")
        String description,

        @NotNull(message = "O preço é obrigatório")
        @Positive(message = "O preço deve ser maior que zero")
        BigDecimal price,

        @NotNull(message = "O estoque é obrigatório")
        @PositiveOrZero(message = "O estoque não pode ser negativo")
        Integer stock,

        @NotNull(message = "O status ativo é obrigatório")
        Boolean active,

        @NotNull(message = "A categoria é obrigatória")
        Long categoryId
) {}