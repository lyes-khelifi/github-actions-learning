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
@WithTagValuesOf({"regression", "health"})
public class HealthExtendedRegressionTest {

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
    void healthStatusAndDetailsRegression() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.metricsReturns200();
    }

    // 5 steps
    @Test
    void healthWithGreetingVerification() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.greetingReturnsHello("World");
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 6 steps
    @Test
    void healthDetailsAndMetricsRegression() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
    }

    // 7 steps
    @Test
    void healthServiceMetadataRegression() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.greetingReturnsHello("Test");
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 7 steps
    @Test
    void healthAndMetricsConsistencyCheck() {
        regressionSteps.healthReturnsUp();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.verifyMetricsUptimeIsPositive();
    }

    // 8 steps
    @Test
    void healthFullDetailsVerification() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyMetricsUptimeIsPositive();
    }

    // 9 steps
    @Test
    void healthSystemReadinessRegression() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.greetingReturnsHello("Smoke");
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
    }

    // 10 steps
    @Test
    void healthCompleteRegressionSuite() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.greetingReturnsHello("HealthCheck");
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }
}
