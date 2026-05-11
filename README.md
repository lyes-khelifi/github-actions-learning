# GitHub Actions Learning Project

A comprehensive example project demonstrating modern Java development practices with Spring Boot, thorough testing, and automated CI/CD with GitHub Actions.

## 🎯 Learning Objectives

1. **Spring Boot Fundamentals**
   - REST API design with Spring Web MVC
   - Dependency injection with Spring Boot
   - Application structure and best practices

2. **Testing Methodologies**
   - Unit testing with JUnit 5
   - Integration testing with SpringBootTest
   - Test-driven development (TDD) patterns

3. **CI/CD Implementation**
   - GitHub Actions workflows for CI and CD
   - Code quality enforcement (Checkstyle, SpotBugs)
   - Docker image automation
   - Test coverage reporting with JaCoCo

4. **Containerization**
   - Docker multi-stage builds
   - Application packaging as executable JAR
   - Container deployment patterns

## 📁 Project Structure

```
src/main/java/org/example/
├── Application.java               # Main application entry point
├── service/
│   └── GreetingService.java       # Business logic
├── controller/
│   └── GreetingController.java    # REST API endpoints
├── model/
│   └── Greeting.java              # Data transfer object
├── util/
│   └── Constants.java             # Constants and configuration keys

src/test/java/org/example/
├── service/
│   └── GreetingServiceTest.java   # Unit tests
├── controller/
│   └── GreetingControllerIntegrationTest.java  # Integration tests
├── util/
│   └── ConstantsTest.java         # Constants validation tests

.github/workflows/ci.yml           # GitHub Actions CI/CD pipeline
Dockerfile                         # Multi-stage Docker build configuration
pom.xml                            # Maven project configuration
```

## 🛠️ Getting Started

### Local Development
```bash
# 1. Clone the repository
git clone https://github.com/yourusername/github-actions-learning.git
cd github-actions-learning

# 2. Build and run locally
mvn spring-boot:run

# 3. Access the API endpoint
GET http://localhost:8080/api/greeting/World

# 4. Run unit tests
mvn test

# 5. Run integration tests
mvn verify
```

### Code Quality Checks
```bash
# Check for code style violations
mvn checkstyle:check

# Run static security analysis
mvn spotbugs:check

# View JaCoCo coverage report
open target/site/jacoco/index.html
```

## 🤖 CI/CD Pipeline: `.github/workflows/ci.yml`

The pipeline performs comprehensive quality checks and automated deployments:

1. **Build Stage**
   - Checks out repository and sets up JDK 26
   - Runs full Maven verification with test coverage
   - Enforces code quality standards

2. **Testing Stage**
   - Executes unit and integration tests
   - Generates JaCoCo code coverage report
   - Uploads coverage report as artifact

3. **Quality Assurance**
   - Checkstyle configuration validation
   - SpotBugs security vulnerability detection

4. **Deployment Stage**
   - Package application as optimized JAR
   - Build multi-stage Docker images
   - Deploy to GitHub Container Registry
   - Automatic deployment on pushes to main/master

## 📊 GitHub Actions Status

[![CI](https://github.com/yourusername/github-actions-learning/actions/workflows/ci.yml/badge.svg)](https://github.com/yourusername/github-actions-learning/actions/workflows/ci.yml)
[![GitHub Container Registry](https://img.shields.io/badge/GitHub%20Container%20Registry-available-blue)](https://ghcr.io/yourusername/github-actions-learning)

## 🏗️ Docker Integration

The project includes a production-ready Docker setup using multi-stage builds:

### Build Process
```bash
# Build from repository root
docker build -t my-app:latest .
```

### Run Application
```bash
docker run -p 8080:8080 my-app:latest
```

### Access Endpoints
```bash
# Health check
GET http://localhost:8080/actuator/health

# Greeting API
GET http://localhost:8080/api/greeting/World

# Improved greeting with message customization
POST /api/greeting
{
  "name": "World",
  "message": "Custom greeting format"
}
```

## 📁 Dockerfile Breakdown

```dockerfile
# Build stage (maven:3.9.9-eclipse-temurin-26 AS build)
FROM maven:3.9.9-eclipse-temurin-26 AS build
WORKDIR /app
COPY . /app
RUN mvn -B package -DskipTests  # Build JAR without running tests in container

# Runtime stage (openjdk:26-jdk)
FROM openjdk:26-jdk
WORKDIR /app
COPY --from=bui $$app/target/*.jar app.jar  # Copy built JAR from build stage
EXPOSE 8080                                 # Expose Spring Boot port
ENTRYPOINT ["java", "-jar", "app.jar"]      # Execute Spring Boot application
```

## 📈 Best Practices Demonstrated

1. **Separation of Concerns**
   - Strict separation between service, controller, and model layers
   - Clear dependency boundaries

2. **Test Automation**
   - Test coverage enforcement through Maven lifecycle
   - Integration testing with Spring context

3. **Production Readiness**
   - Multi-stage Docker builds minimizing final image size
   - Proper JAR packaging for Spring Boot
   - Executable application configuration

4. **Pipeline Robustness**
   - Comprehensive test automation
   - Tooling for code quality enforcement
   - Versioned artifact deployment
   - Health check endpoints

## 🤔 Advanced Topics to Explore

1. **Microservices Architecture Patterns**
   - Service discovery with Consul/Eureka
   - Configuration management with Spring Cloud ConfigServer
   - Circuit breaker patterns with Resilience4j

2. **Continuous Deployment**
   - Blue-green deployment strategies
   - Canary release configurations
   - Kubernetes deployment automation

3. **Performance Optimization**
   - Application profiling with Micrometer
   - Load testing with JMeter
   - Connection pooling configuration

4. **Security Enhancements**
   - JWT authentication implementation
   - Secret management with HashiCorp Vault
   - Dynamic security policy routing

5. **Event-Driven Architecture**
   - Spring Integration for messaging patterns
   - Message brokering with RabbitMQ/Kafka
   - Event sourcing architecture patterns

## 🤝 Contributing

Contributions are welcome! Please feel free to submit pull requests that:
- Add new learning examples
- Demonstrate alternative architectural patterns
- Improve test coverage
- Enhance CI/CD pipelines with new configurations

## 📚 Resources for Further Learning

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/)
- [GitHub Actions Official Guide](https://docs.github.com/en/actions)
- [Effective Java (Joshua Bloch)](https://amzn.to/3QLMGaJ)
- [Martin Fowler's Patterns of Enterprise Application Architecture](https://patterns.arrange.org/)
- [Continuous Delivery (Jez Humble)](https://leanpub.com/continuousdeliver)

---

This project serves as a complete template for understanding modern Java application development with enterprise-grade practices. Each component demonstrates specific patterns and technologies commonly used in professional software engineering environments.