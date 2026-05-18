package org.example.unit;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.model.UserDTO;
import org.example.steps.UnitSteps;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "user"})
public class UserDTOTest {

    @Steps
    UnitSteps unitSteps;

    @Test
    void defaultConstructorCreatesObjectWithNullFields() {
        UserDTO dto = new UserDTO();
        unitSteps.userDTOIdIsNull(dto);
    }

    @Test
    void fullConstructorSetsId() {
        UserDTO dto = unitSteps.buildUserDTO(1L, "alice", "alice@example.com");
        unitSteps.userDTOIdEquals(dto, 1L);
    }

    @Test
    void fullConstructorSetsUsername() {
        UserDTO dto = unitSteps.buildUserDTO(1L, "alice", "alice@example.com");
        unitSteps.userDTOUsernameEquals(dto, "alice");
    }

    @Test
    void fullConstructorSetsEmail() {
        UserDTO dto = unitSteps.buildUserDTO(1L, "alice", "alice@example.com");
        unitSteps.userDTOEmailEquals(dto, "alice@example.com");
    }

    @Test
    void nullIdConstructorHasNullId() {
        UserDTO dto = unitSteps.buildUserDTO(null, "bob", "bob@example.com");
        unitSteps.userDTOIdIsNull(dto);
        unitSteps.userDTOUsernameEquals(dto, "bob");
    }

    @Test
    void setterUpdatesUsername() {
        UserDTO dto = unitSteps.buildUserDTO(1L, "carol", "carol@example.com");
        unitSteps.userDTOSetUsername(dto, "carol-updated");
    }

    @Test
    void setterUpdatesEmail() {
        UserDTO dto = unitSteps.buildUserDTO(1L, "dave", "dave@example.com");
        unitSteps.userDTOSetEmail(dto, "dave-new@example.com");
    }

    @Test
    void setterUpdatesId() {
        UserDTO dto = unitSteps.buildUserDTO(null, "eve", "eve@example.com");
        unitSteps.userDTOSetId(dto, 42L);
    }

    @Test
    void usernameIsNotBlankAfterConstruction() {
        UserDTO dto = unitSteps.buildUserDTO(1L, "frank", "frank@example.com");
        unitSteps.userDTOUsernameIsNotBlank(dto);
    }

    @Test
    void emailContainsAtSymbol() {
        UserDTO dto = unitSteps.buildUserDTO(1L, "grace", "grace@example.com");
        unitSteps.userDTOEmailContainsAt(dto);
    }
}
