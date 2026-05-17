package org.example.regression;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.assertj.core.api.Assertions;
import org.example.TestSecurityConfig;
import org.example.steps.RegressionSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@ExtendWith(SerenityJUnit5Extension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfig.class)
@Tag("regression")
@WithTagValuesOf({"regression", "cross-functional"})
public class CrossFunctionalRegressionTest {

    @LocalServerPort
    private int port;

    @Steps
    RegressionSteps regressionSteps;

    @BeforeEach
    void setUp() {
        regressionSteps.setBaseUrl("http://localhost:" + port);
    }

    @Test
    void greetingHealthAndUserWorkflow() {
        regressionSteps.greetingReturnsHello("World");
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
        long id = regressionSteps.createUser("cf-user1", "cf-user1@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id, "cf-user1");
        regressionSteps.verifyUsersListIsNotEmpty();
        regressionSteps.greetingReturnsHello("Test");
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void fullApiSurfaceCheck() {
        regressionSteps.greetingReturnsHello("API");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.getAllUsersReturns200();
        long id = regressionSteps.createUser("api-check", "api-check@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id, "api-check");
        regressionSteps.verifyHealthServiceName();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void systemHealthWithActiveUserLoad() {
        long id = regressionSteps.createUser("load-user", "load-user@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
        regressionSteps.getUserByIdReturnsCorrectData(id, "load-user");
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.updateUserAndVerify(id, "load-user-v2", "load-user-v2@example.com");
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void greetingVariantsWithHealthCheck() {
        regressionSteps.greetingReturnsHello("Alice");
        regressionSteps.greetingReturnsHello("Bob");
        regressionSteps.greetingReturnsHello("Charlie");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.greetingReturnsHello("Dave");
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyGreetingReturns200("Eve");
    }

    @Test
    void userLifecycleWithSystemHealthVerification() {
        regressionSteps.healthReturnsUp();
        long id = regressionSteps.createUser("hlth-user", "hlth-user@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.metricsReturns200();
        regressionSteps.getUserByIdReturnsCorrectData(id, "hlth-user");
        regressionSteps.verifyUserEmailMatches(id, "hlth-user@example.com");
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.updateUserAndVerify(id, "hlth-user-v2", "hlth-user-v2@example.com");
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }
}
