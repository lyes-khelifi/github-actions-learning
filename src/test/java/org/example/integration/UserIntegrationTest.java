package org.example.integration;

import org.example.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@ActiveProfiles("test")
public class UserIntegrationTest {

    @Autowired
    private UserController userController;

    private MockMvc mockMvc;

    @Test
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void contextLoads() {
        // This test ensures the Spring context loads correctly
        // with all the required beans and configurations
    }
}
