package org.example.unit;

import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.util.Constants;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "util"})
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
