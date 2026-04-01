package com.dhimmhorisom.orderflow.service;

import com.dhimmhorisom.orderflow.dto.UserRequestDTO;
import com.dhimmhorisom.orderflow.dto.UserResponseDTO;
import com.dhimmhorisom.orderflow.entity.User;
import com.dhimmhorisom.orderflow.exception.EmailAlreadyExistsException;
import com.dhimmhorisom.orderflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserResponseDTO create(UserRequestDTO dto) {

        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email já cadastrado.");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setRole("USER");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        User saved = repository.save(user);

        return new UserResponseDTO(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }

    public List<UserResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(user -> new UserResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }

    public UserResponseDTO findById(Long id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public UserResponseDTO update(Long id, UserRequestDTO dto) {

        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // valida email duplicado (se mudou)
        if (!user.getEmail().equals(dto.email()) &&
                repository.findByEmail(dto.email()).isPresent()) {
            throw new EmailAlreadyExistsException("Email já cadastrado.");
        }

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        User updated = repository.save(user);

        return new UserResponseDTO(
                updated.getId(),
                updated.getName(),
                updated.getEmail()
        );
    }

    public void delete(Long id){
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        repository.delete(user);
    }

}