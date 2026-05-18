package org.example.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ApiSteps {

    private static final Logger log = LoggerFactory.getLogger(ApiSteps.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;
    private Response response;

    public ApiSteps() {}

    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Step("Greeting endpoint returns a proper message")
    public void greetingEndpointReturnsMessage() throws Exception {
        log.info("Calling GET /api/greeting/John via MockMvc");
        mockMvc.perform(get("/api/greeting/John"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, John"));
        log.info("Greeting endpoint returned expected response");
    }

    @Step("Health endpoint returns UP status")
    public void healthEndpointReturnsOk() throws Exception {
        log.info("Calling GET /api/health via MockMvc");
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
        log.info("Health endpoint returned UP status");
    }

    @Step("Metrics endpoint returns system metrics")
    public void metricsEndpointReturnsMetrics() throws Exception {
        log.info("Calling GET /api/metrics via MockMvc");
        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", notNullValue()))
                .andExpect(jsonPath("$.uptime", notNullValue()))
                .andExpect(jsonPath("$.memory", notNullValue()))
                .andExpect(jsonPath("$.processors", notNullValue()));
        log.info("Metrics endpoint returned all required fields");
    }

    @Step("Navigate to homepage")
    public void iNavigateToHomepage() {
        log.info("Setting RestAssured base URI to https://httpbin.org");
        RestAssured.baseURI = "https://httpbin.org";
    }

    @Step("Get request to endpoint")
    public void iGetRequestToEndpoint() {
        log.info("Performing GET /get to httpbin.org");
        response = RestAssured.given().get("/get");
        log.info("Response status: {}", response.getStatusCode());
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Step("Create user via POST /api/users/register")
    public void createUserStep() throws Exception {
        if (mockMvc == null) {
            log.warn("MockMvc not configured — skipping createUserStep");
            return;
        }
        log.info("Creating user via POST /api/users/register");
        String body = "{\"username\":\"integrationUser\",\"email\":\"integration@example.com\"}";
        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("integrationUser"))
                .andExpect(jsonPath("$.email").value("integration@example.com"))
                .andExpect(jsonPath("$.id").isNumber());
        log.info("User created and response fields verified");
    }

    @Step("Create user then retrieve by ID")
    public void getUserStep() throws Exception {
        if (mockMvc == null) {
            log.warn("MockMvc not configured — skipping getUserStep");
            return;
        }
        log.info("Creating user to retrieve by ID");
        String body = "{\"username\":\"getUserTest\",\"email\":\"getuser@example.com\"}";
        MvcResult createResult = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andReturn();
        long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();
        Assertions.assertThat(id).isGreaterThan(0L);
        log.info("Created user with id: {}, now retrieving", id);
        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("getUserTest"))
                .andExpect(jsonPath("$.id").value(id));
        log.info("User retrieved and data verified");
    }

    @Step("Create user then delete by ID")
    public void deleteUserStep() throws Exception {
        if (mockMvc == null) {
            log.warn("MockMvc not configured — skipping deleteUserStep");
            return;
        }
        log.info("Creating user to delete by ID");
        String body = "{\"username\":\"deleteUserTest\",\"email\":\"deleteuser@example.com\"}";
        MvcResult createResult = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andReturn();
        long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();
        Assertions.assertThat(id).isGreaterThan(0L);
        log.info("Created user with id: {}, now deleting", id);
        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isNoContent());
        log.info("User deleted successfully");
    }

    @Step("Execute user controller setup")
    public void execute() {
        log.info("Execute step invoked");
    }

    @Step
    public void failTestExample() {
        log.warn("Intentional failure step invoked");
        Assert.assertFalse("This test should fail as an example to check the reporting", true);
    }

    @Step("Create user '{0}' with email '{1}' and return ID")
    public long createNamedUserStep(String username, String email) throws Exception {
        if (mockMvc == null) {
            log.warn("MockMvc not configured — skipping createNamedUserStep");
            return -1L;
        }
        log.info("Creating named user: username={}, email={}", username, email);
        String body = String.format("{\"username\":\"%s\",\"email\":\"%s\"}", username, email);
        MvcResult result = mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andReturn();
        long id = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
        log.info("Created named user with id: {}", id);
        return id;
    }

    @Step("Get user by ID {0} and verify username is '{1}'")
    public void getUserByIdStep(long id, String expectedUsername) throws Exception {
        if (mockMvc == null) {
            log.warn("MockMvc not configured — skipping getUserByIdStep");
            return;
        }
        log.info("Getting user by id={}, expecting username: {}", id, expectedUsername);
        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(expectedUsername));
        log.info("User id={} username verified: {}", id, expectedUsername);
    }

    @Step("Update user {0} with username '{1}' and email '{2}'")
    public void updateUserStep(long id, String username, String email) throws Exception {
        if (mockMvc == null) {
            log.warn("MockMvc not configured — skipping updateUserStep");
            return;
        }
        log.info("Updating user id={} with username={}, email={}", id, username, email);
        String body = String.format("{\"username\":\"%s\",\"email\":\"%s\"}", username, email);
        mockMvc.perform(put("/api/users/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value(username));
        log.info("User id={} updated successfully", id);
    }

    @Step("Delete user by ID {0}")
    public void deleteUserByIdStep(long id) throws Exception {
        if (mockMvc == null) {
            log.warn("MockMvc not configured — skipping deleteUserByIdStep");
            return;
        }
        log.info("Deleting user with id: {}", id);
        mockMvc.perform(delete("/api/users/" + id))
                .andExpect(status().isNoContent());
        log.info("User id={} deleted", id);
    }

    @Step("Verify greeting endpoint responds correctly for '{0}'")
    public void verifyGreetingForName(String name) throws Exception {
        log.info("Verifying greeting for name: {}", name);
        mockMvc.perform(get("/api/greeting/" + name))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, " + name));
        log.info("Greeting for '{}' verified", name);
    }

    @Step("Verify health response contains service details")
    public void verifyHealthDetailsPresent() throws Exception {
        log.info("Verifying health endpoint has service details");
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details").exists())
                .andExpect(jsonPath("$.details.service").value("github-actions-learning"));
        log.info("Health service details verified");
    }

    @Step("Verify all users endpoint returns a list")
    public void verifyAllUsersEndpoint() throws Exception {
        log.info("Verifying GET /api/users returns 200");
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
        log.info("All users endpoint verified");
    }

    @Step("Verify metrics status field is 'healthy'")
    public void verifyMetricsStatusIsHealthy() throws Exception {
        log.info("Verifying metrics status is 'healthy'");
        mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("healthy"));
        log.info("Metrics status verified as healthy");
    }

    @Step("Verify metrics uptime is a positive number")
    public void verifyMetricsUptimeIsPositive() throws Exception {
        log.info("Verifying metrics uptime field is positive");
        MvcResult result = mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andReturn();
        com.fasterxml.jackson.databind.JsonNode body =
                objectMapper.readTree(result.getResponse().getContentAsString());
        Assertions.assertThat(body.get("uptime").asLong()).isGreaterThan(0L);
        log.info("Metrics uptime verified as positive");
    }

    @Step("Verify metrics memory is non-negative")
    public void verifyMetricsMemoryIsNonNegative() throws Exception {
        log.info("Verifying metrics memory field is non-negative");
        MvcResult result = mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andReturn();
        com.fasterxml.jackson.databind.JsonNode body =
                objectMapper.readTree(result.getResponse().getContentAsString());
        Assertions.assertThat(body.get("memory").asLong()).isGreaterThanOrEqualTo(0L);
        log.info("Metrics memory verified as non-negative");
    }

    @Step("Verify metrics processors count is positive")
    public void verifyMetricsProcessorsIsPositive() throws Exception {
        log.info("Verifying metrics processors count is positive");
        MvcResult result = mockMvc.perform(get("/api/metrics"))
                .andExpect(status().isOk())
                .andReturn();
        com.fasterxml.jackson.databind.JsonNode body =
                objectMapper.readTree(result.getResponse().getContentAsString());
        Assertions.assertThat(body.get("processors").asInt()).isGreaterThan(0);
        log.info("Metrics processors count verified as positive");
    }

    @Step("Verify health response includes a version detail")
    public void verifyHealthVersionPresent() throws Exception {
        log.info("Verifying health endpoint has a version detail");
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.details.version").exists());
        log.info("Health version detail verified");
    }

    @Step("Verify health status is UP")
    public void verifyHealthStatusIsUp() throws Exception {
        log.info("Verifying health status field equals UP");
        mockMvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
        log.info("Health status UP verified");
    }

    @Step("Verify greeting for '{0}' returns HTTP 200")
    public void verifyGreetingStatusIsOk(String name) throws Exception {
        log.info("Verifying greeting status 200 for name: {}", name);
        mockMvc.perform(get("/api/greeting/" + name))
                .andExpect(status().isOk());
        log.info("Greeting status 200 verified for: {}", name);
    }

    @Step("Verify user id {0} has email '{1}'")
    public void verifyUserEmailById(long id, String expectedEmail) throws Exception {
        log.info("Verifying email for user id={}, expected: {}", id, expectedEmail);
        mockMvc.perform(get("/api/users/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(expectedEmail));
        log.info("User email verified for id={}", id);
    }

    @Step("Verify user id {0} is positive")
    public void verifyUserIdIsPositive(long id) {
        log.info("Verifying user id {} is positive", id);
        Assertions.assertThat(id).isGreaterThan(0L);
        log.info("User id {} verified as positive", id);
    }
}
