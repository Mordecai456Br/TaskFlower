# 🌸 TaskFlower

TaskFlower é uma aplicação **Java 17** com **Spring Boot** voltada para o gerenciamento de tarefas no estilo **Kanban**.  
O projeto contém a **API principal** e uma **interface básica do frontend**.  
As demais funções e telas serão desenvolvidas gradualmente, como parte de um processo pessoal de **estudo, prototipagem e validação**.

**Autor:** GRUPO 4 Java Accenture — **Gabriel Begodex 🐋 (@begodex)**, Hiago, Fernando França, Maria Esther.

---

## 🚀 Como rodar o projeto

Para executar o projeto, você precisa ter instalado:
- **Java 17**
- **Maven** (ou usar o wrapper `mvnw`)
- **MySQL** (para o banco de dados)

---

### 🧩 Passo 1 — Clonar o repositório

git clone https://github.com/Mordecai456Br/TaskFlower.git cd TaskFlower


### 🗄️ Passo 2 — Criar o banco de dados

Crie um banco de dados chamado `taskflower` no MySQL.
Em seguida, abra o arquivo:

`src/main/resources/application.properties`

e adicione suas credenciais:

spring.datasource.url=jdbc:mysql://localhost:3306/taskflower spring.datasource.username=SEU_USUARIO spring.datasource.password=SUA_SENHA spring.jpa.hibernate.ddl-auto=update spring.jpa.show-sql=true


### ▶️ Passo 3 — Rodar o projeto

./mvnw spring-boot:run


Após iniciar, acesse o backend no navegador:

`http://localhost:8080`

---

### 📖 Acessando a documentação e os endpoints

A documentação Swagger da API está disponível em:

`http://localhost:8080/swagger-ui/index.html`

### 🔐 Como usar a autenticação

A autenticação é feita por JWT (Bearer Token).

**Registrar um usuário**

POST http://localhost:8080/auth/register


Corpo (JSON):

{ "username": "seu_usuario", "password": "sua_senha" }


**Fazer login**

POST http://localhost:8080/auth/login


A resposta conterá o token JWT de autenticação.

**Usar o token**

Em todas as requisições protegidas, envie o cabeçalho:

Authorization: Bearer SEU_TOKEN_AQUI


Exemplo:

Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJhdXRoLWFwaSIsInN1YiI6InN0cmluZyIsImV4cCI6MTc2MjU1Mjk5Nn0.iOJ42ko8MaiunryWMlNRnXajYLNM5nTcGlIRoyQh_t8


---

### 🎨 Protótipo visual e conceito

Você pode visualizar a proposta visual e ideia do projeto no Figma:
(https://www.figma.com/deck/Fdt9MktMgdNBBZdL9Qox8u/Taskflower?node-id=1-196&t=uGRUt6usaIiDX2Pu-1&scaling=min-zoom&content-scaling=fixed&page-id=0%3A1)

---

### ⚙️ Resumo técnico

- **Linguagem:** Java 17
- **Framework:** Spring Boot
- **Autenticação:** JWT
- **Banco de Dados:** MySQL
- **Ferramentas:** Maven, Swagger, Spring Data JPA
- **Frontend:** Kanban básico embutido na API

---

### 💬 Observação do autor

Este módulo contém apenas a API e o frontend básico com o Kanban.
Por questões de metodologia de desenvolvimento pessoal (estudo, protótipo, validação, teste e código),
as demais funções e telas foram adiadas e serão implementadas ao longo do tempo.

Acompanhe no Instagram: @begodex
att, Gabriel Begodex 🐋
