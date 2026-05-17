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
@WithTagValuesOf({"regression", "health", "metrics", "system"})
public class SystemHealthRegressionTest {

    @LocalServerPort
    private int port;

    @Steps
    RegressionSteps regressionSteps;

    @BeforeEach
    void setUp() {
        regressionSteps.setBaseUrl("http://localhost:" + port);
    }

    @Test
    void healthEndpointFullVerification() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }

    @Test
    void metricsEndpointFullVerification() {
        regressionSteps.metricsReturns200();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsUptimeIsPositive();
    }

    @Test
    void healthAndMetricsCombinedCheck() {
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
    }

    @Test
    void systemResourceMetricsAreValid() {
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.healthReturnsUp();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthVersionDetail();
    }

    @Test
    void repeatedHealthChecksAreConsistent() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.metricsReturns200();
        regressionSteps.verifyMetricsUptimeIsPositive();
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.verifyMetricsMemoryIsNonNegative();
    }
}
