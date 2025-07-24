---
description: Set up a standardized Spring Boot 3.5.3 module architecture
---

# Spring Boot 3.5.3 Module Setup

This workflow sets up a Spring Boot 3.5.3 module following modern Java 17+ patterns and Jakarta EE standards.

**IMPORTANT**: Migrated modules are created in the existing `spring-boot-migration/` multi-module Maven structure.

## Prerequisites

- Java 17 LTS
- Spring Boot 3.5.3
- Spring Framework 6.0.x
- Jakarta EE 9+ (not javax.*)
- Maven 3.8.x+

## Existing Multi-Module Structure

The `spring-boot-migration/` directory contains a multi-module Maven project:

```
spring-boot-migration/
├── pom.xml                    # Parent POM with Spring Boot 3.5.3
├── core/                      # Core GeoServer functionality
├── common/                    # Shared utilities and components
├── services/                  # Service modules (WMS, WFS, WCS, etc.)
│   ├── pom.xml               # Services parent POM
│   ├── wms/                  # Existing WMS module
│   └── [new-module]/         # Your new service module goes here
├── rest/                      # REST API modules
├── web/                       # Web interface modules
└── docs/                      # Documentation
```

## Service Module Structure

For service modules (like `restconfig-wmts`), create in `services/[module]/`:

```
spring-boot-migration/services/[module]/
├── pom.xml                      # Module-specific dependencies
├── src/main/java/org/geoserver/[module]/
│   ├── config/
│   │   ├── [Module]Config.java      # @Configuration
│   │   └── SecurityConfig.java      # Spring Security 6.x
│   ├── controller/
│   │   └── [Module]Controller.java  # @RestController
│   ├── service/
│   │   ├── [Module]Service.java     # Interface
│   │   └── [Module]ServiceImpl.java # @Service implementation
│   ├── model/
│   │   ├── [Module].java           # Entity/Domain model
│   │   └── dto/
│   │       └── [Module]DTO.java    # Data Transfer Objects
│   └── exception/
│       └── [Module]Exception.java  # Custom exceptions
└── src/test/java/org/geoserver/[module]/
    ├── controller/
    └── service/
```

## Implementation Steps

1. **Create new service module structure**
```bash
cd spring-boot-migration/services
mkdir -p [module]/src/main/java/org/geoserver/[module]/{config,controller,service,model/dto,exception}
mkdir -p [module]/src/test/java/org/geoserver/[module]/{controller,service}
```

2. **Create pom.xml with Spring Boot 3.5.3**
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.5.3</version>
    <relativePath/>
</parent>

<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-validation</artifactId>
    </dependency>
</dependencies>
```

3. **Create Configuration class**
```java
@Configuration
@EnableWebSecurity
public class [Module]Config {
    // Bean definitions
}
```

4. **Create Controller with modern patterns**
```java
@RestController
@RequestMapping("/api/v1/[module]")
@RequiredArgsConstructor
public class [Module]Controller {
    private final [Module]Service service;
    
    @GetMapping("/{id}")
    public ResponseEntity<[Module]DTO> get(@PathVariable Long id) {
        // Implementation
    }
}
```

5. **Create Service with constructor injection**
```java
@Service
@RequiredArgsConstructor
public class [Module]ServiceImpl implements [Module]Service {
    private final [Module]Repository repository;
    
    @Override
    @Transactional(readOnly = true)
    public [Module]DTO findById(Long id) {
        // Implementation
    }
}
```

6. **Create tests with Spring Boot 3.x patterns**
```java
@SpringBootTest
class [Module]ServiceTest {
    @Test
    void whenFindById_thenReturn[Module]() {
        // AAA pattern: Arrange, Act, Assert
    }
}
```

## Key Spring Boot 3.5.3 Features

- **Jakarta EE**: Use `jakarta.*` imports (not `javax.*`)
- **Constructor Injection**: Use `@RequiredArgsConstructor` (Lombok)
- **Security 6.x**: Lambda-based configuration
- **Native Compilation**: GraalVM ready
- **Observability**: Built-in metrics and tracing

## Validation Checklist

- [ ] Java 17+ syntax used
- [ ] Jakarta EE imports (no javax.*)
- [ ] Constructor injection pattern
- [ ] Modern Spring Security config
- [ ] Proper exception handling
- [ ] Test coverage > 80%
- [ ] API documentation (OpenAPI)

Run `/java-style-validation` to verify compliance.