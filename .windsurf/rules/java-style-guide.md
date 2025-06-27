---
trigger: always_on
---

# Java and Spring Boot Style Guide

## Version Requirements

### Core Platform
- Java Version: 17 LTS
- Spring Boot: 3.5.3
- Spring Framework: 6.0.x (managed by Spring Boot)
- Spring Security: 6.2.1
- Spring Integration: 6.0.x (managed by Spring Boot)
- Hibernate: 6.4.1.Final
- Apache Wicket: 10.0.0
- Jakarta EE: 9+

### Build Tools
- Maven: 3.8.x or newer
- Gradle: 7.x or newer (if used)

## Code Style and Formatting

### General Java Style
- Use 4 spaces for indentation (no tabs)
- Line length: 120 characters maximum
- File encoding: UTF-8
- Files must end with a newline character

### Naming Conventions
- Classes: PascalCase (e.g., `UserService`)
- Interfaces: PascalCase (e.g., `UserRepository`)
- Methods: camelCase (e.g., `findByUsername()`)
- Variables: camelCase (e.g., `userCount`)
- Constants: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`)
- Packages: lowercase with dots (e.g., `com.company.module`)
- Test Classes: Name ends with "Test" (e.g., `UserServiceTest`)

## Spring Boot Best Practices

### Configuration
```java
@Configuration
public class AppConfig {
    // Prefer constructor injection over field injection
    private final UserService userService;

    public AppConfig(UserService userService) {
        this.userService = userService;
    }
}
```

### Component Organization
- Use appropriate stereotypes:
  - `@Service` for business logic
  - `@Repository` for data access
  - `@Controller` or `@RestController` for web endpoints
  - `@Configuration` for configuration classes

### Dependency Injection
- Always use constructor injection
- Avoid `@Autowired` on fields
- Use `final` fields with constructor injection
- Use `@RequiredArgsConstructor` (Lombok) for clean constructor injection

```java
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
}
```

### REST APIs
- Use `@RestController` for REST endpoints
- Follow REST naming conventions
- Use appropriate HTTP methods
- Include API versioning
- Use DTOs for request/response

```java
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        // Implementation
    }
}
```

### Security
- Use Spring Security's latest practices
- Implement method-level security with `@PreAuthorize`
- Use JWT for stateless authentication
- Implement CORS properly
- Use security headers

```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated()
            )
            .build();
    }
}
```

### Data Access
- Use Spring Data repositories
- Implement auditing with `@EntityListeners`
- Use proper transaction management
- Implement optimistic locking where appropriate

```java
@Entity
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Version
    private Long version;
}
```

### Testing
- Use `@SpringBootTest` for integration tests
- Use `@WebMvcTest` for controller tests
- Use `@DataJpaTest` for repository tests
- Follow AAA (Arrange, Act, Assert) pattern
- Use meaningful test names

```java
@SpringBootTest
class UserServiceIntegrationTest {
    @Test
    void whenCreateUser_thenUserIsSaved() {
        // Test implementation
    }
}
```

### Exception Handling
- Use `@ControllerAdvice` for global exception handling
- Create custom exceptions when needed
- Return appropriate HTTP status codes
- Provide meaningful error messages

```java
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        // Handler implementation
    }
}
```

### Logging
- Use SLF4J with Logback
- Define appropriate log levels
- Include correlation IDs
- Use structured logging

```java
private static final Logger log = LoggerFactory.getLogger(UserService.class);
```

### Performance
- Use caching appropriately
- Implement pagination for large datasets
- Use async processing when appropriate
- Profile and monitor application performance

### Documentation
- Use Javadoc for public APIs
- Include OpenAPI/Swagger documentation
- Document non-obvious implementations
- Keep README files updated

## Build and Deployment
- Use Docker for containerization
- Implement CI/CD pipelines
- Use proper profiles for different environments
- Externalize configuration

## Code Quality
- Maintain test coverage above 80%
- Use SonarQube for code quality checks
- Perform regular security audits
- Review dependencies for vulnerabilities

## Version Control
- Use meaningful commit messages
- Follow GitFlow or trunk-based development
- Review pull requests thoroughly
- Keep feature branches short-lived
