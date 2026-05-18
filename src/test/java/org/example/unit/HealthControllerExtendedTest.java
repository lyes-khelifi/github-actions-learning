package org.example.unit;

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
@Tag("unit")
@WithTagValuesOf({"unit", "health"})
public class HealthControllerExtendedTest {

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

    @Test
    void healthStatusIsUp() throws Exception {
        apiSteps.verifyHealthStatusIsUp();
    }

    @Test
    void healthDetailsArePresentInResponse() throws Exception {
        apiSteps.verifyHealthDetailsPresent();
    }

    @Test
    void healthServiceNameIsCorrect() throws Exception {
        apiSteps.verifyHealthDetailsPresent();
        apiSteps.healthEndpointReturnsOk();
    }

    @Test
    void healthVersionDetailIsPresent() throws Exception {
        apiSteps.verifyHealthVersionPresent();
    }

    @Test
    void metricsEndpointReturns200() throws Exception {
        apiSteps.metricsEndpointReturnsMetrics();
    }

    @Test
    void metricsStatusIsHealthy() throws Exception {
        apiSteps.verifyMetricsStatusIsHealthy();
    }

    @Test
    void metricsUptimeIsPositive() throws Exception {
        apiSteps.verifyMetricsUptimeIsPositive();
    }

    @Test
    void metricsMemoryIsNonNegative() throws Exception {
        apiSteps.verifyMetricsMemoryIsNonNegative();
    }

    @Test
    void metricsProcessorsIsPositive() throws Exception {
        apiSteps.verifyMetricsProcessorsIsPositive();
    }

    @Test
    void healthAndMetricsBothReachable() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        apiSteps.metricsEndpointReturnsMetrics();
    }
}
