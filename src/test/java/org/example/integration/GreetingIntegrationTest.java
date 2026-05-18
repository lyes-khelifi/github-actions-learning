package org.example.integration;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.TestSecurityConfig;
import org.example.steps.ApiSteps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(SerenityJUnit5Extension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@Tag("integration")
@WithTagValuesOf({"integration", "greeting"})
public class GreetingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Steps
    ApiSteps apiSteps;

    @BeforeEach
    void setUp() {
        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken("john", "john"));
        apiSteps.setMockMvc(mockMvc);
    }

    // 2 steps
    @Test
    void greetingEndpointReturnsOkStatus() throws Exception {
        apiSteps.verifyGreetingStatusIsOk("Alice");
        apiSteps.verifyGreetingForName("Alice");
    }

    // 2 steps
    @Test
    void greetingWithDifferentNamesReturnCorrectMessages() throws Exception {
        apiSteps.verifyGreetingForName("Bob");
        apiSteps.verifyGreetingForName("Charlie");
    }

    // 3 steps
    @Test
    void greetingIsAccessibleAndHealthIsUp() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.verifyGreetingForName("Dave");
        apiSteps.healthEndpointReturnsOk();
    }

    // 3 steps
    @Test
    void greetingAndMetricsAreOperational() throws Exception {
        apiSteps.verifyGreetingStatusIsOk("Eve");
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.metricsEndpointReturnsMetrics();
    }

    // 3 steps
    @Test
    void greetingForNumericNameIsOk() throws Exception {
        apiSteps.verifyGreetingStatusIsOk("User99");
        apiSteps.verifyGreetingForName("User99");
        apiSteps.verifyGreetingStatusIsOk("Test42");
    }

    // 4 steps
    @Test
    void greetingCoreFlowWithHealthAndMetrics() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.verifyGreetingForName("Frank");
        apiSteps.healthEndpointReturnsOk();
        apiSteps.metricsEndpointReturnsMetrics();
    }

    // 4 steps
    @Test
    void multipleGreetingsAndStatusChecks() throws Exception {
        apiSteps.verifyGreetingForName("Grace");
        apiSteps.verifyGreetingStatusIsOk("Henry");
        apiSteps.verifyGreetingForName("Ivy");
        apiSteps.verifyGreetingStatusIsOk("Jack");
    }

    // 4 steps
    @Test
    void greetingWithHealthDetailsVerification() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.verifyGreetingForName("Kate");
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthDetailsPresent();
    }

    // 5 steps
    @Test
    void greetingIntegrationWithFullHealthCheck() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.verifyGreetingForName("Leo");
        apiSteps.verifyGreetingStatusIsOk("Mia");
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthDetailsPresent();
    }

    // 5 steps
    @Test
    void greetingEndpointSmokeWithAllCoreEndpoints() throws Exception {
        apiSteps.verifyGreetingForName("Nina");
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.healthEndpointReturnsOk();
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyAllUsersEndpoint();
    }
}
