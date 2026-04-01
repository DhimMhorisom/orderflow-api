package com.dhimmhorisom.orderflow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

// Cria getters e setters automaticamente e reduz codigo.
@Getter
@Setter

 /*
  Representa a entidade de usuário no sistema.
  Mapeada para a tabela "users".
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private String email;
    private String password;
    private String role;  // Papel do usuário no sistema (ADMIN ou CUSTOMER)
    private Boolean active;  // Indica se o usuário está ativo no sistema
    private LocalDateTime createdAt;



}
