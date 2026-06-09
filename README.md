# ⚽ A3 Soccer - Plataforma de Análise de Futebol

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.13-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17-blue)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED)

## 📋 Descrição do Projeto

**A3 Soccer** é uma plataforma de análise e visualização de dados de futebol em tempo real. A aplicação integra dados de múltiplas ligas internacionais (Brasileirão, Premier League, La Liga, Serie A, Bundesliga) através da API **API-Sports** e fornece análises detalhadas sobre gols, partidas e estatísticas por temporada.

### Principais Funcionalidades

- 📊 **Dashboard Interativo**: Visualização de estatísticas de gols por liga e temporada
- 🔄 **Cache Inteligente**: Sistema de cache Caffeine com expiração configurável (2 horas)
- 🗄️ **Persistência em Banco de Dados**: PostgreSQL para armazenamento permanente de dados
- 📈 **Análise de Gols**: Cálculo automático de total de gols e média por liga
- ⏱️ **Rate Limiting**: Controle de requisições à API externa (delay configurável entre chamadas)
- 🔍 **API RESTful**: Endpoints bem definidos para acesso aos dados

---

## 🛠️ Tecnologias Utilizadas

### Backend
- **Java 17**: Linguagem de programação
- **Spring Boot 3.5.13**: Framework web e IoC container
- **Spring Data JPA**: ORM para persistência de dados
- **Spring Cache (Caffeine)**: Cache em memória com políticas de expiração
- **Spring Thymeleaf**: Template engine para renderização HTML

### Banco de Dados
- **PostgreSQL 17**: Banco de dados relacional
- **JPA/Hibernate**: ORM para mapeamento objeto-relacional

### Bibliotecas Adicionais
- **Jackson**: Serialização/deserialização JSON
- **Dozer Mapper**: Mapeamento automático entre DTOs e entidades
- **Lombok**: Redução de boilerplate com annotations
- **SLF4J + Logback**: Logging estruturado

### DevOps & Containerização
- **Docker**: Containerização da aplicação
- **Docker Compose**: Orquestração de múltiplos containers (App + PostgreSQL)

### APIs Externas
- **API-Sports (football-api-v3)**: Fonte de dados de fixtures e ligaspontuais

---

## 📦 Estrutura do Projeto

```
a3_soccer/
├── src/
│   ├── main/
│   │   ├── java/com/a3_soccer/
│   │   │   ├── A3SoccerApplication.java          # Classe principal
│   │   │   ├── client/
│   │   │   │   └── SoccerClient.java             # Cliente HTTP para API-Sports
│   │   │   ├── config/
│   │   │   │   ├── CacheConfig.java              # Configuração do Caffeine Cache
│   │   │   │   └── DozerConfig.java              # Configuração do Dozer Mapper
│   │   │   ├── controller/
│   │   │   │   ├── PageController.java           # Endpoints para páginas HTML
│   │   │   │   └── SoccerController.java         # Endpoints REST para dados
│   │   │   ├── dto/
│   │   │   │   ├── LeagueApiResponseDTO.java     # DTO para resposta da API
│   │   │   │   ├── LeagueDTO.java                # DTO genérico de liga
│   │   │   │   └── LeagueWrapperDTO.java         # Wrapper para dados de liga
│   │   │   ├── entity/
│   │   │   │   └── League.java                   # Entidade JPA (tabela league)
│   │   │   ├── enums/
│   │   │   │   └── Ligas.java                    # Enum com IDs das ligas
│   │   │   ├── repository/
│   │   │   │   └── LeagueRepository.java         # Interface JPA Repository
│   │   │   └── service/
│   │   │       ├── AnalyticsService.java         # Lógica de análise (gols, partidas)
│   │   │       └── PageService.java              # Lógica para carregamento inicial
│   │   ├── resources/
│   │   │   ├── application.properties            # Configurações da aplicação
│   │   │   ├── static/                           # Arquivos estáticos (CSS, JS)
│   │   │   │   ├── style.css
│   │   │   │   ├── script.js
│   │   │   │   └── images/
│   │   │   └── templates/
│   │   │       └── dashboard.html                # Template principal
│   └── test/
│       └── java/com/a3_soccer/
│           └── A3SoccerApplicationTests.java     # Testes unitários
├── docker-compose.yaml                           # Orquestração Docker
├── pom.xml                                       # Dependências Maven
└── README.md                                     # Este arquivo
```

---

## 🚀 Como Subir a Aplicação

### Pré-requisitos

- **Java 17+** instalado
- **Maven 3.6+** instalado
- **Docker** e **Docker Compose** instalados (para rodar com containers)
- **Git** para clonar o repositório

### Opção 1: Rodar com Docker Compose (Recomendado)

#### Passo 1: Clonar o Repositório
```bash
git clone https://github.com/seu-usuario/a3_soccer.git
cd a3_soccer
```

#### Passo 2: Subir os Containers
```bash
docker-compose up -d
```

Isso irá:
- Criar e iniciar o container PostgreSQL (porta 5432)
- Compilar a aplicação Spring Boot
- Iniciar a aplicação na porta 8080

#### Passo 3: Verificar Status
```bash
docker-compose ps
```

Você deve ver dois containers rodando: `postgres` e `a3_soccer`.

#### Passo 4: Acessar a Aplicação
- **Dashboard**: http://localhost:8080/
- **API REST**: http://localhost:8080/ustj/analytics/goals?season=2024

#### Parar os Containers
```bash
docker-compose down
```

#### Remover Volumes (Dados do Banco)
```bash
docker-compose down -v
```

---

### Opção 2: Rodar Localmente (Sem Docker)

#### Passo 1: Instalar PostgreSQL Localmente

**Windows (usando PostgreSQL Installer):**
1. Baixe o instalador em https://www.postgresql.org/download/windows/
2. Execute e configure com:
   - Porta: 5432
   - Usuário: soccer
   - Senha: soccer123
   - Database: soccer_db

**macOS (usando Homebrew):**
```bash
brew install postgresql
brew services start postgresql
createdb soccer_db
```

**Linux (Debian/Ubuntu):**
```bash
sudo apt-get install postgresql postgresql-contrib
sudo -u postgres createdb soccer_db
sudo -u postgres psql -c "CREATE USER soccer WITH PASSWORD 'soccer123';"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE soccer_db TO soccer;"
```

#### Passo 2: Clonar e Compilar
```bash
git clone https://github.com/seu-usuario/a3_soccer.git
cd a3_soccer
```

#### Passo 3: Compilar com Maven
```bash
./mvnw.cmd clean package  # Windows
./mvnw clean package      # macOS/Linux
```

#### Passo 4: Executar a Aplicação
```bash
./mvnw.cmd spring-boot:run  # Windows
./mvnw spring-boot:run      # macOS/Linux
```

Ou diretamente com Java:
```bash
java -jar target/a3_soccer-0.0.1-SNAPSHOT.jar
```

#### Passo 5: Acessar a Aplicação
- **Dashboard**: http://localhost:8080/
- **API REST**: http://localhost:8080/ustj/analytics/goals?season=2024

---

## 📡 Endpoints Principais

### Dashboard e Páginas HTML
- `GET /` - Página principal (dashboard)
- `GET /dashboard` - Dashboard de estatísticas

### API REST - Analytics
- `GET /ustj/analytics/goals?season=2024` - Retorna total de gols por liga para uma temporada
- `GET /ustj/analytics/goals?season=2020` - Retorna dados para temporada específica (2020-2024)

#### Exemplo de Resposta:
```json
{
  "Brasileirão": 929,
  "Premier League": 1115,
  "La Liga": 995,
  "Serie A": 973,
  "Bundesliga": 966
}
```

### API REST - Dados de Ligas
- `GET /ustj/leagues?id=71` - Detalhes de uma liga específica
- `GET /ustj/teams?league=71&season=2024` - Times de uma liga em uma temporada
- `GET /ustj/scorers` - Top artilheiros

### API REST - Test
- `GET /ustj/test-fixtures` - Testa conexão com API (usa Premier League 2024)

---

## ⚙️ Configurações

### application.properties

```properties
# Aplicação
spring.application.name=a3_soccer

# Banco de Dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/soccer_db
spring.datasource.username=soccer
spring.datasource.password=soccer123

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Cache Caffeine
spring.cache.type=caffeine
logging.level.org.springframework.cache=TRACE

# Rate Limiting - Delay entre requisições à API (em ms)
app.api.delay-ms=500

# Logging
logging.level.com.a3_soccer.service=INFO
```

### Variáveis de Ambiente

Você pode sobrescrever as propriedades usando variáveis de ambiente:

```bash
# Windows (PowerShell)
$env:SPRING_DATASOURCE_URL="jdbc:postgresql://seu-host:5432/soccer_db"
$env:APP_API_DELAY_MS="1000"
java -jar target/a3_soccer-0.0.1-SNAPSHOT.jar

# macOS/Linux
export SPRING_DATASOURCE_URL="jdbc:postgresql://seu-host:5432/soccer_db"
export APP_API_DELAY_MS="1000"
java -jar target/a3_soccer-0.0.1-SNAPSHOT.jar
```

---

## 🔧 Ajuste de Performance e Rate Limit

### Rate Limiting (Delay entre Requisições)

A API-Sports tem limites de requisições por segundo. Por padrão, a aplicação aguarda **500ms** entre cada chamada para evitar erro **429 (Too Many Requests)**.

Para aumentar o delay (recomendado se ainda receber 429):

**Opção 1: Editar application.properties**
```properties
app.api.delay-ms=1000  # Aguarda 1 segundo entre requisições
```

**Opção 2: Passar como System Property**
```bash
java -Dapp.api.delay-ms=2000 -jar target/a3_soccer-0.0.1-SNAPSHOT.jar
```

**Opção 3: Variável de Ambiente**
```bash
export APP_API_DELAY_MS=1500
java -jar target/a3_soccer-0.0.1-SNAPSHOT.jar
```

### Cache Configuration

O cache Caffeine está configurado em `CacheConfig.java`:

```java
.maximumSize(100)          // Máximo de 100 entradas no cache
.expireAfterWrite(2, TimeUnit.HOURS)  // Expira após 2 horas
```

Para aumentar o tempo de expiração, edite `src/main/java/com/a3_soccer/config/CacheConfig.java`.

---

## 🗄️ Modelo de Dados

### Tabela: league

```sql
CREATE TABLE league (
    database_id BIGSERIAL PRIMARY KEY,
    id INTEGER NOT NULL,                    -- ID da API-Sports
    season INTEGER,                         -- Ano da temporada (ex: 2024, 2023)
    name VARCHAR(255),                      -- Nome da liga (ex: "Brasileirão")
    type VARCHAR(50),                       -- Tipo (ex: "League")
    logo TEXT,                              -- URL do logo
    partidas INTEGER,                       -- Total de partidas
    total_gols BIGINT,                      -- Total de gols
    UNIQUE(id, season)                      -- Uma liga por season
);
```

### Entidade: League.java

- `databaseId` (Long): Chave primária auto-incrementada
- `id` (Integer): ID da liga na API-Sports
- `season` (Integer): Ano da temporada (NULL para metadados)
- `name` (String): Nome da liga
- `type` (String): Tipo de competição
- `logo` (String): URL do logo
- `partidas` (Integer): Total de partidas
- `totalGols` (Long): Total de gols marcados

---

## 📊 Ligas Suportadas

As seguintes ligas são atualmente suportadas (configuradas no enum `Ligas.java`):

| Liga | ID API-Sports | País |
|------|----------------|------|
| Brasileirão | 71 | Brasil 🇧🇷 |
| Premier League | 39 | Inglaterra 🏴󠁧󠁢󠁥󠁮󠁧󠁿 |
| La Liga | 140 | Espanha 🇪🇸 |
| Serie A | 135 | Itália 🇮🇹 |
| Bundesliga | 78 | Alemanha 🇩🇪 |

Para adicionar uma nova liga, edite `src/main/java/com/a3_soccer/enums/Ligas.java`.

---

## 🐛 Troubleshooting

### Erro: "Connection refused" ao conectar no PostgreSQL

**Causa**: PostgreSQL não está rodando ou não está acessível.

**Solução Docker**:
```bash
docker-compose up -d
docker-compose logs postgres  # Verificar logs
```

**Solução Local**:
```bash
# Windows
net start PostgreSQL17

# macOS
brew services start postgresql

# Linux
sudo systemctl start postgresql
```

### Erro: "429 Too Many Requests"

**Causa**: Taxa de requisições à API-Sports excedida.

**Solução**: Aumentar `app.api.delay-ms` em `application.properties` ou por variável de ambiente:
```bash
export APP_API_DELAY_MS=2000
java -jar target/a3_soccer-0.0.1-SNAPSHOT.jar
```

### Cache Não Funciona / Retorna Dados Antigos

**Causa**: Cache Caffeine é em memória; ao reiniciar a app, é limpo.

**Solução**: Para persistência entre restarts, configure Redis como cache distribuído (veja `CacheConfig.java`).

### Dados de Temporada Diferente Sendo Retornados

**Causa**: Season não está sendo normalizado corretamente ou parâmetro foi passado incorretamente.

**Solução**: Sempre use o parâmetro nomeado:
```
✅ Correto:   http://localhost:8080/ustj/analytics/goals?season=2020
❌ Incorreto: http://localhost:8080/ustj/analytics/goals?2020
```

### Erro ao Compilar: "Java 17 não encontrado"

**Solução**: Instale Java 17 ou configure JAVA_HOME:
```bash
# Windows
set JAVA_HOME=C:\Program Files\Java\jdk-17
mvnw.cmd clean package

# macOS/Linux
export JAVA_HOME=/usr/libexec/java_home -v 17
./mvnw clean package
```

---

## 📝 Variáveis de Ambiente Disponíveis

| Variável | Padrão | Descrição |
|----------|--------|-----------|
| `SPRING_DATASOURCE_URL` | jdbc:postgresql://localhost:5432/soccer_db | URL do PostgreSQL |
| `SPRING_DATASOURCE_USERNAME` | soccer | Usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | soccer123 | Senha do banco |
| `APP_API_DELAY_MS` | 500 | Delay entre requisições (ms) |
| `LOGGING_LEVEL_COM_A3_SOCCER_SERVICE` | INFO | Nível de log do serviço |
| `SERVER_PORT` | 8080 | Porta da aplicação |

---

## 📚 Documentação Adicional

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [API-Sports Football API](https://api-sports-io.p.rapidapi.com/api/football)
- [Docker Documentation](https://docs.docker.com/)
- [PostgreSQL Documentation](https://www.postgresql.org/docs/)

---

## 👨‍💻 Desenvolvimento

### Padrões de Código

- Clean Code principles
- Design Patterns: Repository, Service, Controller
- DTOs para transferência de dados
- Logging estruturado com SLF4J
- Cache-first strategy para otimização

---

**Última atualização**: Junho 2026

**Desenvolvido com ❤️ usando Spring Boot**


