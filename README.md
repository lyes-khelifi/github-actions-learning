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
├── test-gate.yml              # Unit test gate — PRs and pushes to master
├── integration-gate.yml       # Integration test gate — PRs and pushes to master
├── regression-run.yml         # Regression suite — PRs to master, nightly schedule
├── all-tests-run.yml          # Full test suite — PRs to master, nightly schedule
├── build-deploy.yml           # Build JAR and push Docker image — pushes to master
├── codeql.yml                 # CodeQL security analysis — PRs, pushes, weekly
└── dependency-check.yml       # OWASP dependency scan — pushes to master, weekly
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

### `test-gate.yml` — PRs and pushes to master

| Step | Details |
|---|---|
| Unit tests | `mvn package -Dgroups=unit -Dmaven.test.failure.ignore=true` |
| SpotBugs | Static analysis, fails the job if issues found |
| JaCoCo | Coverage report uploaded as artifact |
| Serenity report | HTML report published to GitHub Pages on push |
| **Pass rate check** | Parses Surefire XML — blocks merge if pass rate < 80% |

The `check-pass-rate` job is the required status check enforced by branch protection. A PR cannot be merged into master unless at least 80% of unit tests pass.

### `integration-gate.yml` — PRs and pushes to master

| Step | Details |
|---|---|
| Integration tests | `mvn -B test -Dgroups=integration -Dmaven.test.failure.ignore=true` |

### `regression-run.yml` — PRs to master, nightly at 8 AM EST, manual

| Step | Details |
|---|---|
| Regression tests | `mvn -B test -Dgroups=regression -Dmaven.test.failure.ignore=true` |
| Serenity report | HTML report published to GitHub Pages |
| Trend data | Pass/fail history written to `regression/trend-data.json` on gh-pages |

Running on PRs ensures regressions on a feature branch are caught before merge. The nightly schedule keeps a continuous health history on master.

### `all-tests-run.yml` — PRs to master, nightly at 9 AM EST, manual

| Step | Details |
|---|---|
| Full test suite | `mvn -B test -Dmaven.test.failure.ignore=true` |
| Serenity report | HTML report published to GitHub Pages |
| Trend data | Pass/fail history written to `all-tests/trend-data.json` on gh-pages |

### `build-deploy.yml` — pushes to master

| Step | Details |
|---|---|
| Build JAR | `mvn package -DskipTests` |
| Docker build & push | Pushes image to GitHub Container Registry |

### `codeql.yml` — PRs, pushes to master, weekly on Monday

Runs GitHub's CodeQL static analysis for security vulnerabilities in Java source code.

### `dependency-check.yml` — pushes to master, weekly on Monday

Runs the OWASP Dependency-Check tool and uploads results as a SARIF security alert to the Security tab.

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
