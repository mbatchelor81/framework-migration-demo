---
description: Validate the java styling in migrated code
---

# Java Style Guide Validation Workflow

This workflow validates migrated Spring Boot code against the established Java and Spring Boot style guide standards defined in `.windsurf/rules/java-style-guide.md`.

## Pre-Validation Setup

1. **Ensure Java 17 and Maven are available**
   ```bash
   java -version
   mvn -version
   ```

2. **Navigate to the module directory**
   ```bash
   cd spring-boot-migration/[module-name]
   ```

## Code Style and Formatting Validation

### 1. **Indentation and Line Length Check**
   - Verify 4 spaces for indentation (no tabs)
   - Check line length maximum of 120 characters
   - Ensure UTF-8 encoding
   - Verify files end with newline character

   **Manual Check Commands:**
   ```bash
   # Check for tabs (should return no results)
   grep -r $'\t' src/main/java/
   
   # Check line lengths over 120 characters
   find src/main/java/ -name "*.java" -exec awk 'length($0) > 120 {print FILENAME ":" NR ":" $0}' {} \;
   
   # Check file encoding
   file -bi src/main/java/**/*.java | grep -v "utf-8"
   ```

### 2. **Naming Conventions Validation**
   Check the following naming patterns:
   - **Classes**: PascalCase (e.g., `UserService`, `RestApplication`)
   - **Interfaces**: PascalCase (e.g., `UserRepository`)
   - **Methods**: camelCase (e.g., `findByUsername()`, `wrapObject()`)
   - **Variables**: camelCase (e.g., `userCount`, `objectMapper`)
   - **Constants**: UPPER_SNAKE_CASE (e.g., `MAX_RETRY_COUNT`, `ROOT_PATH`)
   - **Packages**: lowercase with dots (e.g., `org.geoserver.rest.springboot`)
   - **Test Classes**: Name ends with "Test"

   **Validation Steps:**
   ```bash
   # Check class naming (should be PascalCase)
   grep -r "^public class [a-z]" src/main/java/ || echo "✓ Class naming correct"
   
   # Check method naming (should be camelCase)
   grep -r "public.*[A-Z][a-zA-Z]*(" src/main/java/ || echo "✓ Method naming correct"
   
   # Check constant naming (should be UPPER_SNAKE_CASE)
   grep -r "static final.*[a-z]" src/main/java/ || echo "✓ Constant naming correct"
   ```

## Spring Boot Best Practices Validation

### 3. **Version Requirements Check**
   Verify the following versions in `pom.xml`:
   - Java Version: 17 LTS
   - Spring Boot: 3.5.3
   - Spring Framework: 6.0.x (managed by Spring Boot)
   - Spring Security: 6.2.1
   - Jakarta EE: 9+

   **Validation Command:**
   ```bash
   # Check Spring Boot version
   grep -A 5 -B 5 "spring-boot-starter-parent" pom.xml
   
   # Check Java version
   grep -A 3 -B 3 "maven.compiler" pom.xml
   ```

### 4. **Configuration Class Validation**
   Check that configuration classes follow best practices:
   - Use `@Configuration` annotation
   - Prefer constructor injection over field injection
   - Use `final` fields with constructor injection

   **Manual Review Points:**
   - [ ] Configuration classes use `@Configuration`
   - [ ] Constructor injection is used instead of `@Autowired` fields
   - [ ] Fields are marked `final` where appropriate
   - [ ] No `@Autowired` on fields (use constructor injection)

### 5. **Component Organization Validation**
   Verify appropriate stereotype usage:
   - `@Service` for business logic
   - `@Repository` for data access
   - `@Controller` or `@RestController` for web endpoints
   - `@Configuration` for configuration classes

   **Validation Steps:**
   ```bash
   # Check for proper controller annotations
   grep -r "@RestController" src/main/java/
   
   # Check for proper service annotations
   grep -r "@Service" src/main/java/
   
   # Check for proper configuration annotations
   grep -r "@Configuration" src/main/java/
   ```

### 6. **Dependency Injection Validation**
   Ensure proper dependency injection patterns:
   - Constructor injection is used
   - No `@Autowired` on fields
   - `final` fields with constructor injection

   **Manual Review Points:**
   - [ ] All dependencies use constructor injection
   - [ ] No `@Autowired` annotations on fields
   - [ ] Dependency fields are marked `final`
   - [ ] Constructor parameters match injected dependencies

### 7. **REST API Standards Validation**
   Check REST controller implementation:
   - Use `@RestController` for REST endpoints
   - Follow REST naming conventions
   - Use appropriate HTTP methods
   - Include proper request mappings
   - Use DTOs for request/response

   **Manual Review Points:**
   - [ ] Controllers use `@RestController`
   - [ ] Request mappings follow REST conventions
   - [ ] HTTP methods are appropriate (GET, POST, PUT, DELETE)
   - [ ] DTOs are used for request/response objects
   - [ ] Proper exception handling is implemented

### 8. **Security Configuration Validation**
   Verify security implementation:
   - Use Spring Security 6.x patterns
   - Implement method-level security with `@PreAuthorize` where needed
   - Proper CORS configuration
   - Security headers implementation

   **Manual Review Points:**
   - [ ] Security configuration uses Spring Security 6.x patterns
   - [ ] `@EnableWebSecurity` is present
   - [ ] SecurityFilterChain bean is properly configured
   - [ ] CORS is configured appropriately
   - [ ] Security headers are implemented

## Testing Validation

### 9. **Test Structure and Naming**
   Verify test implementation follows standards:
   - Test classes end with "Test"
   - Use appropriate test annotations
   - Follow AAA (Arrange, Act, Assert) pattern
   - Meaningful test names

   **Validation Steps:**
   ```bash
   # Check test class naming
   find src/test/java/ -name "*.java" | grep -v "Test.java$" || echo "✓ Test naming correct"
   
   # Check for test annotations
   grep -r "@Test\|@SpringBootTest\|@WebMvcTest" src/test/java/
   ```

### 10. **Exception Handling Validation**
    Check exception handling implementation:
    - Use `@ControllerAdvice` for global exception handling
    - Custom exceptions when needed
    - Appropriate HTTP status codes
    - Meaningful error messages

    **Manual Review Points:**
    - [ ] Global exception handler uses `@ControllerAdvice`
    - [ ] Custom exceptions are properly defined
    - [ ] HTTP status codes are appropriate
    - [ ] Error messages are meaningful and user-friendly

## Documentation and Code Quality

### 11. **Documentation Standards**
    Verify documentation quality:
    - Javadoc for public APIs
    - Class-level documentation
    - Method documentation for complex logic
    - Package documentation where appropriate

    **Manual Review Points:**
    - [ ] Public classes have Javadoc comments
    - [ ] Public methods have Javadoc comments
    - [ ] Complex logic is documented
    - [ ] Package-info.java exists where needed

### 12. **Logging Standards**
    Check logging implementation:
    - Use SLF4J with Logback
    - Appropriate log levels
    - Proper logger instantiation

    **Validation Steps:**
    ```bash
    # Check for proper logger usage
    grep -r "Logger.*=.*LoggerFactory\|Logging.getLogger" src/main/java/
    
    # Check for System.out.println (should not exist)
    grep -r "System.out.print" src/main/java/ && echo "❌ Found System.out usage" || echo "✓ No System.out usage"
    ```

## Build and Compilation Validation

### 13. **Maven Build Validation**
    Ensure the module builds successfully:
    ```bash
    # Clean and compile
    mvn clean compile
    
    # Run tests
    mvn test
    
    # Package the application
    mvn package -DskipTests
    ```

### 14. **Dependency Analysis**
    Check for proper dependency management:
    ```bash
    # Check for dependency conflicts
    mvn dependency:tree
    
    # Check for unused dependencies
    mvn dependency:analyze
    ```

## Final Validation Checklist

### Code Style Compliance
- [ ] 4 spaces indentation (no tabs)
- [ ] Line length ≤ 120 characters
- [ ] UTF-8 encoding
- [ ] Files end with newline
- [ ] Proper naming conventions (PascalCase, camelCase, UPPER_SNAKE_CASE)

### Spring Boot Compliance
- [ ] Correct version requirements (Java 17, Spring Boot 3.5.3, etc.)
- [ ] Constructor injection used
- [ ] Proper component annotations (@Service, @RestController, @Configuration)
- [ ] Security configuration follows Spring Security 6.x patterns
- [ ] REST APIs follow conventions

### Code Quality
- [ ] Proper exception handling
- [ ] Appropriate logging (SLF4J)
- [ ] Javadoc documentation for public APIs
- [ ] Test classes follow naming conventions
- [ ] No System.out.println usage

### Build and Dependencies
- [ ] Maven build successful
- [ ] Tests pass
- [ ] No dependency conflicts
- [ ] No unused dependencies

## Validation Report

After completing all validation steps, create a summary report:

```markdown
# Validation Report for [Module Name]

## ✅ Passed Validations
- List all passed validation points

## ❌ Failed Validations
- List any failed validation points with specific details

## 🔧 Recommendations
- List recommendations for improvements

## 📋 Action Items
- List specific action items to address failed validations
```

## Notes
- This validation should be run after each major migration milestone
- Address any failed validations before proceeding to the next module
- Update this workflow as new standards are established
- Consider integrating automated checks into CI/CD pipeline