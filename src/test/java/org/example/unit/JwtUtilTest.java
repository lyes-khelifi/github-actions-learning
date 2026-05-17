package org.example.unit;

import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.security.JwtUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "security"})
public class JwtUtilTest {

    @Test
    void testGenerateAndExtractUsername() {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        String extractedUsername = jwtUtil.extractUsername(token);
        assertEquals("testuser", extractedUsername);
    }

    @Test
    void testTokenExpiration() throws InterruptedException {
        JwtUtil jwtUtil = new JwtUtil();
        String token = jwtUtil.generateToken("testuser");
        Thread.sleep(2000);
        assertTrue(jwtUtil.isTokenValid(token, "testuser"));
    }
}
