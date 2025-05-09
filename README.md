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
5. Vá até a classe `BackendApplication.java` (pacote `com.planit.backend`) e clique em **Run**.

> 💡 Caso não apareça o botão de "Run", clique com o botão direito sobre a classe e selecione **Run 'ApiApplication'**.

---

#### ✅ Opção 2: Linha de Comando (Windows/Linux)

Acesse a pasta do backend:

```bash
cd backend
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

## 📡 Rotas Principais (`/viagens`)

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
  "name": "Viagem para São Paulo",
  "user": {
    "id": 1,
    "name": "Jonas",
    "email": "jonas@email.com"
  },
  "destination": {
    "id": 4,
    "name": "São Paulo",
    "country": "Brasil"
  },
  "baggageSuggestion": [
    "Casaco",
    "Calça jeans"
  ],
  "startDate": "2025-11-15T09:00:00",
  "endDate": "2025-11-20T20:00:00",
  "participants": [
    {
      "id": 3,
      "name": "Maria",
      "email": "maria@email.com"
    }
  ]
}
```
#### ✅ GET `/viagens?search=rio&take=5&skip=0` — Listar as viagens 

#### ✅ DELETE `/viagens/5` — Deletar as viagens

#### ✅ GET `/viagens/baggage-items/{destinationId}` — Sugestão de bagagens pra aquele destino


## Grupo

*João Vitor Figueiredo Espindolola*