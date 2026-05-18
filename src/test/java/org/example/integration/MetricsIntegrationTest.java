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
@WithTagValuesOf({"integration", "metrics"})
public class MetricsIntegrationTest {

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
    void metricsEndpointReturns200() throws Exception {
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsStatusIsHealthy();
    }

    // 2 steps
    @Test
    void metricsUptimeFieldIsPositive() throws Exception {
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsUptimeIsPositive();
    }

    // 3 steps
    @Test
    void metricsMemoryAndProcessorsAreValid() throws Exception {
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsMemoryIsNonNegative();
        apiSteps.verifyMetricsProcessorsIsPositive();
    }

    // 3 steps
    @Test
    void metricsStatusAndUptimeCheck() throws Exception {
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsStatusIsHealthy();
        apiSteps.verifyMetricsUptimeIsPositive();
    }

    // 3 steps
    @Test
    void metricsWithHealthEndpointSmokeCheck() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsStatusIsHealthy();
    }

    // 4 steps
    @Test
    void metricsAllNumericFieldsAreValid() throws Exception {
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsUptimeIsPositive();
        apiSteps.verifyMetricsMemoryIsNonNegative();
        apiSteps.verifyMetricsProcessorsIsPositive();
    }

    // 4 steps
    @Test
    void metricsStatusAndNumericFieldsCheck() throws Exception {
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsStatusIsHealthy();
        apiSteps.verifyMetricsUptimeIsPositive();
        apiSteps.verifyMetricsMemoryIsNonNegative();
    }

    // 4 steps
    @Test
    void metricsWithGreetingAndHealthContext() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.healthEndpointReturnsOk();
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsStatusIsHealthy();
    }

    // 5 steps
    @Test
    void metricsFullFieldVerification() throws Exception {
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsStatusIsHealthy();
        apiSteps.verifyMetricsUptimeIsPositive();
        apiSteps.verifyMetricsMemoryIsNonNegative();
        apiSteps.verifyMetricsProcessorsIsPositive();
    }

    // 5 steps
    @Test
    void metricsIntegrationWithAllCoreEndpoints() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.healthEndpointReturnsOk();
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyMetricsStatusIsHealthy();
        apiSteps.verifyMetricsUptimeIsPositive();
    }
}
