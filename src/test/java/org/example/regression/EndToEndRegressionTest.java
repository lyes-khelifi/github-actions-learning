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
@WithTagValuesOf({"regression", "e2e"})
public class EndToEndRegressionTest {

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
    void systemReadinessSmokeCheck() {
        regressionSteps.healthReturnsUp();
        regressionSteps.greetingReturnsHello("Smoke");
        regressionSteps.metricsReturns200();
        regressionSteps.getAllUsersReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    // 5 steps
    @Test
    void createUserAndVerifySystemState() {
        regressionSteps.healthReturnsUp();
        long id = regressionSteps.createUser("e2e-user-1", "e2e-user-1@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "e2e-user-1");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 6 steps
    @Test
    void greetingAndUserCreationEndToEnd() {
        regressionSteps.greetingReturnsHello("E2E");
        regressionSteps.healthReturnsUp();
        long id = regressionSteps.createUser("e2e-user-2", "e2e-user-2@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "e2e-user-2");
        regressionSteps.verifyUsersListIsNotEmpty();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 6 steps
    @Test
    void fullApiSurfaceAvailabilityCheck() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.greetingReturnsHello("Surface");
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.getAllUsersReturns200();
    }

    // 7 steps
    @Test
    void userWorkflowWithSystemHealthChecks() {
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
        long id = regressionSteps.createUser("e2e-user-3", "e2e-user-3@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "e2e-user-3");
        regressionSteps.verifyUserEmailMatches(id, "e2e-user-3@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    // 7 steps
    @Test
    void updateAndDeleteUserEndToEnd() {
        long id = regressionSteps.createUser("e2e-user-4", "e2e-user-4@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "e2e-user-4");
        regressionSteps.updateUserAndVerify(id, "e2e-user-4-v2", "e2e-user-4-v2@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "e2e-user-4-v2");
        regressionSteps.verifyUserEmailMatches(id, "e2e-user-4-v2@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    // 8 steps
    @Test
    void fullUserLifecycleWithMetricsCheck() {
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        long id = regressionSteps.createUser("e2e-user-5", "e2e-user-5@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "e2e-user-5");
        regressionSteps.verifyUserEmailMatches(id, "e2e-user-5@example.com");
        regressionSteps.updateUserAndVerify(id, "e2e-user-5-v2", "e2e-user-5-v2@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    // 8 steps
    @Test
    void greetingHealthMetricsUserJourney() {
        regressionSteps.greetingReturnsHello("Journey");
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        long id = regressionSteps.createUser("e2e-user-6", "e2e-user-6@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "e2e-user-6");
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 9 steps
    @Test
    void systemIntegrityWithUserCrudAndGreeting() {
        regressionSteps.healthReturnsUp();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.greetingReturnsHello("Integrity");
        regressionSteps.metricsReturns200();
        long id = regressionSteps.createUser("e2e-user-7", "e2e-user-7@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "e2e-user-7");
        regressionSteps.verifyUserEmailMatches(id, "e2e-user-7@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    // 10 steps
    @Test
    void completeEndToEndRegressionSuite() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.verifyHealthServiceName();
        regressionSteps.greetingReturnsHello("Complete");
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
        long id = regressionSteps.createUser("e2e-user-8", "e2e-user-8@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "e2e-user-8");
        regressionSteps.verifyUserEmailMatches(id, "e2e-user-8@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
    }
}
