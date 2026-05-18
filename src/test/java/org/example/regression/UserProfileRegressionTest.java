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
@WithTagValuesOf({"regression", "user"})
public class UserProfileRegressionTest {

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
    void createAndRetrieveUserProfile() {
        long id = regressionSteps.createUser("prof-user-1", "prof-user-1@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-1");
        regressionSteps.verifyUserEmailMatches(id, "prof-user-1@example.com");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 5 steps
    @Test
    void updateUserProfileAndVerify() {
        long id = regressionSteps.createUser("prof-user-2", "prof-user-2@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-2");
        regressionSteps.updateUserAndVerify(id, "prof-user-2-updated", "prof-user-2-updated@example.com");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 6 steps
    @Test
    void userProfileFullDetailsVerification() {
        long id = regressionSteps.createUser("prof-user-3", "prof-user-3@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-3");
        regressionSteps.verifyUserEmailMatches(id, "prof-user-3@example.com");
        regressionSteps.verifyUserFullDetails(id, "prof-user-3", "prof-user-3@example.com");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 7 steps
    @Test
    void userProfileUpdateAndReverify() {
        long id = regressionSteps.createUser("prof-user-4", "prof-user-4@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-4");
        regressionSteps.verifyUserEmailMatches(id, "prof-user-4@example.com");
        regressionSteps.updateUserAndVerify(id, "prof-user-4-v2", "prof-user-4-v2@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-4-v2");
        regressionSteps.verifyUserEmailMatches(id, "prof-user-4-v2@example.com");
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 7 steps
    @Test
    void userProfileWithHealthVerification() {
        regressionSteps.healthReturnsUp();
        long id = regressionSteps.createUser("prof-user-5", "prof-user-5@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-5");
        regressionSteps.verifyUserEmailMatches(id, "prof-user-5@example.com");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.verifyUsersListIsNotEmpty();
        regressionSteps.deleteUserReturnsNoContent(id);
    }

    // 8 steps
    @Test
    void userProfileDeleteAndVerifyNotFound() {
        long id = regressionSteps.createUser("prof-user-6", "prof-user-6@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-6");
        regressionSteps.verifyUserEmailMatches(id, "prof-user-6@example.com");
        regressionSteps.verifyUserFullDetails(id, "prof-user-6", "prof-user-6@example.com");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.verifyUsersListIsNotEmpty();
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    // 9 steps
    @Test
    void userProfileFullLifecycleWithSystemChecks() {
        regressionSteps.healthReturnsUp();
        regressionSteps.greetingReturnsHello("ProfileTest");
        long id = regressionSteps.createUser("prof-user-7", "prof-user-7@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-7");
        regressionSteps.verifyUserEmailMatches(id, "prof-user-7@example.com");
        regressionSteps.updateUserAndVerify(id, "prof-user-7-v2", "prof-user-7-v2@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-7-v2");
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }

    // 10 steps
    @Test
    void userProfileCompleteRegressionSuite() {
        regressionSteps.healthReturnsUp();
        regressionSteps.healthContainsServiceDetail();
        regressionSteps.greetingReturnsHello("Profile");
        long id = regressionSteps.createUser("prof-user-8", "prof-user-8@example.com");
        regressionSteps.getUserByIdReturnsCorrectData(id, "prof-user-8");
        regressionSteps.verifyUserEmailMatches(id, "prof-user-8@example.com");
        regressionSteps.verifyUserFullDetails(id, "prof-user-8", "prof-user-8@example.com");
        regressionSteps.getAllUsersReturns200();
        regressionSteps.deleteUserReturnsNoContent(id);
        regressionSteps.verifyUserNotFound(id);
    }
}
