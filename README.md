# Spring Boot Security with JWT & HTTPS

This project demonstrates a modern, production-ready Spring Boot application with:
- JWT-based authentication using Spring Security
- H2 database integration
- HTTPS (TLS) enabled on port 443
- Log4j2 logging with Lombok
- Clean, robust, and well-documented codebase

## Features
- Secure JWT authentication for REST APIs
- H2 in-file database for development
- HTTPS enabled by default (production-ready)
- HTTP to HTTPS redirection (optional for local/dev)
- H2 console access (for development only)
- Swagger/OpenAPI documentation
- Modern code: Lombok, constructor injection, best practices

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### HTTPS Keystore Setup
1. **Production:** Use a CA-signed certificate and convert it to PKCS12 format as `keystore.p12`.
2. **Development:** Generate a self-signed certificate:
   ```sh
   keytool -genkeypair -alias learning-security -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650 -storepass changeit -dname "CN=localhost,OU=Dev,O=YourOrg,L=YourCity,S=YourState,C=IN"
   ```
3. Place `keystore.p12` in `src/main/resources` or update the path in `application.properties`.

### Configuration
Edit `src/main/resources/application.properties`:
- For **production** (default):
  ```properties
  server.port=443
  server.ssl.enabled=true
  server.ssl.key-store=classpath:keystore.p12
  server.ssl.key-store-password=changeit
  server.ssl.key-store-type=PKCS12
  server.ssl.key-alias=learning-security
  ```
- For **local development** (optional):
  ```properties
  server.port=8080
  server.ssl.enabled=false
  ```

### Build & Run
```sh
mvn clean package
java -jar target/learning-security-0.0.1-SNAPSHOT.jar
```

- Access API: https://localhost/
- Access Swagger UI: https://localhost/swagger-ui.html
- Access H2 Console (dev only): https://localhost/h2-console

## Test Credentials
- **Username**: `admin`
- **Password**: `password`

## Example: Testing the Login Endpoint
```bash
curl -k -X POST https://localhost/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'
```

## Key Endpoints
- **POST** `/auth/login` - Login and get JWT token
- **GET** `/employee/get/all` - Protected endpoint (requires JWT token)
- **GET** `/h2-console` - H2 database console (dev only)
- **GET** `/swagger-ui.html` - Swagger/OpenAPI UI

## Security Notes
- For production, use a CA-signed certificate and secure your keystore password.
- Remove or restrict H2 console and Swagger UI in production.
- All HTTP traffic should be redirected to HTTPS (see `HttpToHttpsRedirectConfig`).

## Logging
- Uses Log4j2 with Lombok's @Log4j2 for modern, performant logging.
- Log configuration: `src/main/resources/log4j2.xml`

## Documentation
- All classes and methods are documented with Javadoc for maintainability.

---

For more details, see the code and configuration files in the repository.
