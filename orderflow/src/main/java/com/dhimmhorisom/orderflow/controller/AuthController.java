package com.dhimmhorisom.orderflow.controller;

import com.dhimmhorisom.orderflow.dto.AuthRequestDTO;
import com.dhimmhorisom.orderflow.dto.AuthResponseDTO;
import com.dhimmhorisom.orderflow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequestDTO dto) {
        return ResponseEntity.ok(service.login(dto));
    }
}