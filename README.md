# Segurança Urbana - Backend

![Java](https://img.shields.io/badge/Java-17-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?logo=spring-boot)
![H2](https://img.shields.io/badge/H2-Database-blue?logo=h2)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Compatible-blue?logo=postgresql)

## 🚦 Sobre o Projeto

O **Segurança Urbana** é uma API backend para um sistema de registro, monitoramento e gestão de ocorrências urbanas. Ela permite que cidadãos relatem ocorrências (como furtos, vandalismo, etc.), acompanhem o status dos seus registros e possibilita que agentes e administradores possam gerenciar, atender e resolver essas ocorrências.  
O sistema foi desenvolvido em Java com Spring Boot, seguindo boas práticas de arquitetura, segurança (JWT), documentação (Swagger) e testes automatizados.

## 🛠️ Principais Funcionalidades

- Cadastro e autenticação de usuários (JWT)
- Registro de ocorrências por cidadãos
- Consulta de ocorrências próprias
- Gestão de ocorrências por agentes e admins
- Upload de fotos de ocorrências
- Sistema de categorias e status de ocorrências
- Histórico e atualização de status
- Documentação Swagger/OpenAPI disponível

## 🚀 Como executar o projeto

### 1. Pré-requisitos

- Java 17+
- Maven 3.8+
- (Opcional) Docker

### 2. Configuração

1. **Clone o repositório:**
    ```sh
    git clone https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git
    cd SEU_REPOSITORIO
    ```
2. **Configuração do banco de dados:**
    - Por padrão, a aplicação utiliza o banco de dados **H2** (em memória), que não precisa de configuração adicional para testes e desenvolvimento rápido.
    - Para ambientes de produção ou integração, é possível configurar facilmente outro banco de dados relacional (como **PostgreSQL**, **MySQL** etc) alterando as propriedades em `src/main/resources/application.properties`:
      ```properties
      # Exemplo para PostgreSQL
      spring.datasource.url=jdbc:postgresql://localhost:5432/seurdb
      spring.datasource.username=SEU_USUARIO
      spring.datasource.password=SUA_SENHA
      spring.jpa.hibernate.ddl-auto=update
      ```
    - Para H2 (default):
      ```properties
      spring.datasource.url=jdbc:h2:mem:seguranca_urbana
      spring.datasource.driverClassName=org.h2.Driver
      spring.datasource.username=sa
      spring.datasource.password=
      spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
      spring.h2.console.enabled=true
      ```
    - O console do H2 pode ser acessado em: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

3. **Instale as dependências e rode:**
    ```sh
    ./mvnw spring-boot:run
    ```
    ou
    ```sh
    mvn spring-boot:run
    ```

### 3. Banco de Dados

- Por padrão, o H2 é utilizado para facilitar o desenvolvimento e testes.
- Para uso em produção, configure um banco relacional real alterando as propriedades conforme desejado.

### 4. Acessando o Swagger (Documentação da API)

Após iniciar o projeto, acesse:

- **Swagger UI:**  
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

- **OpenAPI Docs:**  
  [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 📦 Estrutura do Projeto

```
src/
 └── main/
     ├── java/
     │    └── com/
     │         └── seguranca_urbana/
     │              ├── backend/
     │              │    ├── controllers/
     │              │    ├── models/
     │              │    ├── repositorys/
     │              │    ├── security/
     │              │    ├── services/
     │              │    └── ...
     └── resources/
          ├── application.properties
          └── ...
```

## 📝 Tecnologias Utilizadas

- Java 17
- Spring Boot 3
- Spring Data JPA
- Spring Security (JWT)
- H2 Database (default)
- Suporte a PostgreSQL, MySQL e outros bancos relacionais
- Maven
- Swagger/OpenAPI
- JUnit (testes)
- Docker (opcional)

## 👥 Desenvolvedores

| Nome                | GitHub                                               | E-mail                          |
|---------------------|------------------------------------------------------|---------------------------------|
| Livia Menezes       | [@menezeslivia](https://github.com/menezeslivia)     | liviajmenezes23@gmail.com       |
| Ystefani Gomes      | [@Ystefani-Gomes](https://github.com/Ystefani-Gomes) | ystefani231@gmail.com           |
| Cleverson Fernando  | [@NandoVlr](https://github.com/NandoVlr)             | cleversonando@gmail.com         |
| Weslley Matheus     | [@w3sll3ym](https://github.com/w3sll3ym)             | wslleymatheusferreira@gmail.com |
| Emily Lacerda       | [@Emily-Lacerda](https://github.com/Emily-Lacerda)   | emilyvr19@gmail.com             |
---

> Projeto desenvolvido para fins acadêmicos/profissionais. Sinta-se livre para contribuir!
