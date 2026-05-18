package org.example.regression;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
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
@WithTagValuesOf({"regression", "smoke"})
public class ApiSmokeExtendedRegressionTest {

    @LocalServerPort
    private int port;

    @Steps
    RegressionSteps regressionSteps;

    @BeforeEach
    void setUp() {
        regressionSteps.setBaseUrl("http://localhost:" + port);
    }

    // 5 steps
    @Test
    void apiSmokeGreetingAndHealth() {
        regressionSteps.greetingReturnsHello("Smoke1");
        regressionSteps.greetingReturnsHello("Smoke2");
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
        regressionSteps.getAllUsersReturns200();
    }

    // 5 steps
    @Test
    void apiSmokeCoreEndpointsRespond() {
        regressionSteps.verifyGreetingReturns200("SmokeA");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 6 steps
    @Test
    void apiSmokeWithUserOperations() {
        regressionSteps.healthReturnsUp();
        regressionSteps.greetingReturnsHello("SmokeUser");
        long id = regressionSteps.createUser("smoke-user-1", "smoke-user-1@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "smoke-user-1");
        regressionSteps.metricsReturns200();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 6 steps
    @Test
    void apiSmokeMetricsAndHealthDetails() {
        regressionSteps.healthReturnsUp();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsUptimeIsPositive();
    }

    // 7 steps
    @Test
    void apiSmokeFullSystemCheck() {
        regressionSteps.greetingReturnsHello("FullSmoke");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.getAllUsersReturns200();
    }

    // 7 steps
    @Test
    void apiSmokeUserLifecycleWithHealthCheck() {
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsStatusIsHealthy();
        long id = regressionSteps.createUser("smoke-user-2", "smoke-user-2@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "smoke-user-2");
        regressionSteps.verifyUserEmailMatches(id, "smoke-user-2@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    // 8 steps
    @Test
    void apiSmokeComprehensiveRegression() {
        regressionSteps.greetingReturnsHello("Comprehensive");
        regressionSteps.healthReturnsUp();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.getAllUsersReturns200();
    }

    // 9 steps
    @Test
    void apiSmokeCompleteRegressionSuite() {
        regressionSteps.greetingReturnsHello("FinalSmoke");
        regressionSteps.verifyGreetingReturns200("SmokeCheck");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.getAllUsersReturns200();
    }
}
