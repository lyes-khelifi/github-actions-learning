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
@WithTagValuesOf({"integration", "user"})
public class UserCrudIntegrationTest {

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
    void createUserAndVerifyIdIsPositive() throws Exception {
        long id = apiSteps.createNamedUserStep("crud-it-1", "crud-it-1@example.com");
        apiSteps.verifyUserIdIsPositive(id);
    }

    // 3 steps
    @Test
    void createUserThenRetrieveById() throws Exception {
        long id = apiSteps.createNamedUserStep("crud-it-2", "crud-it-2@example.com");
        apiSteps.verifyUserIdIsPositive(id);
        apiSteps.getUserByIdStep(id, "crud-it-2");
    }

    // 3 steps
    @Test
    void createUserThenDelete() throws Exception {
        long id = apiSteps.createNamedUserStep("crud-it-3", "crud-it-3@example.com");
        apiSteps.getUserByIdStep(id, "crud-it-3");
        apiSteps.deleteUserByIdStep(id);
    }

    // 4 steps
    @Test
    void createUserVerifyEmailThenDelete() throws Exception {
        long id = apiSteps.createNamedUserStep("crud-it-4", "crud-it-4@example.com");
        apiSteps.verifyUserIdIsPositive(id);
        apiSteps.getUserByIdStep(id, "crud-it-4");
        apiSteps.deleteUserByIdStep(id);
    }

    // 4 steps
    @Test
    void createUpdateAndVerifyUser() throws Exception {
        long id = apiSteps.createNamedUserStep("crud-it-5", "crud-it-5@example.com");
        apiSteps.getUserByIdStep(id, "crud-it-5");
        apiSteps.updateUserStep(id, "crud-it-5-updated", "crud-it-5-updated@example.com");
        apiSteps.getUserByIdStep(id, "crud-it-5-updated");
    }

    // 4 steps
    @Test
    void createUserWithEmailVerification() throws Exception {
        long id = apiSteps.createNamedUserStep("crud-it-6", "crud-it-6@example.com");
        apiSteps.verifyUserIdIsPositive(id);
        apiSteps.verifyUserEmailById(id, "crud-it-6@example.com");
        apiSteps.deleteUserByIdStep(id);
    }

    // 5 steps
    @Test
    void fullUserLifecycleCreate_Get_Update_Get_Delete() throws Exception {
        long id = apiSteps.createNamedUserStep("crud-it-7", "crud-it-7@example.com");
        apiSteps.getUserByIdStep(id, "crud-it-7");
        apiSteps.updateUserStep(id, "crud-it-7-v2", "crud-it-7-v2@example.com");
        apiSteps.getUserByIdStep(id, "crud-it-7-v2");
        apiSteps.deleteUserByIdStep(id);
    }

    // 5 steps
    @Test
    void createUserAndVerifyAllFields() throws Exception {
        long id = apiSteps.createNamedUserStep("crud-it-8", "crud-it-8@example.com");
        apiSteps.verifyUserIdIsPositive(id);
        apiSteps.getUserByIdStep(id, "crud-it-8");
        apiSteps.verifyUserEmailById(id, "crud-it-8@example.com");
        apiSteps.deleteUserByIdStep(id);
    }

    // 5 steps
    @Test
    void userCrudWithGreetingIntegration() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
        long id = apiSteps.createNamedUserStep("crud-it-9", "crud-it-9@example.com");
        apiSteps.getUserByIdStep(id, "crud-it-9");
        apiSteps.verifyAllUsersEndpoint();
        apiSteps.deleteUserByIdStep(id);
    }

    // 5 steps
    @Test
    void userCrudWithHealthCheck() throws Exception {
        apiSteps.healthEndpointReturnsOk();
        long id = apiSteps.createNamedUserStep("crud-it-10", "crud-it-10@example.com");
        apiSteps.verifyUserIdIsPositive(id);
        apiSteps.getUserByIdStep(id, "crud-it-10");
        apiSteps.deleteUserByIdStep(id);
    }

    // 5 steps
    @Test
    void updateUserEmailAndVerify() throws Exception {
        long id = apiSteps.createNamedUserStep("crud-it-11", "crud-it-11@example.com");
        apiSteps.verifyUserIdIsPositive(id);
        apiSteps.verifyUserEmailById(id, "crud-it-11@example.com");
        apiSteps.updateUserStep(id, "crud-it-11", "crud-it-11-new@example.com");
        apiSteps.deleteUserByIdStep(id);
    }
}
