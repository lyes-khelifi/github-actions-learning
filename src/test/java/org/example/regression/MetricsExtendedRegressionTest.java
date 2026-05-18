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
@WithTagValuesOf({"regression", "metrics"})
public class MetricsExtendedRegressionTest {

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
    void metricsStatusAndUptimeRegression() {
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }

    // 6 steps
    @Test
    void metricsFieldsCompleteVerification() {
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }

    // 6 steps
    @Test
    void metricsWithHealthConsistency() {
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }

    // 7 steps
    @Test
    void metricsSystemResourcesRegression() {
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.healthReturnsUp();
    }

    // 7 steps
    @Test
    void metricsWithFullSystemCheck() {
        regressionSteps.healthReturnsUp();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }

    // 8 steps
    @Test
    void metricsAndHealthJointRegression() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }

    // 9 steps
    @Test
    void metricsResourcesWithGreetingRegression() {
        regressionSteps.greetingReturnsHello("Metrics");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }

    // 10 steps
    @Test
    void metricsCompleteRegressionSuite() {
        regressionSteps.greetingReturnsHello("SysCheck");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }
}
