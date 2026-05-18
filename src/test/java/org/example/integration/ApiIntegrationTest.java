package org.example.integration;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.assertj.core.api.Assertions;
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
@WithTagValuesOf({"integration", "api"})
public class ApiIntegrationTest {

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
    void userRegistrationCreatesRetrievableUser() throws Exception {
        long id = apiSteps.createNamedUserStep("int-user1", "int-user1@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        apiSteps.getUserByIdStep(id, "int-user1");
        apiSteps.verifyGreetingForName("IntegrationTest");
        apiSteps.healthEndpointReturnsOk();
        apiSteps.deleteUserByIdStep(id);
        apiSteps.failTestExample();
    }

    @Test
    void userRegistrationAndDeletion() throws Exception {
        long id = apiSteps.createNamedUserStep("int-del-user", "int-del@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        apiSteps.getUserByIdStep(id, "int-del-user");
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.deleteUserByIdStep(id);
        apiSteps.greetingEndpointReturnsMessage();
     //   apiSteps.failTestExample();
    }

    @Test
    void userUpdateFlowWithApiVerification() throws Exception {
        long id = apiSteps.createNamedUserStep("int-upd-user", "int-upd@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        apiSteps.getUserByIdStep(id, "int-upd-user");
        apiSteps.updateUserStep(id, "int-upd-user-v2", "int-upd-v2@example.com");
        apiSteps.getUserByIdStep(id, "int-upd-user-v2");
        apiSteps.deleteUserByIdStep(id);
     //   apiSteps.failTestExample();
    }

    @Test
    void allCoreEndpointsAreOperational() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        apiSteps.healthEndpointReturnsOk();
        apiSteps.metricsEndpointReturnsMetrics();
        apiSteps.verifyAllUsersEndpoint();
        apiSteps.verifyHealthDetailsPresent();
    }

    @Test
    void userLifecycleWithGreetingAndHealthVerification() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        long id = apiSteps.createNamedUserStep("int-lifecycle", "int-lifecycle@example.com");
        Assertions.assertThat(id).isGreaterThan(0L);
        apiSteps.getUserByIdStep(id, "int-lifecycle");
        apiSteps.verifyGreetingForName("LifecycleTest");
        apiSteps.deleteUserByIdStep(id);
    }
}
