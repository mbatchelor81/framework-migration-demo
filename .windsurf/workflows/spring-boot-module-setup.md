---
description: Set up a standardized Spring Boot module architecture
---

# Spring Boot Module Architecture Setup

This workflow provides a standardized approach to setting up a new Spring Boot module architecture.

Use the below workflow to set up the user specified module.

## Directory Structure
Create the following directory structure in your module's `src/main/java/org/geoserver/[module]/springboot/` folder:

```
src/main/java/org/geoserver/[module]/springboot/
├── config/           # Configuration classes
│   ├── ModuleConfig        # Module-specific configuration
│   └── SecurityConfig      # Security configuration
├── controller/      # REST controllers
│   └── *Controller        # Endpoint controllers
├── service/         # Business logic layer
│   └── *Service          # Service implementations
├── model/           # Domain models and DTOs
│   └── dto/             # Data Transfer Objects
├── repository/      # Data access layer
│   └── *Repository      # Data access interfaces
└── util/            # Utility classes
    ├── ModuleException   # Module-specific exceptions
    └── ModuleUtils      # Utility functions
```

## Implementation Steps

1. **Create Base Package Structure**
   ```bash
   mkdir -p src/main/java/org/geoserver/[module]/springboot/{config,controller,service,model,repository,util}
   ```
2. **Create Main SpringBoot Service Application Java file**
   - Create `[Module]Application.java` in the root package with proper Spring Boot annotations

3. **Document Module Architecture**
   - Create `[module]-architecture.md` in the module root directory
   - Document the module structure using the following template:
   ```markdown
   # [Module] Architecture
   
   ```
   src/main/java/org/geoserver/[module]/springboot/
   ├── config/           # Configuration classes
   │   ├── [Module]Config     # Module-specific configuration
   │   └── SecurityConfig     # Security configuration
   ├── controller/      # REST controllers
   │   └── *Controller        # Endpoint controllers
   ├── service/         # Business logic layer
   │   └── *Service          # Service implementations
   ├── model/           # Domain models and DTOs
   │   └── dto/             # Data Transfer Objects
   ├── repository/      # Data access layer
   │   └── *Repository      # Data access interfaces
   └── util/            # Utility classes
       ├── [Module]Exception  # Module-specific exceptions
       └── [Module]Utils     # Utility functions
   ```
   
   ## Key Components
   - Document each component's responsibility
   - List key classes and their purposes
   - Describe module-specific patterns
   - Include design principles and decisions
   ```

4. **Configuration Setup**
   - Create `ModuleConfig` class with `@Configuration` annotation
   - Set up module-specific beans and properties
   - Configure security if needed with `SecurityConfig`

5. **Controllers**
   - Create controllers for each major endpoint
   - Use `@RestController` and proper request mappings
   - Follow REST API naming conventions
   - Implement proper error handling

6. **Services**
   - Create service interfaces and implementations
   - Use `@Service` annotation
   - Implement business logic
   - Add proper transaction management

7. **Models**
   - Create domain models
   - Add DTOs for request/response objects
   - Implement proper validation
   - Add serialization annotations if needed

8. **Repositories**
   - Create repository interfaces
   - Use Spring Data where applicable
   - Add custom queries if needed
   - Implement caching strategy

9. **Utilities**
   - Add module-specific exceptions
   - Create utility classes
   - Add common helper functions

## Best Practices

1. **Package Naming**
   - Use `org.geoserver.[module].springboot.*`
   - Keep consistent with existing modules
   - Use descriptive package names

2. **Class Naming**
   - Controllers: `*Controller`
   - Services: `*Service`, `*ServiceImpl`
   - Repositories: `*Repository`
   - Models: Clear domain names
   - Config: `*Config`

3. **Dependencies**
   - Add to module's `pom.xml`:
   ```xml
   <dependencies>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-web</artifactId>
       </dependency>
       <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-security</artifactId>
       </dependency>
       <!-- Add other needed starters -->
   </dependencies>
   ```

4. **Testing Structure**
   Create parallel test structure:
   ```
   src/test/java/org/geoserver/[module]/springboot/
   ├── controller/      # Controller tests
   ├── service/        # Service tests
   └── repository/     # Repository tests
   ```

5. **Documentation**
   - Add Javadoc to all public APIs
   - Include README.md in module root
   - Document configuration properties

## Validation

1. **Code Quality**
   - Run checkstyle
   - Verify test coverage
   - Check for proper exception handling

2. **Security**
   - Verify endpoint security
   - Check authentication/authorization
   - Validate input sanitization

3. **Testing**
   - Run unit tests
   - Execute integration tests
   - Verify API contracts

## Notes
- Replace `[module]` with your specific module name
- Adjust structure based on module requirements
- Follow team's Java style guide
- Ensure proper Spring Boot version compatibility