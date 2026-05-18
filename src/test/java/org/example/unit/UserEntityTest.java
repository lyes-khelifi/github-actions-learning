package org.example.unit;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.entity.UserEntity;
import org.example.steps.UnitSteps;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "user"})
public class UserEntityTest {

    @Steps
    UnitSteps unitSteps;

    @Test
    void defaultConstructorCreatesEntityWithNullId() {
        UserEntity entity = new UserEntity();
        unitSteps.userEntityIdIsNull(entity);
    }

    @Test
    void setterAssignsUsername() {
        UserEntity entity = unitSteps.buildUserEntity("alice", "alice@example.com");
        unitSteps.userEntityUsernameEquals(entity, "alice");
    }

    @Test
    void setterAssignsEmail() {
        UserEntity entity = unitSteps.buildUserEntity("alice", "alice@example.com");
        unitSteps.userEntityEmailEquals(entity, "alice@example.com");
    }

    @Test
    void builtEntityHasNullId() {
        UserEntity entity = unitSteps.buildUserEntity("bob", "bob@example.com");
        unitSteps.userEntityIdIsNull(entity);
    }

    @Test
    void setterUpdatesUsername() {
        UserEntity entity = unitSteps.buildUserEntity("carol", "carol@example.com");
        unitSteps.userEntitySetUsername(entity, "carol-updated");
    }

    @Test
    void setterUpdatesEmail() {
        UserEntity entity = unitSteps.buildUserEntity("dave", "dave@example.com");
        unitSteps.userEntitySetEmail(entity, "dave-new@example.com");
    }

    @Test
    void constructorWithAllFieldsSetsUsername() {
        UserEntity entity = unitSteps.buildUserEntityWithConstructor("eve", "eve@example.com", "secret");
        unitSteps.userEntityUsernameEquals(entity, "eve");
    }

    @Test
    void constructorWithAllFieldsSetsEmail() {
        UserEntity entity = unitSteps.buildUserEntityWithConstructor("frank", "frank@example.com", "pass");
        unitSteps.userEntityEmailEquals(entity, "frank@example.com");
    }

    @Test
    void constructorWithAllFieldsSetsPassword() {
        UserEntity entity = unitSteps.buildUserEntityWithConstructor("grace", "grace@example.com", "mypass");
        unitSteps.userEntityPasswordEquals(entity, "mypass");
    }

    @Test
    void setUsernameAndEmailRoundTrip() {
        UserEntity entity = unitSteps.buildUserEntity("henry", "henry@example.com");
        unitSteps.userEntitySetUsername(entity, "henry-v2");
        unitSteps.userEntitySetEmail(entity, "henry-v2@example.com");
    }
}
