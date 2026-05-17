# GitHub Actions Learning Project

A Spring Boot project demonstrating modern Java development practices — REST APIs, JWT security, BDD testing with Serenity, and a full CI/CD pipeline using GitHub Actions.

## Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 3.2.5 |
| Language | Java 17 |
| Build | Maven |
| Security | Spring Security + JWT |
| Database | H2 (in-memory) + Flyway migrations |
| Testing | JUnit 5, Serenity BDD, REST Assured, MockMvc |
| Coverage | JaCoCo |
| Static analysis | SpotBugs |
| Containerisation | Docker + GitHub Container Registry |
| CI/CD | GitHub Actions |
| Test reports | Serenity HTML (published to GitHub Pages) |

## Project Structure

```
src/main/java/org/example/
├── Application.java
├── controller/
│   ├── GreetingController.java
│   ├── HealthController.java
│   └── UserController.java
├── service/
│   ├── GreetingService.java
│   └── UserService.java
├── entity/
│   └── UserEntity.java
├── repository/
│   └── UserRepository.java
├── dto/
│   └── UserDTO.java
├── security/
│   ├── AuthenticationRequest.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtUtil.java
│   └── SecurityConfig.java
└── util/
    └── Constants.java

src/test/java/org/example/
├── SampleTest.java
├── controller/
│   ├── GreetingControllerIntegrationTest.java
│   └── HealthControllerTest.java
├── integration/
│   └── UserIntegrationTest.java
├── security/
│   └── JwtUtilTest.java
├── service/
│   ├── GreetingServiceTest.java
│   └── UserServiceTest.java
├── steps/
│   └── ApiSteps.java          # Serenity @Step definitions
└── util/
    └── ConstantsTest.java

.github/workflows/
├── test-gate.yml              # PR quality gate
└── build-deploy.yml           # Build, deploy, and publish report
```

## Getting Started

```bash
# Clone and build
git clone <repo-url>
cd github-actions-learning
mvn spring-boot:run

# Run tests
mvn test

# View Serenity report locally (serve via HTTP to avoid browser restrictions)
cd target/site/serenity
python -m http.server 8000
# Open http://localhost:8000
```

## API Endpoints

| Method | Path | Description |
|---|---|---|
| GET | `/api/greeting/{name}` | Returns a personalised greeting |
| GET | `/api/health` | Returns service health status |
| GET | `/api/metrics` | Returns system metrics |
| POST | `/api/users` | Create a user |
| GET | `/api/users/{id}` | Get a user by ID |
| DELETE | `/api/users/{id}` | Delete a user |

## Testing

The project uses **Serenity BDD** on top of JUnit 5 to produce structured, readable test reports. Step methods are annotated with `@Step` and injected into test classes via `@Steps`, so each test shows a full breakdown of what was executed.

```
src/test/java/org/example/steps/ApiSteps.java   # Reusable @Step methods
```

Test classes use `@ExtendWith(SerenityJUnit5Extension.class)` and `@SpringBootTest` together, with MockMvc for controller-level tests and REST Assured for HTTP-based steps.

### Running quality checks locally

```bash
mvn spotbugs:check      # Static analysis
mvn jacoco:report       # Code coverage report (target/site/jacoco/index.html)
mvn serenity:aggregate  # Regenerate Serenity HTML report from last test run
```

## CI/CD Pipelines

### `test-gate.yml` — runs on every Pull Request

| Step | Details |
|---|---|
| Build & test | `mvn package -Dmaven.test.failure.ignore=true` |
| SpotBugs | Static analysis, fails the job if issues found |
| JaCoCo | Coverage report uploaded as artifact |
| Serenity report | HTML report uploaded as artifact |
| **Pass rate check** | Parses Surefire XML — blocks merge if pass rate < 80% |

The `check-pass-rate` job is the required status check enforced by branch protection. A PR cannot be merged into master unless at least 80% of tests pass.

### `build-deploy.yml` — runs on every push to master

| Step | Details |
|---|---|
| Build JAR | `mvn package -DskipTests` |
| Docker build & push | Pushes image to GitHub Container Registry |
| Publish Serenity report | Runs tests, generates report, deploys to GitHub Pages |

## Serenity Test Report

The full Serenity BDD report is published to GitHub Pages after every merge to master. It shows test results, step-by-step execution, and failure details.

> Enable GitHub Pages in repo Settings → Pages → Source: **GitHub Actions** to activate this.

## Docker

```bash
# Pull the latest image
docker pull ghcr.io/<org>/<repo>:master

# Run locally
docker run -p 8080:8080 ghcr.io/<org>/<repo>:master
```

The Dockerfile uses a two-stage build: Maven compiles and packages the JAR in the build stage, and a slim JDK image runs it in the runtime stage.

## Branch Protection

The `master` branch requires the `check-pass-rate` status check to pass before any PR can be merged. This is enforced via GitHub branch protection rules (Settings → Branches → master → Require status checks).
