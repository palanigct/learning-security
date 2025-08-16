# Spring Boot Security with JWT

This project demonstrates JWT-based authentication with Spring Security.

## Issues Fixed

1. **JWT Filter Bug**: Fixed `String.valueOf(userDetails)` to `userDetails.getUsername()` in token validation
2. **Password Encoding**: Used pre-encoded password to avoid encoding inconsistencies
3. **JWT Secret Key**: Extended secret key to meet HS256 requirements (64+ characters)
4. **Debug Logging**: Added comprehensive logging for troubleshooting

## Test Credentials

- **Username**: `admin`
- **Password**: `password`

## Testing the Login Endpoint

### Using PowerShell (Windows)
```powershell
.\test-login.ps1
```

### Using Bash/curl
```bash
chmod +x test-login.sh
./test-login.sh
```

### Manual Testing

1. **Login Request**:
   ```bash
   curl -X POST http://localhost:8080/auth/login \
     -H "Content-Type: application/json" \
     -d '{"username":"admin","password":"password"}'
   ```

2. **Use JWT Token**:
   ```bash
   curl -X GET http://localhost:8080/employees \
     -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
   ```

## Key Endpoints

- **POST** `/auth/login` - Login and get JWT token
- **GET** `/employees` - Protected endpoint (requires JWT token)
- **GET** `/h2-console` - H2 database console

## Security Configuration

- JWT-based authentication
- Stateless session management
- CSRF disabled
- Public endpoints: `/auth/**`, `/public/**`, `/swagger-ui/**`, `/h2-console/**`

## Troubleshooting

If you still get 403 errors:

1. Check the console logs for authentication details
2. Verify the JWT token is being sent in the Authorization header
3. Ensure the token hasn't expired (default: 1 hour)
4. Check that the request path matches the security configuration

## Running the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`
