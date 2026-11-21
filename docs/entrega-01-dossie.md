# Entrega 01 — Dossiê e diagnóstico inicial: PlanIt API

Projeto final de Clean Code 2026.2: refatoração do back-end do **PlanIt**, plataforma de planejamento e recomendação de viagens.

## 1. Equipe e repositório

| Integrante |
|---|
| João Vitor Figueiredo Espindola |
| Gabriel Boelter |
| Thiago Almeida |

- Repositório público: <https://github.com/joaovfe/planit-api>
- Projeto desenvolvido em disciplina anterior do curso; histórico completo em `git log` e `git shortlog -sn --all`.

## 2. O software

**Domínio:** turismo. O usuário cadastra preferências (país desejado, clima e estação), recebe recomendações de destino geradas por IA, planeja viagens com participantes e lista de bagagem, favorita destinos e acompanha um ranking dos destinos mais vistos e favoritados.

| Funcionalidade | Endpoint | Classe |
|---|---|---|
| CRUD de viagens com participantes e bagagem | `/viagens` | `TripsController`, `TripsService` |
| Sugestão de destino por IA a partir das preferências do usuário | `GET /viagens/sugestao` | `TripsService.generateSuggestion` |
| Sugestão de itens de bagagem por IA | `GET /viagens/baggage-items/{id}` | `BaggageItemService.suggestBaggageItems` |
| CRUD de destinos e contagem de visualizações | `/destino` | `DestinationController`, `DestinationService` |
| Favoritar e desfavoritar destinos | `/destino/favorite/{id}` | `DestinationService` |
| Ranking por visualizações ou favoritos | `GET /destino/ranking` | `DestinationService.getDestinationsByRanking` |
| Autenticação JWT, usuários, avaliações, comentários | `/auth`, `/usuarios` e outros | fora do escopo (seção 9) |

**Stack:** Java 17, Spring Boot 3.4.5 (Web, Security, Data JPA), PostgreSQL, Lombok, JJWT 0.11.5, Google Gemini (`gemini-2.0-flash`) via `RestTemplate`, Maven Wrapper.

## 3. Tamanho do projeto (`cloc`)

O projeto inteiro tem **2.315 linhas de código Java**, acima do limite de 800 da disciplina:

```text
$ cloc src/main/java
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                            89            634             17           2315
```

Por isso **propomos um escopo delimitado**: o núcleo de **viagens e recomendação**, com os pacotes `trips`, `destination`, `GoogleAI` e `baggage` e os 5 repositórios que eles usam. As entidades JPA (`models/`) ficam fora da contagem por serem só mapeamento. Elas só serão alteradas quando uma refatoração do escopo exigir, por exemplo um Move Method, e cada caso será registrado no PR.

```text
$ cloc --list-file=docs/escopo.txt
Language                     files          blank        comment           code
-------------------------------------------------------------------------------
Java                            22            206              0            731
-------------------------------------------------------------------------------
SUM:                            22            206              0            731
```

**731 linhas de código**, dentro da faixa de 400 a 800. Medido no commit original, sem testes, sem `target/` e sem dependências (`cloc` 1.66). A lista dos 22 arquivos está em [`docs/escopo.txt`](escopo.txt).

## 4. Baseline medido (commit original)

| Métrica | Valor | Ferramenta |
|---|---|---|
| Testes | 1 teste, **1 erro**, 0 passando | `./mvnw test` |
| Cobertura de linhas no escopo | **0%** (0 de 261 linhas executáveis) | JaCoCo 0.8.12 |
| Cobertura de branches no escopo | **0%** (0 de 28) | JaCoCo 0.8.12 |
| Avisos do linter no escopo | **491** (projeto inteiro: 1.355) | Checkstyle, `google_checks.xml` |
| Principais avisos | Indentation 292, CustomImportOrder 93, MissingJavadoc 49, LineLength 33 | Checkstyle |
| PMD (ruleset padrão) | 5 no escopo, todos `UnnecessaryImport` | maven-pmd-plugin 3.26.0 |
| Complexidade ciclomática | média 1,3; máxima 4 (`GoogleAIService.sendPrompt`); 52 métodos | lizard 1.17.13 |
| Maiores métodos | `GoogleAIService.sendPrompt` (32 NLOC), `TripsService.createTrip` (25 NLOC) | lizard |
| CI existente | 1 workflow (`maven-publish.yml`), só em release, com **JDK 11** num projeto Java 17 | `.github/workflows` |

O único teste (`ApiApplicationTests`) falha porque está no pacote `com.planit.planit`, enquanto a aplicação está em `com.planit.api`, e o Spring não encontra a `@SpringBootConfiguration`.

Comandos usados:

```bash
./mvnw -Dmaven.test.failure.ignore=true \
  org.jacoco:jacoco-maven-plugin:0.8.12:prepare-agent test \
  org.jacoco:jacoco-maven-plugin:0.8.12:report
./mvnw org.apache.maven.plugins:maven-checkstyle-plugin:3.6.0:checkstyle \
  -Dcheckstyle.config.location=google_checks.xml
./mvnw org.apache.maven.plugins:maven-pmd-plugin:3.26.0:pmd
lizard $(cat docs/escopo.txt)
cloc --list-file=docs/escopo.txt
```

## 5. Versão original

| Item | Valor |
|---|---|
| Commit original | `7cc77f9076542b880b8da36190db022e0adc95cf` ("Update README.md") |
| Tag | `v0-original` no mesmo commit |
| Branch congelado | `original`, protegido contra push |
| Branch refatorado | `main`, só recebe código via PR com 1 aprovação e checks verdes |

## 6. Catálogo inicial de smells

| ID | Smell | Categoria | Arquivo:linha | Impacto |
|---|---|---|---|---|
| S01 | Long Method | Inchaços | `trips/TripsService.java:42-77` | `createTrip` salva o destino, monta a viagem e cria os itens de bagagem; nenhum passo pode ser testado isolado |
| S02 | Long Method | Inchaços | `GoogleAI/GoogleAIService.java:22-61` | monta URL e corpo, chama o HTTP e faz o parse manual da resposta num só método (CCN 4, o maior do escopo) |
| S03 | Data Clumps | Inchaços | `trips/TripsController.java:46-49`, `trips/TripsService.java:79` | `search`, `take` e `skip` sempre andam juntos, e o cálculo de página fica espalhado |
| S04 | Switch Statements (condicional por tipo) | Abusos de OO | `destination/DestinationService.java:145-147` | ranking escolhido por ternário sobre `EVisualizationType`; um tipo novo exige editar o serviço |
| S05 | Alternative Classes with Different Interfaces | Abusos de OO | `GoogleAI/dtos/TripSuggestionDto.java:10`, `baggage/dtos/BaggageItemDto.java:10` | duas classes idênticas embrulham o texto da IA com nomes de campo diferentes |
| S06 | Divergent Change | Impedidores de mudança | `trips/TripsService.java:33-181` | a classe muda tanto por regra de CRUD de viagem quanto por texto do prompt da IA |
| S07 | Shotgun Surgery | Impedidores de mudança | `TripsService.java:90,109,143`; `DestinationService.java:50,62,72,79,95`; `BaggageItemService.java:62,67,76,84` | "buscar ou lançar" repetido 12 vezes com `IllegalArgumentException` e `RuntimeException`; mudar o tratamento (por exemplo, para 404) exige editar todos |
| S08 | Dead Code | Dispensáveis | `trips/dtos/UpdateTripDto.java:3`, `destination/dtos/DestinationRequestDto.java:3`, `BaggageItemService.java:26-27`, `DestinationService.java:29-30` | DTOs nunca usados, repositórios e `EntityManager` injetados e não usados |
| S09 | Speculative Generality | Dispensáveis | `baggage/dtos/CreateBaggageItemDto.java:4-7` | `name`, `category`, `climatePreferenceId` e `seasonId` são recebidos e ignorados |
| S10 | Duplicate Code | Dispensáveis | `DestinationService.java:86,110-112,136` | incremento e decremento de contador com checagem de `null` repetidos três vezes |
| S11 | Feature Envy | Acopladores | `DestinationService.java:85-87,110-112,136-137` | o serviço manipula os contadores de `DestinationModel` em vez de a entidade cuidar deles |
| S12 | Message Chains | Acopladores | `GoogleAI/GoogleAIService.java:49-52` | `candidates[0].content.parts[0].text` navegado com casts brutos; quebra se o formato da resposta mudar |
| S13 | Inappropriate Intimacy | Acopladores | `trips/dtos/CreateTripDto.java:11-16`, `trips/dtos/TripListDto.java:15-17` | DTOs de entrada e saída carregam entidades JPA (`Users`, `DestinationModel`); a API fica presa ao modelo de banco |
| S14 | Dependência de implementação concreta | Acopladores | `TripsService.java:39`, `BaggageItemService.java:28`, `GoogleAIService.java:20` | `RestTemplate` criado com `new` e serviço concreto injetado; não dá para testar sem chamar o Gemini |

As 5 categorias estão cobertas: Inchaços (S01–S03), Abusos de OO (S04, S05), Impedidores de mudança (S06, S07), Dispensáveis (S08–S10) e Acopladores (S11–S14).

## 7. Plano de refatoração

Ordem de execução: primeiro ferramentas e testes de caracterização (rede de segurança), depois as refatorações, das mais isoladas às mais estruturais.

| PR | Tipo | Smells | Técnicas (grupo do catálogo) | Autor | Revisor |
|---|---|---|---|---|---|
| PR-00 | `chore` | — | Checkstyle, formatador, JaCoCo, CI, hooks e template de PR | João | Gabriel |
| PR-01 | `test` | — | testes de caracterização com Mockito para `TripsService`, `DestinationService`, `BaggageItemService` e `GoogleAIService`; correção do pacote do `ApiApplicationTests` | Thiago | João |
| R1 | `refactor` | S02, S12, S14 | **Extract Interface** `AiClient` (Generalização); **Extract Class** `GeminiResponseParser` (Movimentação de recursos); **Replace Error Code with Exception** (Simplificação de chamadas) | João | Thiago |
| R2 | `refactor` | S06 | **Extract Class** `TravelPromptFactory` com os prompts de destino e de bagagem (Movimentação de recursos) | Gabriel | João |
| R3 | `refactor` | S01 | **Extract Method** `resolveDestination` e `saveBaggageItems` (Composição de métodos) | Thiago | Gabriel |
| R4 | `refactor` | S07 | **Extract Method** `findOrThrow` e **Replace Exception** por `ResourceNotFoundException` com 404 (Composição de métodos, Simplificação de chamadas) | João | Gabriel |
| R5 | `refactor` | S10, S11 | **Move Method** dos contadores para `DestinationModel`; **Consolidate Duplicate Conditional Fragments** (Movimentação de recursos, Simplificação de condicionais) | Gabriel | Thiago |
| R6 | `refactor` | S04 | **Replace Conditional with Polymorphism**, com um mapa de `EVisualizationType` para a consulta (Simplificação de condicionais) | Thiago | João |
| R7 | `refactor` | S03 | **Introduce Parameter Object** `PageQuery(search, take, skip)` (Simplificação de chamadas, Organização de dados) | Gabriel | Thiago |
| R8 | `refactor` | S05, S08, S09 | **Remove Dead Code**, **Remove Parameter** e unificação de `TripSuggestionDto`/`BaggageItemDto` em `AiTextResponse` (Generalização) | João | Gabriel |

- **Grupos cobertos:** os 6 do catálogo (o mínimo é 4).
- **Volume:** 8 PRs de refatoração, além de 2 PRs de base.
- **Distribuição:** cada integrante é autor de pelo menos 2 PRs e revisor de pelo menos 3.
- **S13** (DTOs com entidades) fica como candidato extra, sujeito a tempo, porque muda o contrato JSON da API.

### 7.1 Bugs encontrados (fora da refatoração)

Refatorar preserva o comportamento. Estes bugs ficam documentados. Se a equipe decidir corrigir, cada um vira um PR `fix:` separado, com teste e registro no CHANGELOG.

| Bug | Local | Efeito |
|---|---|---|
| `createdAt` recebe `endDate` | `TripsService.java:103` | o detalhe da viagem mostra data de criação errada |
| `total` é o tamanho da página, não o total de registros | `TripsController.java:51` | a contagem de páginas da listagem sai errada |
| mensagem "Viagem não encontrada" ao excluir destino | `DestinationService.java:72` | erro enganoso |
| `passwordHash` sem `@JsonIgnore` em `Users` | `models/Users.java:38-39` | `GET /viagens` serializa o usuário e os participantes com o hash da senha |

## 8. Ferramentas

| Finalidade | Ferramenta | Versão prevista | Onde roda |
|---|---|---|---|
| Linter | Checkstyle (`google_checks.xml` ajustado para indentação de 4 espaços) | maven-checkstyle-plugin 3.6.0 | hook pre-commit e CI |
| Linter complementar | PMD | maven-pmd-plugin 3.26.0 | CI |
| Formatador | Spotless com google-java-format (estilo AOSP) | spotless-maven-plugin 2.x | hook pre-commit e CI (`spotless:check`) |
| Testes | JUnit 5, Mockito e AssertJ (via `spring-boot-starter-test`) | Spring Boot 3.4.5 | CI |
| Cobertura | JaCoCo com `check` mínimo de 50% de linhas no escopo | 0.8.12 | CI |
| Complexidade | lizard | 1.17.13 | relatório antes e depois |
| CI | GitHub Actions com JDK 17: lint, testes, cobertura e commitlint | — | todo PR para `main` |
| Hooks | Husky: `pre-commit` (Spotless e Checkstyle) e `commit-msg` (commitlint) | Husky 9 | máquina local |
| Commits | commitlint com `@commitlint/config-conventional` (Conventional Commits) | 19.x | hook e CI |
| Revisão | template de PR com checklist (ID do smell, técnica, teste, lint, cobertura) e proteção de branch | — | GitHub |
| IA | Claude (Anthropic), usado no diagnóstico inicial: medições, catálogo de smells e plano. Todo código gerado por IA será revisado por um integrante no PR, e o uso será declarado em `docs/03-ferramentas.md` | — | — |

## 9. Riscos, limitações e o que não será refatorado

| Risco ou limitação | Mitigação |
|---|---|
| O projeto inteiro (2.315 linhas) está acima do limite | escopo delimitado de 731 linhas (seção 3), sujeito à aprovação do professor |
| Os serviços dependem de PostgreSQL e da API do Gemini | testes unitários com Mockito; a interface `AiClient` (R1) permite trocar o Gemini por um dublê |
| A versão `original` precisa rodar na demonstração | ambiente com PostgreSQL local e `GOOGLE_API_KEY`; o roteiro de execução fica no README |
| R8 e S13 podem alterar o contrato JSON | R8 mantém os nomes de campo; S13 só entra com aprovação da equipe e registro no CHANGELOG |
| Prazo curto (Entrega Final em 01/12) | PR-00 e PR-01 primeiro; R1 a R8 em paralelo, 1 PR por refatoração |
| `target/classes/application.yaml` está versionado | remoção no PR-00, junto com o ajuste do `.gitignore` |

**Fora do escopo, não será refatorado:** `auth`, `security`, `configuration`, `users`, `roles`, `reviews`, `comments`, `country`, `climate`, `season`, `about`, `enums` e as entidades em `models/`. A exceção são mudanças pontuais que uma refatoração do escopo exigir, sempre registradas no PR.
