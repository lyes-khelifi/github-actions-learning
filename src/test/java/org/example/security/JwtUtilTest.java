package org.example.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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