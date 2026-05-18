package org.example.unit;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.service.GreetingService;
import org.example.steps.UnitSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "greeting"})
public class GreetingServiceExtendedTest {

    @Steps
    UnitSteps unitSteps;

    @BeforeEach
    void setUp() {
        unitSteps.setGreetingService(new GreetingService());
    }

    @Test
    void greetingReturnsHelloWorld() {
        unitSteps.greetingReturnsHello("World");
    }

    @Test
    void greetingReturnsHelloAlice() {
        unitSteps.greetingReturnsHello("Alice");
    }

    @Test
    void greetingWithNumericName() {
        unitSteps.greetingReturnsHello("User123");
    }

    @Test
    void greetingResultStartsWithHelloPrefix() {
        String result = unitSteps.greetingReturnsHello("Test");
        unitSteps.greetingResultStartsWithHello(result);
    }

    @Test
    void greetingResultContainsProvidedName() {
        String result = unitSteps.greetingReturnsHello("Bob");
        unitSteps.greetingResultContainsName(result, "Bob");
    }

    @Test
    void greetingWithSingleCharName() {
        unitSteps.greetingReturnsHello("A");
    }

    @Test
    void greetingWithLongName() {
        String result = unitSteps.greetingReturnsHello("VeryLongUserNameHere");
        unitSteps.greetingResultIsNotBlank(result);
    }

    @Test
    void greetingResultIsNeverBlank() {
        String result = unitSteps.greetingReturnsHello("Dave");
        unitSteps.greetingResultIsNotBlank(result);
        unitSteps.greetingResultHasLength(result);
    }

    @Test
    void greetingWithSpecialCharsInName() {
        unitSteps.greetingReturnsHello("O'Brien");
    }

    @Test
    void greetingFormatIsHelloCommaName() {
        String result = unitSteps.greetingReturnsHello("Eve");
        unitSteps.greetingResultEquals(result, "Hello, Eve");
    }
}
