# OrderFlow API

API REST desenvolvida com Java e Spring Boot para gerenciamento de usuários, categorias, produtos e pedidos, com autenticação via JWT.

---

## Objetivo

Projeto desenvolvido para fins de portfólio, com foco em boas práticas de desenvolvimento backend, arquitetura em camadas, segurança e organização de código.

---

## Tecnologias utilizadas

* Java 17
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* JWT (JSON Web Token)
* MySQL
* Flyway (versionamento de banco)
* Lombok
* Swagger / OpenAPI

---

##  Autenticação

A API utiliza autenticação via JWT.

###  Login

```http
POST /auth/login
```

**Request:**

```json
{
  "email": "user@email.com",
  "password": "123456"
}
```

**Response:**

```json
{
  "token": "jwt_token"
}
```

---

##  Funcionalidades

###  Usuários

* Criar usuário
* Listar usuários (ADMIN)
* Buscar por ID
* Atualizar
* Deletar (ADMIN)

---

###  Categorias

* Criar (ADMIN)
* Listar
* Buscar por ID
* Atualizar (ADMIN)
* Deletar (ADMIN)

---

###  Produtos

* Criar (ADMIN)
* Listar
* Buscar por ID
* Atualizar (ADMIN)
* Deletar (ADMIN)

---

###  Pedidos

* Criar pedido
* Listar pedidos
* Buscar por usuário
* Atualizar status
* Cancelar pedido (com devolução de estoque)

---

##  Controle de Acesso

| Ação            | Permissão           |
| --------------- | ------------------- |
| Criar usuário   | Público             |
| Login           | Público             |
| Listar usuários | ADMIN               |
| CRUD categorias | ADMIN / autenticado |
| CRUD produtos   | ADMIN / autenticado |
| Criar pedido    | Autenticado         |

---

##  Banco de Dados

* MySQL
* Versionamento com Flyway

---

##  Documentação da API

Swagger disponível em:

```
http://localhost:8081/swagger-ui/index.html
```

---

##  Como rodar o projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/orderflow.git
```

### 2. Configurar banco de dados

Criar banco MySQL:

```sql
CREATE DATABASE orderflow;
```

### 3. Rodar aplicação

```bash
./mvnw spring-boot:run
```

---

##  Testes

Os endpoints podem ser testados via:

* Postman
* Swagger UI

---

##  Melhorias futuras

* Testes automatizados (JUnit + Mockito)
* Dockerização
* Deploy em cloud (Render / Railway / AWS)
* Paginação de resultados
* Logs estruturados

---

## Autor

Dhim Mhorisom

Projeto desenvolvido para fins de estudo e portfólio.
