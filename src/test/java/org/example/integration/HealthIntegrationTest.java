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
@WithTagValuesOf({"integration", "health"})
public class HealthIntegrationTest {

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
    void healthStatusIsUp() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthStatusIsUp();
    }

    // 2 steps
    @Test
    void healthDetailsArePresentInResponse() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthDetailsPresent();
    }

    // 3 steps
    @Test
    void healthServiceNameAndVersionArePresent() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthDetailsPresent();
        apiSteps.verifyHealthVersionPresent();
    }

    // 3 steps
    @Test
    void healthAndGreetingAreAccessible() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthStatusIsUp();
        apiSteps.greetingEndpointReturnsMessage();
    }

    // 3 steps
    @Test
    void healthStatusAndDetailsConsistency() throws Exception {
        apiSteps.verifyHealthStatusIsUp();
        apiSteps.verifyHealthDetailsPresent();
        apiSteps.verifyHealthVersionPresent();
    }

    // 4 steps
    @Test
    void healthFullVerificationFlow() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthStatusIsUp();
        apiSteps.verifyHealthDetailsPresent();
        apiSteps.verifyHealthVersionPresent();
    }

    // 4 steps
    @Test
    void healthWithGreetingAndMetrics() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthStatusIsUp();
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.metricsEndpointReturnsMetrics();
    }

    // 4 steps
    @Test
    void healthEndpointSmokeCheck() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthDetailsPresent();
        apiSteps.verifyAllUsersEndpoint();
    }

    // 5 steps
    @Test
    void healthIntegrationWithAllChecks() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthStatusIsUp();
        apiSteps.verifyHealthDetailsPresent();
        apiSteps.verifyHealthVersionPresent();
        apiSteps.greetingEndpointReturnsMessage();
    }

    // 5 steps
    @Test
    void healthAndMetricsFullIntegrationCheck() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.verifyHealthStatusIsUp();
        apiSteps.verifyHealthDetailsPresent();
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsStatusIsHealthy();
    }
}
