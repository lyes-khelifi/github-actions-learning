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
    void fullUserLifecycle() {
        long id = regressionSteps.createUser("lifecycle-user", "lifecycle@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "lifecycle-user");
        regressionSteps.updateUserAndVerify(id, "lifecycle-updated", "lifecycle-updated@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void createdUserDataIsVerifiable() {
        regressionSteps.createUserAndVerify("verifiable-user", "verifiable@example.com");
        long id = regressionSteps.createUser("verifiable-user2", "verifiable2@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "verifiable-user2");
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void deletedUserIsNotRetrievable() {
        long id = regressionSteps.createUser("delete-verify", "delete-verify@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "delete-verify");
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    @Test
    void updatedUserDataPersistsOnRetrieval() {
        long id = regressionSteps.createUser("persist-test", "persist@example.com");
        regressionSteps.updateUserAndVerify(id, "persist-updated", "persist-updated@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "persist-updated");
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void userCountIncreasesAfterCreation() {
        int initialCount = regressionSteps.getAllUsersCount();
        long id = regressionSteps.createUser("count-user", "count-user@example.com");
        regressionSteps.verifyUserCountIsAtLeast(initialCount + 1);
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void greetingEndpointHandlesMultipleNames() {
        regressionSteps.greetingReturnsHello("Alice");
        regressionSteps.greetingReturnsHello("Bob");
        regressionSteps.greetingReturnsHello("Charlie");
        regressionSteps.greetingReturnsHello("Dave");
    }

    @Test
    void healthAndMetricsEndpointsAreHealthy() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.metricsReturns200();
        regressionSteps.metricsStatusIsHealthy();
    }

    @Test
    void metricsReportsValidSystemResources() {
        regressionSteps.metricsReturns200();
        regressionSteps.metricsContainsAllFields();
        regressionSteps.metricsStatusIsHealthy();
        regressionSteps.verifyMetricsProcessorCountPositive();
    }

    @Test
    void multipleUsersCanBeCreatedAndListed() {
        long id1 = regressionSteps.createUser("multi-user-a", "multi-a@example.com");
        long id2 = regressionSteps.createUser("multi-user-b", "multi-b@example.com");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.deleteUserReturnsNoContent(id1);
        regressionSteps.deleteUserReturnsNoContent(id2);
    }

    @Test
    void allCoreApiEndpointsAreAccessible() {
        regressionSteps.greetingReturnsHello("Smoke");
        regressionSteps.healthReturnsUp();
        regressionSteps.metricsReturns200();
        regressionSteps.getAllUsersReturns200();
    }
}
