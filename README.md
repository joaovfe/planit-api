# 📦 Backend - Projeto PLANIT

Este é o backend do sistema PLANIT, desenvolvido em Java utilizando Spring Boot.

---

## ✅ Como Rodar o Projeto

### 🔁 Clone o Repositório

```bash
git clone https://github.com/joaovfe/planit-api.git
cd planit-api
```

---

### 🖥️ Rodando o Backend

> Pré-requisitos: **Java 17** + **Lombok**

#### ✅ Opção 1: IntelliJ IDEA (recomendado)

1. Abra o IntelliJ IDEA.
2. Vá em **File > Open** e selecione a pasta `backend` do projeto.
3. Aguarde o IntelliJ importar as dependências automaticamente (Maven).
4. Certifique-se de que o SDK está configurado para **Java 17**.
5. Vá até a classe `ApiApplication.java` (pacote `com.planit.planit`) e clique em **Run**.

> 💡 Caso não apareça o botão de "Run", clique com o botão direito sobre a classe e selecione **Run 'ApiApplication'**.

---

#### ✅ Opção 2: Linha de Comando (Windows/Linux)

Acesse a pasta do backend:

```bash
cd planit-api
```

- **Windows**:
```bash
mvnw.cmd spring-boot:run
```

- **Linux/Mac**:
```bash
./mvnw spring-boot:run
```

---

## 🧪 Testes

Para rodar os testes automatizados, execute:

```bash
./mvnw test
```

---

## 🔗 Tecnologias

- Java 17   
- Spring Boot
- Maven
- Lombok
### Rotas

## 📡 Rota (`/auth`)

### 🧭 Endpoints

| Método | Rota                                      | Descrição                                               |
|--------|-------------------------------------------|----------------------------------------------------------|
| POST   | `/auth/login`                           | Fazer autenticação com retorno do token. |

### 🔐 Payloads

**Utilizar esse usuário do exemplo.**

```json
{
    "email": "teste@gmail.com",
    "password": "12345678"
}
```

## 📡 Rotas (`/viagens`)

### 🧭 Endpoints

| Método | Rota                                      | Descrição                                               |
|--------|-------------------------------------------|----------------------------------------------------------|
| POST   | `/viagens/novo`                           | Cria uma nova viagem.                                   |
| GET    | `/viagens`                                | Lista viagens com filtros `search`, `take`, `skip`.     |
| GET    | `/viagens/{id}`                           | Busca uma viagem pelo ID.                               |
| PUT    | `/viagens/{id}`                           | Atualiza uma viagem existente.                          |
| DELETE | `/viagens/{id}`                           | Deleta uma viagem pelo ID.                              |
| GET    | `/viagens/sugestao`                       | Gera sugestão de viagem com base no usuário logado.     |
| GET    | `/viagens/baggage-items/{destinationId}`  | Sugere itens de bagagem para o destino informado.       |

---

### 🔐 Payloads

#### ✅ POST `/viagens/novo` — Criar nova viagem

```json
{
    "name": "teste",
    "user": {
        "id": 5,
        "email": "teste@gmail.com",
        "name": "joao",
        "hash_password": "",
        "role_id": 0,
        "roles": [
            {
                "id": 5,
                "roleName": "ROLE_ADMIN"
            }
        ]
    },
    "baggageSuggestion": [
        "Protetor solar e labial",
        "Repelente de insetos",
        "Calçados confortáveis para caminhada",
        "Chapéu ou boné",
        "Óculos de sol",
        "Remédios de uso contínuo e kit de primeiros socorros",
        "Câmera ou celular para fotos"
    ],
    "destination": {
        "id": 5,
        "description": "Região de fazendas, trilhas e culinária típica no campo.",
        "name": "Interior de Minas Gerais",
        "type": {
            "id": 4,
            "name": "Campo"
        }
    },
    "participants": [
        {
            "id": 5,
            "name": "joao"
        }
    ],
    "startDate": "2025-05-22T03:00:00.000Z",
    "endDate": "2025-05-02T03:00:00.000Z"
}
```
#### ✅ PUT `/viagens/5` — Atualizar nova viagem
```json
{
    "name": "teste",
    "destination": {
        "id": 8,
        "description": "Cidade histórica litorânea com fortes, igrejas e praias.",
        "name": "Recife",
        "type": {
            "id": 1,
            "name": "Praia"
        }
    },
    "participants": [
        {
            "id": 5,
            "name": "joao"
        },
        {
            "id": 6,
            "name": "Pedro"
        }
    ],
    "startDate": "2025-05-01T06:00:00.000Z",
    "endDate": "2025-05-31T06:00:00.000Z",
    "baggageSuggestion": [
        "Protetor solar",
        "Chapéu/Boné",
        "Óculos de sol",
        "Roupas leves e confortáveis",
        "Roupa de banho",
        "Adaptador de tomada (se necessário)",
        "Repelente"
    ],
    "user": {
        "id": 5,
        "email": "teste@gmail.com",
        "name": "joao",
        "hash_password": "",
        "role_id": 0,
        "roles": [
            {
                "id": 5,
                "roleName": "ROLE_ADMIN"
            }
        ]
    }
}
```
#### ✅ GET `/viagens?search=rio&take=5&skip=0` — Listar as viagens 

#### ✅ DELETE `/viagens/5` — Deletar as viagens

#### ✅ GET `/viagens/baggage-items/{destinationId}` — Sugestão de bagagens pra aquele destino

#### ✅ GET `/viagens/viagens/sugestao` — Sugestão de destino para o usuário cadastro com base nas suas preferências (necessita auth)

---

### 📝 Rotas de Avaliações (Reviews)

| Método | Rota                            | Descrição                                               |
|--------|--------------------------------|--------------------------------------------------------|
| POST   | `/viagens/{tripId}/reviews`    | Cria uma nova avaliação para uma viagem.              |
| GET    | `/viagens/{tripId}/reviews`    | Lista as avaliações de uma viagem específica.         |
| PUT    | `/reviews/{reviewId}`          | Atualiza uma avaliação existente (requer ser o autor).|
| DELETE | `/reviews/{reviewId}`          | Deleta uma avaliação (requer ser o autor).            |

---

### 💬 Rotas de Comentários (Comments)

| Método | Rota                            | Descrição                                               |
|--------|--------------------------------|--------------------------------------------------------|
| POST   | `/viagens/{tripId}/comments`   | Cria um novo comentário em uma viagem.                |
| GET    | `/viagens/{tripId}/comments`   | Lista os comentários de uma viagem específica.        |
| PUT    | `/comments/{commentId}`        | Atualiza um comentário existente (requer ser o autor).|
| DELETE | `/comments/{commentId}`        | Deleta um comentário (requer ser o autor).            |

---

### 🔐 Payloads

### ✅ POST `/viagens/{tripId}/reviews` — Criar nova avaliação

**Requer autenticação.** O usuário deve ser um participante da viagem.

```json
{
    "note": 5,
    "moneySpent": 4200.50
}
```

### ✅ POST `/viagens/{tripId}/comments` — Criar novo comentário

**Requer autenticação.** O usuário deve ser um participante da viagem.

```json
{
    "comment": "Essa viagem foi incrível, recomendo a todos!"
}
```

---

## 📡 Rotas (`/destino`)

### 🧭 Endpoints

| Método | Rota                                      | Descrição                                               |
|--------|-------------------------------------------|----------------------------------------------------------|
| GET    | `/destino`                                | Lista todos os destinos.                                |
| PUT    | `/destino/update/{id}`                    | Edita um destino.                                       |
| GET    | `/destino/{id}`                           | Busca um destino pelo ID e acrescenta uma visualização. |
| POST   | `/destino/novo`                           | Cria um novo destino.                                   |
| DELETE | `/destino/delete/{id}`                    | Deleta um destino pelo ID.                              |
| POST   | `/destino/favorite/{id}`                  | Favorita um destino pelo ID.                            |
| DELETE | `/destino/favorite/{id}`                  | Remove o favorito do destino pelo ID.                   |
| GET    | `/destino/ranking?visualizationType={VIEW ou FAVORITE}`| Busca todos os destinos ordenados por ranking de visualizações ou favoritos. |                   |

---



## By *João Vitor Figueiredo Espindolola, Arthur Ghizi e Eduardo Domingos*

