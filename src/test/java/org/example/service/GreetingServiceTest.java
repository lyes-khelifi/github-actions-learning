package org.example.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
