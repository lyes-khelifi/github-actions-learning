package org.example.util;

import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SerenityJUnit5Extension.class)
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