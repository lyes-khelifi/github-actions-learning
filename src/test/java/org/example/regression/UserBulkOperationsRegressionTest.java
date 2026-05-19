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
@WithTagValuesOf({"regression", "user", "bulk"})
public class UserBulkOperationsRegressionTest {

    @LocalServerPort
    private int port;

    @Steps
    RegressionSteps regressionSteps;

    @BeforeEach
    void setUp() {
        regressionSteps.setBaseUrl("http://localhost:" + port);
    }

    @Test
    void createThreeUsersVerifyAndCleanup() {
        long id1 = regressionSteps.createUser("bulk-a", "bulk-a@example.com");
        long id2 = regressionSteps.createUser("bulk-b", "bulk-b@example.com");
        long id3 = regressionSteps.createUser("bulk-c", "bulk-c@example.com");
        Assertions.assertThat(id1).isGreaterThan(0L);
        Assertions.assertThat(id2).isGreaterThan(0L);
        Assertions.assertThat(id3).isGreaterThan(0L);
        Assertions.assertThat(id1).isNotEqualTo(id2);
        Assertions.assertThat(id2).isNotEqualTo(id3);
        regressionSteps.getUserByIdReturnsCorrectData(id1, "bulk-a");
        regressionSteps.getUserByIdReturnsCorrectData(id2, "bulk-b");
        regressionSteps.getUserByIdReturnsCorrectData(id3, "bulk-c");
        regressionSteps.verifyUsersListIsNotEmpty();
        regressionSteps.deleteUserReturnsNoContent(id1);
        regressionSteps.deleteUserReturnsNoContent(id2);
        regressionSteps.deleteUserReturnsNoContent(id3);
    }

    @Test
    void userListGrowsWithMultipleCreations() {
        long id1 = regressionSteps.createUser("grow-a", "grow-a@example.com");
        long id2 = regressionSteps.createUser("grow-b", "grow-b@example.com");
        Assertions.assertThat(id1).isGreaterThan(0L);
        Assertions.assertThat(id2).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id1, "grow-a");
        regressionSteps.getUserByIdReturnsCorrectData(id2, "grow-b");
        regressionSteps.verifyUserEmailMatches(id1, "grow-a@example.com");
        regressionSteps.verifyUserEmailMatches(id2, "grow-b@example.com");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.verifyUsersListIsNotEmpty();
        regressionSteps.deleteUserReturnsNoContent(id1);
        regressionSteps.deleteUserReturnsNoContent(id2);
    }

    @Test
    void bulkUpdateAndVerify() {
        long id1 = regressionSteps.createUser("upd-bulk-a", "upd-bulk-a@example.com");
        long id2 = regressionSteps.createUser("upd-bulk-b", "upd-bulk-b@example.com");
        Assertions.assertThat(id1).isGreaterThan(0L);
        Assertions.assertThat(id2).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id1, "upd-bulk-a");
        regressionSteps.getUserByIdReturnsCorrectData(id2, "upd-bulk-b");
        regressionSteps.updateUserAndVerify(id1, "upd-bulk-a-v2", "upd-bulk-a-v2@example.com");
        regressionSteps.updateUserAndVerify(id2, "upd-bulk-b-v2", "upd-bulk-b-v2@example.com");
        regressionSteps.verifyUserFullDetails(id1, "upd-bulk-a-v2", "upd-bulk-a-v2@example.com");
        regressionSteps.verifyUserFullDetails(id2, "upd-bulk-b-v2", "upd-bulk-b-v2@example.com");
        regressionSteps.deleteUserReturnsNoContent(id1);
        regressionSteps.deleteUserReturnsNoContent(id2);
    }

    @Test
    void deleteMultipleUsersAndVerifyGone() {
        long id1 = regressionSteps.createUser("del-multi-a", "del-multi-a@example.com");
        long id2 = regressionSteps.createUser("del-multi-b", "del-multi-b@example.com");
        Assertions.assertThat(id1).isGreaterThan(0L);
        Assertions.assertThat(id2).isGreaterThan(0L);
        regressionSteps.getUserByIdReturnsCorrectData(id1, "del-multi-a");
        regressionSteps.getUserByIdReturnsCorrectData(id2, "del-multi-b");
        regressionSteps.verifyUsersListIsNotEmpty();
        regressionSteps.deleteUserReturnsNoContent(id1);
        regressionSteps.verifyUserNotFound(id1);
        regressionSteps.deleteUserReturnsNoContent(id2);
        regressionSteps.verifyUserNotFound(id2);
        regressionSteps.getAllUsersReturns200();
    }

    @Test
    void userIdsAreUniqueAcrossCreations() {
        long id1 = regressionSteps.createUser("uid-a", "uid-a@example.com");
        long id2 = regressionSteps.createUser("uid-b", "uid-b@example.com");
        long id3 = regressionSteps.createUser("uid-c", "uid-c@example.com");
        Assertions.assertThat(id1).isGreaterThan(0L);
        Assertions.assertThat(id2).isGreaterThan(0L);
        Assertions.assertThat(id3).isGreaterThan(0L);
        Assertions.assertThat(id1).isNotEqualTo(id2);
        Assertions.assertThat(id2).isNotEqualTo(id3);
        Assertions.assertThat(id1).isNotEqualTo(id3);
        regressionSteps.getUserByIdReturnsCorrectData(id1, "uid-a");
        regressionSteps.getUserByIdReturnsCorrectData(id2, "uid-b");
        regressionSteps.getUserByIdReturnsCorrectData(id3, "uid-c");
        regressionSteps.verifyUserEmailMatches(id1, "uid-a@example.com");
        regressionSteps.verifyUserEmailMatches(id2, "uid-b@example.com");
        regressionSteps.deleteUserReturnsNoContent(id1);
        regressionSteps.deleteUserReturnsNoContent(id2);
        regressionSteps.deleteUserReturnsNoContent(id3);
    }
}
