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
@WithTagValuesOf({"regression", "greeting"})
public class GreetingExtendedRegressionTest {

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
    void greetingReturnsCorrectResponseForMultipleNames() {
        regressionSteps.greetingReturnsHello("Alice");
        regressionSteps.greetingReturnsHello("Bob");
        regressionSteps.greetingReturnsHello("Charlie");
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
    }

    // 5 steps
    @Test
    void greetingWithSystemHealthVerification() {
        regressionSteps.greetingReturnsHello("Dave");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 6 steps
    @Test
    void greetingWithHealthAndMetricsCheck() {
        regressionSteps.greetingReturnsHello("Eve");
        regressionSteps.greetingReturnsHello("Frank");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 6 steps
    @Test
    void greetingEndpointStatusAndContentVerification() {
        regressionSteps.verifyGreetingReturns200("Grace");
        regressionSteps.greetingReturnsHello("Grace");
        regressionSteps.verifyGreetingReturns200("Henry");
        regressionSteps.greetingReturnsHello("Henry");
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
    }

    // 7 steps
    @Test
    void greetingResponseForVariousNamesWithHealthCheck() {
        regressionSteps.greetingReturnsHello("Ivy");
        regressionSteps.greetingReturnsHello("Jack");
        regressionSteps.greetingReturnsHello("Kate");
        regressionSteps.verifyGreetingReturns200("Leo");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 7 steps
    @Test
    void greetingWithFullSystemCheck() {
        regressionSteps.greetingReturnsHello("Mia");
        regressionSteps.verifyGreetingReturns200("Nina");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyHealthServiceName();
    }

    // 8 steps
    @Test
    void greetingAndSystemHealthFullRegression() {
        regressionSteps.greetingReturnsHello("Oscar");
        regressionSteps.greetingReturnsHello("Pam");
        regressionSteps.verifyGreetingReturns200("Quinn");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 9 steps
    @Test
    void greetingFullRegressionSuite() {
        regressionSteps.greetingReturnsHello("Ray");
        regressionSteps.greetingReturnsHello("Sam");
        regressionSteps.verifyGreetingReturns200("Tom");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.verifyHealthVersionDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }
}
