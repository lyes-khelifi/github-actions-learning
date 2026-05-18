package org.example.unit;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.steps.UnitSteps;
import org.example.util.Constants;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "util"})
public class ConstantsExtendedTest {

    @Steps
    UnitSteps unitSteps;

    @Test
    void greetingKeyValueIsCorrect() {
        unitSteps.greetingKeyEqualsGreeting();
    }

    @Test
    void authorKeyValueIsCorrect() {
        unitSteps.authorKeyEqualsAuthor();
    }

    @Test
    void versionKeyValueIsCorrect() {
        unitSteps.versionKeyEqualsVersion();
    }

    @Test
    void allConstantsAreNotNull() {
        unitSteps.allConstantsAreNotNull();
    }

    @Test
    void allConstantsAreNotBlank() {
        unitSteps.allConstantsAreNotBlank();
    }

    @Test
    void greetingKeyIsNotNull() {
        unitSteps.constantIsNotNull(Constants.GREETING_KEY);
        unitSteps.constantEquals(Constants.GREETING_KEY, "greeting");
    }

    @Test
    void authorKeyIsNotNull() {
        unitSteps.constantIsNotNull(Constants.AUTHOR_KEY);
        unitSteps.constantEquals(Constants.AUTHOR_KEY, "author");
    }

    @Test
    void versionKeyIsNotNull() {
        unitSteps.constantIsNotNull(Constants.VERSION_KEY);
        unitSteps.constantEquals(Constants.VERSION_KEY, "version");
    }
}
