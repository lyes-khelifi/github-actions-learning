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
@WithTagValuesOf({"unit", "greeting"})
public class GreetingControllerExtendedTest {

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
    void greetingForAliceReturnsHelloAlice() throws Exception {
        apiSteps.verifyGreetingForName("Alice");
    }

    @Test
    void greetingForBobReturnsHelloBob() throws Exception {
        apiSteps.verifyGreetingForName("Bob");
    }

    @Test
    void greetingForWorldReturnsHelloWorld() throws Exception {
        apiSteps.verifyGreetingForName("World");
    }

    @Test
    void greetingForNumericNameReturnsOk() throws Exception {
        apiSteps.verifyGreetingStatusIsOk("User123");
    }

    @Test
    void greetingForSingleCharReturnsOk() throws Exception {
        apiSteps.verifyGreetingStatusIsOk("A");
    }

    @Test
    void greetingEndpointIsAccessible() throws Exception {
        apiSteps.greetingEndpointReturnsMessage();
    }

    @Test
    void greetingForCharlieReturnsHelloCharlie() throws Exception {
        apiSteps.verifyGreetingForName("Charlie");
    }

    @Test
    void greetingForDaveReturnsHelloDave() throws Exception {
        apiSteps.verifyGreetingForName("Dave");
    }

    @Test
    void greetingForEveReturnsHelloEve() throws Exception {
        apiSteps.verifyGreetingForName("Eve");
    }

    @Test
    void greetingStatusIsOkForTestUser() throws Exception {
        apiSteps.verifyGreetingStatusIsOk("TestUser");
        apiSteps.verifyGreetingForName("TestUser");
    }
}
