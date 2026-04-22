package com.dhimmhorisom.orderflow.service;

import com.dhimmhorisom.orderflow.dto.UserRequestDTO;
import com.dhimmhorisom.orderflow.dto.UserResponseDTO;
import com.dhimmhorisom.orderflow.entity.User;
import com.dhimmhorisom.orderflow.enums.Role;
import com.dhimmhorisom.orderflow.exception.EmailAlreadyExistsException;
import com.dhimmhorisom.orderflow.repository.UserRepository;
import com.dhimmhorisom.orderflow.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService service;

    private UserRequestDTO userRequestDTO;
    private User user;

    @BeforeEach
    void setUp() {
        userRequestDTO = new UserRequestDTO(
                "Dhim",
                "dhim@email.com",
                "123456"
        );

        user = new User();
        user.setId(1L);
        user.setName("Dhim");
        user.setEmail("dhim@email.com");
        user.setPassword("senha-criptografada");
        user.setRole(Role.CUSTOMER);
        user.setActive(true);
    }

    @Test
    void shouldCreateUserSuccessfully() {
        when(repository.findByEmail(userRequestDTO.email())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRequestDTO.password())).thenReturn("senha-criptografada");
        when(repository.save(any(User.class))).thenReturn(user);

        UserResponseDTO response = service.create(userRequestDTO);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Dhim", response.name());
        assertEquals("dhim@email.com", response.email());

        verify(repository, times(1)).findByEmail(userRequestDTO.email());
        verify(passwordEncoder, times(1)).encode(userRequestDTO.password());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        when(repository.findByEmail(userRequestDTO.email())).thenReturn(Optional.of(user));

        EmailAlreadyExistsException exception = assertThrows(
                EmailAlreadyExistsException.class,
                () -> service.create(userRequestDTO)
        );

        assertEquals("Email já cadastrado.", exception.getMessage());

        verify(repository, times(1)).findByEmail(userRequestDTO.email());
        verify(repository, never()).save(any(User.class));
    }
}