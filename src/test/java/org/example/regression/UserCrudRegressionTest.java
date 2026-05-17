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
@WithTagValuesOf({"regression", "user", "crud"})
public class UserCrudRegressionTest {

    @LocalServerPort
    private int port;

    @Steps
    RegressionSteps regressionSteps;

    @BeforeEach
    void setUp() {
        regressionSteps.setBaseUrl("http://localhost:" + port);
    }

    @Test
    void fullLifecycleWithEmailVerification() {
        long id = regressionSteps.createUser("crud-alice", "crud-alice@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id, "crud-alice");
        regressionSteps.verifyUserEmailMatches(id, "crud-alice@example.com");
        regressionSteps.updateUserAndVerify(id, "crud-alice-v2", "crud-alice-v2@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "crud-alice-v2");
        regressionSteps.verifyUserEmailMatches(id, "crud-alice-v2@example.com");
        regressionSteps.verifyUsersListIsNotEmpty();
        regressionSteps.getAllUsersReturns200();
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    @Test
    void userCountChangesWithCrudOperations() {
        int initial = regressionSteps.getAllUsersCount();
        Assertions.assertThat(initial).isGreaterThanOrEqualTo(0);
        long id = regressionSteps.createUser("crud-bob", "crud-bob@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.verifyUserCountIsAtLeast(initial + 1);
        regressionSteps.getUserByIdReturnsCorrectData(id, "crud-bob");
        regressionSteps.verifyUserEmailMatches(id, "crud-bob@example.com");
        regressionSteps.updateUserAndVerify(id, "crud-bob-updated", "crud-bob-updated@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "crud-bob-updated");
        regressionSteps.verifyUserCountIsAtLeast(initial + 1);
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    @Test
    void multipleUpdateCyclesPreserveData() {
        long id = regressionSteps.createUser("crud-carol", "crud-carol@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id, "crud-carol");
        regressionSteps.updateUserAndVerify(id, "crud-carol-u1", "crud-carol-u1@example.com");
        regressionSteps.verifyUserEmailMatches(id, "crud-carol-u1@example.com");
        regressionSteps.updateUserAndVerify(id, "crud-carol-u2", "crud-carol-u2@example.com");
        regressionSteps.verifyUserEmailMatches(id, "crud-carol-u2@example.com");
        regressionSteps.updateUserAndVerify(id, "crud-carol-final", "crud-carol-final@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "crud-carol-final");
        regressionSteps.verifyUserFullDetails(id, "crud-carol-final", "crud-carol-final@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    @Test
    void userDeletionRemovesFromSystem() {
        int initial = regressionSteps.getAllUsersCount();
        Assertions.assertThat(initial).isGreaterThanOrEqualTo(0);
        long id = regressionSteps.createUser("crud-dave", "crud-dave@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        regressionSteps.verifyUserCountIsAtLeast(initial + 1);
        regressionSteps.getUserByIdReturnsCorrectData(id, "crud-dave");
        regressionSteps.verifyUserEmailMatches(id, "crud-dave@example.com");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.verifyUsersListIsNotEmpty();
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
        regressionSteps.getAllUsersReturns200();
    }

    @Test
    void twoUsersHaveIndependentData() {
        long id1 = regressionSteps.createUser("crud-eve", "crud-eve@example.com");
        long id2 = regressionSteps.createUser("crud-frank", "crud-frank@example.com");
        Assertions.assertThat(id1).isGreaterThan(0L);
        Assertions.assertThat(id2).isGreaterThan(0L);
        Assertions.assertThat(id1).isNotEqualTo(id2);
        regressionSteps.getUserByIdReturnsCorrectData(id1, "crud-eve");
        regressionSteps.getUserByIdReturnsCorrectData(id2, "crud-frank");
        regressionSteps.verifyUserEmailMatches(id1, "crud-eve@example.com");
        regressionSteps.verifyUserEmailMatches(id2, "crud-frank@example.com");
        regressionSteps.updateUserAndVerify(id1, "crud-eve-updated", "crud-eve-updated@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id2, "crud-frank");
        regressionSteps.deleteUserReturnsNoContent(id1);
        regressionSteps.deleteUserReturnsNoContent(id2);
    }
}
