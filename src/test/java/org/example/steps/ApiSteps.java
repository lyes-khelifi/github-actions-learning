package org.example.steps;

import net.serenitybdd.annotations.Step;
import org.junit.Assert;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.springframework.security.authentication.AuthenticationManager;

public class ApiSteps {

    private MockMvc mockMvc;
    private AuthenticationManager authenticationManager;
    private Response response;

    public ApiSteps() {
    }

    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Step("Greeting endpoint returns a proper message")
    public void greetingEndpointReturnsMessage() throws Exception {
        mockMvc.perform(get("/api/greeting/John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, John"));
    }

    @Step("Health endpoint returns UP status")
    public void healthEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Step("Metrics endpoint returns system metrics")
    public void metricsEndpointReturnsMetrics() throws Exception {
        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", notNullValue()))
                .andExpect(jsonPath("$.uptime", notNullValue()))
                .andExpect(jsonPath("$.memory", notNullValue()))
                .andExpect(jsonPath("$.processors", notNullValue()));
    }

    @Step("Navigate to homepage")
    public void iNavigateToHomepage() {
        RestAssured.baseURI = "https://httpbin.org";
    }

    @Step("Get request to endpoint")
    public void iGetRequestToEndpoint() {
        response = RestAssured.given().get("/get");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Step("Execute user controller setup")
 public void execute() {
       // This step is handled separately in UserIntegrationTest
  }

   @Step("Create user step")
    public void createUserStep() {
        // Implementation for user creation test step
   }

  @Step("Get user step")
   public void getUserStep() {
      // Implementation for user retrieval test step
   }

    @Step("Delete user step")
   public void deleteUserStep() {
           // Implementation for user deletion test step
   }

   @Step
    public void failTestExample() {
       Assert.assertFalse("This test should fail as an example to check the reporting", true);
   }

}
