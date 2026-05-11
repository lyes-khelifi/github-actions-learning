package org.example.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class GreetingControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void greetingEndpointReturnsMessage() throws Exception {
        mockMvc.perform(get("/api/greeting/John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, John"));
    }
}
