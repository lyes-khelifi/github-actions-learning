package org.example.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConstantsTest {
    @Test
    void testConstantsExist() {
        assertNotNull(Constants.GREETING_KEY);
        assertNotNull(Constants.AUTHOR_KEY);
        assertNotNull(Constants.VERSION_KEY);
    }

    @Test
    void testGreetingKeyValue() {
        assertEquals("greeting", Constants.GREETING_KEY);
    }
}