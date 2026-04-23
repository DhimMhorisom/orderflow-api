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
* JUnit 5 + Mockito

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

##  Exemplo de fluxo
1. Criar usuário
2. Realizar login para obter token JWT
3. Utilizar o token nas requisições protegidas
4. Criar categorias e produtos (ADMIN)
5. Criar pedidos com múltiplos itens
6. Acompanhar e atualizar status dos pedidos

##  Funcionalidades
* Cadastro e autenticação de usuários
* Controle de acesso por perfil (ADMIN / CUSTOMER)
* CRUD de categorias
* CRUD de produtos
* Criação de pedidos com múltiplos itens
* Controle de estoque automático
* Cancelamento de pedidos com reversão de estoque


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
git clone https://github.com/DhimMhorisom/orderflow-api.git
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

```bash
http://localhost:8081/swagger-ui/index.html
```

---

##  Segurança

* Autenticação via JWT
* Senhas criptografadas com BCrypt
* Rotas protegidas por roles (ADMIN / CUSTOMER)

---

##  Testes

Testes unitários implementados com:

- JUnit 5
- Mockito

Cobertura das principais regras de negócio:

- UserService
- ProductService
- OrderService

Os endpoints também podem ser testados via:

- Swagger UI
- Postman

---

##  Melhorias futuras

* Dockerização da aplicação
* Deploy em cloud (Render / Railway / AWS)
* Paginação de resultados
* Logs estruturados

---

## Autor

Dhim Mhorisom S. R. da Silva

Backend Developer em formação, focado em Java e Spring Boot.

Projeto desenvolvido como aplicação real para portfólio, com foco em boas práticas, segurança e arquitetura backend.

