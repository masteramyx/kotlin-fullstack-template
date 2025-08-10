# Kotlin Fullstack Template

A production-ready template for building Kotlin Multiplatform web applications with modern architecture, comprehensive tooling, and best practices. This template provides a solid foundation for rapidly prototyping and developing full-stack applications with type-safe, shared code between frontend and backend.

## 🚀 Features

- **🔧 Kotlin Multiplatform**: Share models, utilities, and business logic between frontend and backend
- **⚡ Ktor Backend**: Lightweight, asynchronous server framework with built-in features
- **🎨 Compose Multiplatform Web**: Modern declarative UI framework for web development
- **🗄️ Type-Safe Database**: PostgreSQL with SQLDelight for compile-time verified SQL queries
- **🔐 Authentication Framework**: Session-based authentication with bcrypt password hashing (ready to extend)
- **🐳 Docker Ready**: Complete containerization with Docker Compose for development and production
- **📦 Gradle Build System**: Multi-module project structure with Kotlin DSL
- **🧪 Testing Setup**: Unit and integration testing infrastructure
- **🔄 Hot Reload**: Development server with automatic recompilation

## 🏗️ Architecture

### Project Structure
```
kotlin-fullstack-template/
├── app/                           # Ktor backend server
│   ├── src/main/kotlin/com/template/
│   │   ├── Application.kt         # Main application entry point
│   │   ├── plugins/               # Ktor plugins (routing, CORS, serialization)
│   │   └── utils/                 # Server utilities and logging
│   └── src/test/kotlin/           # Backend tests
├── web/                           # Compose Web frontend
│   └── src/jsMain/kotlin/
│       ├── Main.kt                # Frontend entry point
│       └── ApiClient.kt           # HTTP client for backend communication
├── shared/                        # Shared code between platforms
│   └── src/commonMain/kotlin/com/template/shared/
│       └── model/                 # Shared data models and DTOs
├── db/                            # Database layer
│   ├── src/main/java/com/template/db/
│   │   ├── DatabaseFactory.kt     # Database connection management
│   │   └── *Repository*.kt        # Repository pattern implementations
│   ├── src/main/sqldelight/       # SQLDelight schema definitions
│   └── init.sql                   # Database initialization script
├── docker-compose.dev.yml         # Development environment
├── Dockerfile                     # Production container
└── gradle/                        # Gradle wrapper and version catalogs
```

### Technology Stack

**Backend (JVM)**
- **Runtime**: Kotlin/JVM with coroutines for async programming
- **Framework**: Ktor 2.3.12 with Netty engine
- **Database**: PostgreSQL 15 with HikariCP connection pooling
- **Query Builder**: SQLDelight 2.0.2 for type-safe SQL
- **Serialization**: Kotlinx Serialization for JSON handling
- **Security**: BCrypt for password hashing, session-based authentication
- **Testing**: JUnit 5, MockK for mocking

**Frontend (Kotlin/JS)**
- **Framework**: Compose Multiplatform for Web (declarative UI)
- **Runtime**: Kotlin/JS with coroutines support
- **HTTP Client**: Ktor client with automatic cookie handling
- **Build Tool**: Kotlin/JS with Webpack integration
- **Styling**: CSS-in-Kotlin with type-safe styling

**Database**
- **RDBMS**: PostgreSQL 15 with JSONB support
- **Connection Pool**: HikariCP for production-grade connection management
- **Migrations**: SQL-based schema with SQLDelight validation
- **Development**: Docker containerized database with persistent volumes

**Development & Deployment**
- **Build System**: Gradle 8.5 with Kotlin DSL and version catalogs
- **Containerization**: Multi-stage Docker builds with Alpine Linux
- **Development**: Docker Compose with hot reload and automatic rebuilds
- **CI/CD Ready**: Structured for integration with GitHub Actions, GitLab CI, etc.

## 🔧 Quick Start

### Prerequisites
```bash
# Required
- JDK 11+ (recommended: JDK 17 or 21)
- Docker & Docker Compose 2.0+

# Optional (for local development without Docker)
- PostgreSQL 15+
- Node.js 18+ (for frontend debugging)
```

### Development Setup

1. **Clone and initialize:**
   ```bash
   git clone <your-repo-url> my-project
   cd my-project
   ```

2. **Start development database:**
   ```bash
   docker-compose -f docker-compose.dev.yml up -d postgres
   
   # Verify database is running
   docker-compose -f docker-compose.dev.yml logs postgres
   ```

3. **Build and run the application:**
   ```bash
   ./gradlew build
   ./gradlew :app:run
   
   # Or run with development profile
   ./gradlew :app:run --args="-Ddevelopment=true"
   ```

4. **Access the application:**
   - **Backend API**: http://localhost:8080
   - **Frontend**: http://localhost:8080/app
   - **Health Check**: http://localhost:8080/health

### Testing the Setup

```bash
# Test the Hello World API endpoint
curl http://localhost:8080/
# Expected: {"message":"Hello World from Kotlin Fullstack Template!","timestamp":"..."}

# Test database connectivity
curl http://localhost:8080/health
# Expected: {"status":"healthy","version":"1.0.0","database":"connected","timestamp":"..."}

# Run all tests
./gradlew test
```

## 🗄️ Database Configuration

The template includes a PostgreSQL database with basic user authentication schema:

**Connection Details (Development):**
- **Host**: localhost:5432
- **Database**: template_dev
- **Username**: template_user
- **Password**: template_dev_password

**Schema Overview:**
```sql
-- Core user authentication
users (id, email, password_hash, user_type, created_at, updated_at, is_active)

-- Example/demo table
sample_data (id, name, description, created_at)
```

**Database Management:**
```bash
# Connect to database
docker exec -it template-postgres psql -U template_user -d template_dev

# View schema
\dt

# Reset database
docker-compose -f docker-compose.dev.yml down -v
docker-compose -f docker-compose.dev.yml up -d postgres
```

## 🎯 Customization Guide

### 1. Update Package Names
```bash
# Replace 'com.template' with your desired package structure
find . -name "*.kt" -type f -exec sed -i '' 's/com\.template/com.yourcompany.yourproject/g' {} \;
find . -name "*.kts" -type f -exec sed -i '' 's/com\.template/com.yourcompany.yourproject/g' {} \;
```

### 2. Extend Database Schema
```sql
-- Add to db/src/main/sqldelight/com/template/db/YourTable.sq
CREATE TABLE your_table (
    id BIGSERIAL PRIMARY KEY,
    -- your columns here
);

-- Add queries
getAllYourData:
SELECT * FROM your_table;
```

### 3. Add API Endpoints
```kotlin
// In app/src/main/kotlin/com/template/plugins/Routing.kt
get("/api/your-endpoint") {
    // Your endpoint logic
    call.respond(mapOf("data" to "your response"))
}
```

### 4. Extend Frontend
```kotlin
// In web/src/jsMain/kotlin/Main.kt
@Composable
fun YourComponent() {
    // Your Compose Web UI components
}
```

## 🚀 Production Deployment

### Environment Variables
```bash
# Required for production
DATABASE_URL=postgresql://user:password@host:5432/dbname
DATABASE_USER=your_db_user
DATABASE_PASSWORD=your_secure_password

# Optional
PORT=8080
HOST=0.0.0.0
```

### Docker Production Build
```bash
# Build production image
docker build -t your-app:latest .

# Run with environment variables
docker run -d \
  -p 8080:8080 \
  -e DATABASE_URL=postgresql://... \
  -e DATABASE_USER=... \
  -e DATABASE_PASSWORD=... \
  your-app:latest
```

### Performance Considerations
- **Database**: Uses HikariCP with connection pooling (max 10 connections)
- **Frontend**: Webpack bundling with code splitting recommendations
- **Memory**: JVM configured for containerized environments
- **Logging**: Structured logging with configurable levels

## 🧪 Testing Strategy

The template includes comprehensive testing setup:

```bash
# Run all tests
./gradlew test

# Run specific module tests
./gradlew :app:test        # Backend tests
./gradlew :db:test         # Database tests
./gradlew :shared:test     # Shared code tests

# Run with coverage
./gradlew test jacocoTestReport
```

**Testing Philosophy:**
- **Unit Tests**: Pure business logic, utilities, and validation
- **Integration Tests**: Database operations with in-memory H2
- **API Tests**: End-to-end HTTP endpoint testing
- **Frontend Tests**: Component and interaction testing

## 📚 Development Guidelines

### Code Organization
- **Separation of Concerns**: Clear boundaries between layers
- **Repository Pattern**: Database access abstraction
- **Dependency Injection**: Manual DI with factory pattern
- **Error Handling**: Structured exception handling with proper HTTP status codes

### Security Best Practices
- **Password Hashing**: BCrypt with appropriate cost factor
- **SQL Injection Prevention**: Parameterized queries via SQLDelight
- **CORS Configuration**: Properly configured for development and production
- **Session Management**: Secure cookie handling

### Performance Guidelines
- **Database**: Use connection pooling, optimize queries
- **Frontend**: Lazy loading, component optimization
- **Backend**: Async/await patterns, proper coroutine usage

## 🤝 Contributing

1. **Code Style**: Follow Kotlin coding conventions
2. **Testing**: Write tests for new functionality
3. **Documentation**: Update README for architectural changes
4. **Commits**: Use conventional commit messages
5. **Build**: Ensure `./gradlew build` passes before committing

## 📄 License

MIT License - feel free to use this template for personal and commercial projects.

---

**Built with ❤️ using Kotlin Multiplatform**

*This template provides a solid foundation for building modern web applications with shared code, type safety, and production-ready infrastructure.*