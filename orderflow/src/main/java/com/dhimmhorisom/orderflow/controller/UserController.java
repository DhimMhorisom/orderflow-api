package com.dhimmhorisom.orderflow.controller;

import com.dhimmhorisom.orderflow.dto.UserRequestDTO;
import com.dhimmhorisom.orderflow.dto.UserResponseDTO;
import com.dhimmhorisom.orderflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;


@Tag(name = "Users", description = "Gerenciamento de usuários")
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    // Injeção por construtor
    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Criar usuário", description = "Cadastra um novo usuário com perfil CUSTOMER.")
    @PostMapping
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
        UserResponseDTO response = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados. Requer permissão ADMIN.")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário pelo ID informado.")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente.")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid UserRequestDTO dto) {

        UserResponseDTO response = service.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Deletar usuário", description = "Remove um usuário pelo ID informado. Requer permissão ADMIN.")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}