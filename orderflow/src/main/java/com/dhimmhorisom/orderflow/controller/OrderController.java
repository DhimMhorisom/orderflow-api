package com.dhimmhorisom.orderflow.controller;

import com.dhimmhorisom.orderflow.dto.OrderRequestDTO;
import com.dhimmhorisom.orderflow.dto.OrderResponseDTO;
import com.dhimmhorisom.orderflow.dto.UpdateOrderStatusDTO;
import com.dhimmhorisom.orderflow.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Orders", description = "Gerenciamento de pedidos")
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @Operation(
            summary = "Criar pedido",
            description = "Cria um pedido com múltiplos itens, calcula o valor total automaticamente e reduz o estoque dos produtos."
    )
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody @Valid OrderRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @Operation(summary = "Listar pedidos", description = "Lista todos os pedidos cadastrados. Requer permissão ADMIN.")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Listar pedidos por usuário", description = "Lista todos os pedidos vinculados a um usuário específico.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponseDTO>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findByUserId(userId));
    }

    @Operation(
            summary = "Atualizar status do pedido",
            description = "Atualiza o status de um pedido. Ao cancelar, o estoque dos produtos é reintegrado automaticamente."
    )
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdateOrderStatusDTO dto) {

        return ResponseEntity.ok(service.updateStatus(id, dto.status()));
    }
}