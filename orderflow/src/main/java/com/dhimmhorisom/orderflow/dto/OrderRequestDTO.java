package com.dhimmhorisom.orderflow.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestDTO(

        @NotNull(message = "O usuário é obrigatório")
        Long userId,

        @NotEmpty(message = "O pedido deve ter pelo menos um item")
        List<@Valid OrderItemRequestDTO> items
) {}