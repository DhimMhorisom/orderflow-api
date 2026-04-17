package com.dhimmhorisom.orderflow.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateOrderStatusDTO(
        @NotNull(message = "O status é obrigatório")
        String status
) {}