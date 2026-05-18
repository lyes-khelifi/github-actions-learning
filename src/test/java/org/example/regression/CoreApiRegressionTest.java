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
public class CoreApiRegressionTest {

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
    void allCoreApiEndpointsAreAvailable() {
        regressionSteps.greetingReturnsHello("Core");
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
        regressionSteps.getAllUsersReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 6 steps
    @Test
    void greetingHealthAndMetricsSmoke() {
        regressionSteps.greetingReturnsHello("API");
        regressionSteps.verifyGreetingReturns200("Check");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 7 steps
    @Test
    void coreApiWithSystemResourceCheck() {
        regressionSteps.healthReturnsUp();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.greetingReturnsHello("Resource");
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.verifyMetricsUptimeIsPositive();
    }

    // 7 steps
    @Test
    void coreApiWithUserEndpointSmoke() {
        regressionSteps.healthReturnsUp();
        regressionSteps.greetingReturnsHello("UserSmoke");
        regressionSteps.metricsReturns200();
        regressionSteps.getAllUsersReturns200();
        long id = regressionSteps.createUser("core-user-1", "core-user-1@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "core-user-1");
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 8 steps
    @Test
    void coreApiFullSmokeRegression() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.greetingReturnsHello("FullSmoke");
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.getAllUsersReturns200();
        regressionSteps.verifyHealthServiceName();
    }

    // 9 steps
    @Test
    void coreApiSystemHealthAndMetricsRegression() {
        regressionSteps.greetingReturnsHello("SysSmoke");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }

    // 10 steps
    @Test
    void coreApiCompleteRegressionSuite() {
        regressionSteps.greetingReturnsHello("Complete");
        regressionSteps.verifyGreetingReturns200("SmokeUser");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }
}
