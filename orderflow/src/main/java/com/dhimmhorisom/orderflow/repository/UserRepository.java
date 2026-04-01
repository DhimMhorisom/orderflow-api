package com.dhimmhorisom.orderflow.repository;
import com.dhimmhorisom.orderflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//Responsável pelo acesso aos dados da entidade User.
public interface UserRepository extends JpaRepository<User, Long> {
    // Busca um usuário pelo email.
    Optional<User> findByEmail(String email);
}