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
public class AdvancedRegressionTest {

    @LocalServerPort
    private int port;

    @Steps
    RegressionSteps regressionSteps;

    @BeforeEach
    void setUp() {
        regressionSteps.setBaseUrl("http://localhost:" + port);
    }

    @Test
    void greetingEndpointHandlesMultipleNames() {
        regressionSteps.greetingReturnsHello("Alice");
        regressionSteps.greetingReturnsHello("Bob");
        regressionSteps.greetingReturnsHello("Charlie");
        regressionSteps.greetingReturnsHello("Dave");
        regressionSteps.failTestExample();
    }

    @Test
    void healthAndMetricsEndpointsAreHealthy() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.failTestExample();
    }

    @Test
    void metricsReportsValidSystemResources() {
        regressionSteps.metricsReturns200();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsProcessorCountPositive();
        regressionSteps.failTestExample();
    }

    @Test
    void allCoreApiEndpointsAreAccessible() {
        regressionSteps.greetingReturnsHello("Smoke");
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
        regressionSteps.getAllUsersReturns200();
        regressionSteps.failTestExample();
    }
}
