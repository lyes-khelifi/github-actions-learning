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
@WithTagValuesOf({"regression", "health", "metrics"})
public class HealthMetricsRegressionTest {

    @LocalServerPort
    private int port;

    @Steps
    RegressionSteps regressionSteps;

    @BeforeEach
    void setUp() {
        regressionSteps.setBaseUrl("http://localhost:" + port);
    }

    @Test
    void healthEndpointReturnsUpStatus() {
        regressionSteps.healthReturnsUp();
    }

    @Test
    void healthEndpointContainsServiceDetails() {
        regressionSteps.healthContainsServiceDetail();
    }

    @Test
    void metricsEndpointReturns200() {
        regressionSteps.metricsReturns200();
    }

    @Test
    void metricsEndpointContainsAllRequiredFields() {
        regressionSteps.metricsContainsAllFields();
    }

    @Test
    void metricsStatusIsHealthy() {
        regressionSteps.metricsStatusIsHealthy();
    }
}
