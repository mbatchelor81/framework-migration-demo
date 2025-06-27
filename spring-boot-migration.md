# Spring Boot 3.x Migration Requirements

## Current Versions
- Spring Framework: 5.3.39
- Spring Security: 5.8.16
- Spring Integration: 5.5.18
- Java Version: 8+ (based on dependencies)
- Hibernate: 6.4.1.Final
- Apache Wicket: 9.21.0

## Required Versions for Spring Boot 3.x

### Mandatory Requirements
- **Java Version**: Minimum Java 17 (Spring Boot 3.x requirement)
- **Spring Framework**: 6.x (automatically managed by Spring Boot 3.x)
- **Spring Security**: 6.2.1 (Latest stable)
- **Spring Integration**: 6.x (automatically managed by Spring Boot 3.x)
- **Jakarta EE**: Replace `javax.*` packages with `jakarta.*` packages
- **Hibernate**: 6.x (for Jakarta EE 9+ compatibility)

### Specific Version Updates Needed
1. **Spring Framework**: 6.0.x or newer
   - Current: 5.3.39
   - Target: 6.0.x (Latest stable)
   - Breaking changes in Spring Security authentication

2. **Spring Security**: 6.2.1
   - Current: 5.8.16
   - Target: 6.0.x (Latest stable)
   - Major changes in security configuration

3. **Spring Integration**: 6.0.x or newer
   - Current: 5.5.18
   - Target: 6.0.x (Latest stable)

4. **Hibernate**: 6.2.x or newer
   - Current: 5.6.15.Final
   - Target: 6.4.1.Final
   - Major changes in entity mapping

5. **Apache Wicket**: 10.x or newer
   - Current: 9.21.0
   - Target: 10.x (for Jakarta EE compatibility)

## Major Migration Tasks

1. **Java Updates**
   - Upgrade to Java 17
   - Update build configuration
   - Review and update deprecated Java APIs

2. **Jakarta EE Migration**
   - Replace all `javax.*` imports with `jakarta.*`
   - Update persistence.xml configurations
   - Update servlet configurations

3. **Spring Security Changes**
   - Migrate WebSecurityConfigurerAdapter (deprecated)
   - Update authentication configuration
   - Review and update custom security filters

4. **Hibernate Changes**
   - Update entity mappings
   - Review and update HQL queries
   - Update persistence configurations

5. **Dependency Management**
   - Add Spring Boot parent POM
   - Review and resolve dependency conflicts
   - Update third-party libraries for Jakarta EE compatibility

## Notes
- Consider a phased migration approach
- Test thoroughly at each step
- Pay special attention to custom REST framework compatibility
- Review Spring Boot's auto-configuration impact on GeoServer's custom components
