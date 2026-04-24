package com.dhimmhorisom.orderflow.controller;

import com.dhimmhorisom.orderflow.dto.AuthRequestDTO;
import com.dhimmhorisom.orderflow.dto.AuthResponseDTO;
import com.dhimmhorisom.orderflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Auth", description = "Autenticação e geração de token JWT")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @Operation(
            summary = "Realizar login",
            description = "Autentica o usuário e retorna um token JWT para acessar rotas protegidas."
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO dto) {
        return ResponseEntity.ok(service.login(dto));
    }
}