package org.example.service;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.thucydides.core.annotations.WithTagValuesOf;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "greeting"})
public class GreetingServiceTest {
    @Test
    void testGreeting() {
        GreetingService service = new GreetingService();
        String result = service.getGreeting("World");
        assertEquals("Hello, World", result);
    }

    @Test
    void testGreetingWithEmptyName() {
        GreetingService service = new GreetingService();
        String result = service.getGreeting("");
        assertEquals("Hello, ", result);
    }

    @Test
    void testGreetingWithSpecialCharacters() {
        GreetingService service = new GreetingService();
        String result = service.getGreeting("O'Brien");
        assertEquals("Hello, O'Brien", result);
    }
}
