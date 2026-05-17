package org.example.regression;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.assertj.core.api.Assertions;
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
@WithTagValuesOf({"regression", "user", "workflow"})
public class UserWorkflowRegressionTest {

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
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id, "lifecycle-user");
        regressionSteps.updateUserAndVerify(id, "lifecycle-updated", "lifecycle-updated@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void createdUserDataIsVerifiable() {
        regressionSteps.createUserAndVerify("verifiable-user", "verifiable@example.com");
        long id = regressionSteps.createUser("verifiable-user2", "verifiable2@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id, "verifiable-user2");
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void deletedUserIsNotRetrievable() {
        long id = regressionSteps.createUser("delete-verify", "delete-verify@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id, "delete-verify");
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    @Test
    void updatedUserDataPersistsOnRetrieval() {
        long id = regressionSteps.createUser("persist-test", "persist@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.updateUserAndVerify(id, "persist-updated", "persist-updated@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "persist-updated");
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void userCountIncreasesAfterCreation() {
        int initialCount = regressionSteps.getAllUsersCount();
        Assertions.assertThat(initialCount).isGreaterThanOrEqualTo(0);
        long id = regressionSteps.createUser("count-user", "count-user@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.verifyUserCountIsAtLeast(initialCount + 1);
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    @Test
    void multipleUsersCanBeCreatedAndListed() {
        long id1 = regressionSteps.createUser("multi-user-a", "multi-a@example.com");
        long id2 = regressionSteps.createUser("multi-user-b", "multi-b@example.com");
        Assertions.assertThat(id1).isGreaterThan(0L);
        Assertions.assertThat(id2).isGreaterThan(0L);
        Assertions.assertThat(id1).isNotEqualTo(id2);
        regressionSteps.getAllUsersReturns200();
        regressionSteps.deleteUserReturnsNoContent(id1);
        regressionSteps.deleteUserReturnsNoContent(id2);
    }
}
