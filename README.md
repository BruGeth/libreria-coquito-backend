# Coquito Backend

Inventory & sales system for Librería Coquito - Backend API built with Spring Boot.

## Description

This is a RESTful API backend for managing inventory and sales operations for Librería Coquito. The system is built using Spring Boot 4.0.1 and provides comprehensive functionality for bookstore management.

## Technologies

- **Java 17**
- **Spring Boot 4.0.1**
  - Spring Data JPA
  - Spring Security
  - Spring Web MVC
  - Spring Validation
- **MySQL** - Database
- **Lombok** - Reduce boilerplate code
- **Maven** - Dependency management and build tool

## Prerequisites

- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.9+ (or use the included Maven Wrapper)

## Installation

1. Clone the repository:
```bash
git clone https://github.com/BruGeth/libreria-coquito-backend.git
cd libreria-coquito-backend
```

2. Configure the database connection in `src/main/resources/application.yaml`:
```yaml
spring:
  application:
    name: coquito-backend
  datasource:
    url: jdbc:mysql://localhost:3306/coquito_db
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
```

3. Create the database:
```sql
CREATE DATABASE coquito_db;
```

## Running the Application

### Using Maven Wrapper (Recommended)

**On Windows:**
```cmd
mvnw.cmd spring-boot:run
```

**On Linux/Mac:**
```bash
./mvnw spring-boot:run
```

### Using Maven

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Building the Project

```bash
./mvnw clean package
```

The compiled JAR will be available in the `target/` directory.

## Running Tests

```bash
./mvnw test
```

## Project Structure

```
coquito-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── coquito/
│   │   │           └── backend/
│   │   │               ├── config/          # Configuration classes (Security, CORS, etc.)
│   │   │               ├── controller/      # REST Controllers
│   │   │               ├── dto/             # Data Transfer Objects
│   │   │               ├── entity/          # JPA Entities
│   │   │               ├── repository/      # Spring Data JPA Repositories
│   │   │               ├── service/         # Business Logic Layer
│   │   │               │   └── impl/        # Service implementations
│   │   │               ├── security/        # Security filters, providers, etc.
│   │   │               ├── exception/       # Custom exceptions and handlers
│   │   │               ├── util/            # Utility classes and helpers
│   │   │               └── CoquitoBackendApplication.java
│   │   └── resources/
│   │       ├── application.yaml
│   │       ├── static/
│   │       └── templates/
│   └── test/
│       └── java/
│           └── com/
│               └── coquito/
│                   └── backend/
├── pom.xml
└── README.md
```

## Package Structure

```
com.coquito.backend
│
├── config              # Application configuration (Security, CORS, Bean definitions)
├── controller          # REST API endpoints
├── dto                 # Request/Response objects
├── entity              # Database entities (JPA)
├── repository          # Data access layer (Spring Data JPA)
├── service             # Business logic interfaces
│   └── impl            # Service implementations
├── security            # Security configuration, JWT, filters
├── exception           # Custom exceptions and global exception handlers
└── util                # Helper classes and utilities
```

## Development

The project uses:
- **Spring Boot DevTools** for hot reload during development
- **Lombok** for reducing boilerplate code (getters, setters, constructors, etc.)
- **Spring Security** for authentication and authorization
- **Spring Data JPA** for database operations
- **Bean Validation** for request validation

## API Documentation

API documentation will be available at: `http://localhost:8080/swagger-ui.html` (once Swagger is configured)

## Contributing

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is private and proprietary to Librería Coquito.

## Contact

Project Link: [https://github.com/BruGeth/libreria-coquito-backend](https://github.com/BruGeth/libreria-coquito-backend)

## Acknowledgments

- Spring Boot Team
- Librería Coquito Team
