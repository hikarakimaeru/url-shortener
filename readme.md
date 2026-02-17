# URL Shortener

A simple REST API service for shortening URLs and redirecting to the original address.  
Built with **Spring Boot**, **Spring Data JPA**, and **PostgreSQL**, with full **URL validation** and interactive **Swagger UI** documentation.

---

## Technologies

- Java 24
- Spring Boot 4
- Spring Data JPA / Hibernate
- PostgreSQL
- Swagger / OpenAPI 3

---

## Installation

1. **Clone the repository**:
```bash
git clone https://github.com/username/url-shortener.git
cd url-shortener
```
2. **Configure the database**:
   Make sure PostgreSQL is running and create a database named `url_shortener`.
   Update `src/main/resources/application.properties` with your credentials.
   *(Note: It is recommended to use environment variables for database credentials to avoid exposing real passwords).*

```properties
spring.application.name=demo
spring.datasource.url=jdbc:postgresql://localhost:5432/url_shortener
spring.datasource.username=${DB_USER:shortener_user}
spring.datasource.password=${DB_PASSWORD:password123}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

shortener.base-url=http://localhost:8080/
```

Build and run the project:
```
mvn clean install
mvn spring-boot:run
```

### Swagger UI usage ###
Access the API documentation and test endpoints directly from your browser:
http://localhost:8080/swagger-ui/index.html

### Example Endpoints

#### 1. Shorten a URL
**POST** `/api/shorten`

**Request Body:**
```json
{
  "originalUrl": "https://www.example.com/very/long/url"
}
```
**Response:**
```
{
"shortenedUrl": "http://localhost:8080/Z8FcxL",
"originalUrl": "https://www.example.com/very/long/url"
}
```

#### 2. Redirect to original URL
**GET** `/{key}`  
Redirects the user to the original long URL.

---

## Configuration

| Property | Description |
|---|---|
| `spring.datasource.url` | JDBC URL for PostgreSQL |
| `spring.datasource.username` | Database username (override with `DB_USER` env var) |
| `spring.datasource.password` | Database password (override with `DB_PASSWORD` env var) |
| `spring.jpa.hibernate.ddl-auto` | Schema auto-update strategy (`update` is recommended for dev only) |
| `shortener.base-url` | Base URL for generated short links |

---

Contributions are welcome! Please fork the repo and create a pull request with your changes.