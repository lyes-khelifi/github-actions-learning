package org.example.unit;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.security.JwtUtil;
import org.example.steps.UnitSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "security"})
public class JwtUtilExtendedTest {

    @Steps
    UnitSteps unitSteps;

    @BeforeEach
    void setUp() {
        unitSteps.setJwtUtil(new JwtUtil());
    }

    @Test
    void generateTokenReturnsNonNullToken() {
        String token = unitSteps.generateJwtToken("user");
        unitSteps.jwtTokenIsNotBlank(token);
    }

    @Test
    void generatedTokenHasThreeParts() {
        String token = unitSteps.generateJwtToken("user");
        unitSteps.jwtTokenHasThreeParts(token);
    }

    @Test
    void extractedUsernameMatchesSubject() {
        String token = unitSteps.generateJwtToken("alice");
        unitSteps.jwtExtractedUsernameEquals(token, "alice");
    }

    @Test
    void tokenIsValidForCorrectUsername() {
        String token = unitSteps.generateJwtToken("bob");
        unitSteps.jwtTokenIsValid(token, "bob");
    }

    @Test
    void tokenIsInvalidForWrongUsername() {
        String token = unitSteps.generateJwtToken("charlie");
        unitSteps.jwtTokenIsInvalidForUsername(token, "dave");
    }

    @Test
    void generateTokenForAdminUser() {
        String token = unitSteps.generateJwtToken("admin");
        unitSteps.jwtExtractedUsernameEquals(token, "admin");
        unitSteps.jwtTokenIsValid(token, "admin");
    }

    @Test
    void generateTokenForNumericUsername() {
        String token = unitSteps.generateJwtToken("user123");
        unitSteps.jwtTokenIsValid(token, "user123");
    }

    @Test
    void generateTokenForEmailUsername() {
        String token = unitSteps.generateJwtToken("user@example.com");
        unitSteps.jwtTokenIsValid(token, "user@example.com");
    }

    @Test
    void twoTokensForSameUserAreBothValid() {
        String t1 = unitSteps.generateJwtToken("alice");
        String t2 = unitSteps.generateJwtToken("alice");
        unitSteps.jwtTokenIsValid(t1, "alice");
        unitSteps.jwtTokenIsValid(t2, "alice");
    }

    @Test
    void tokensForDifferentUsersAreIndependentlyValid() {
        String t1 = unitSteps.generateJwtToken("alice");
        String t2 = unitSteps.generateJwtToken("bob");
        unitSteps.jwtTokenIsValid(t1, "alice");
        unitSteps.jwtTokenIsValid(t2, "bob");
        unitSteps.jwtTokenIsInvalidForUsername(t1, "bob");
    }
}
