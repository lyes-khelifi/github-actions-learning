package org.example.steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import org.assertj.core.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegressionSteps {

    private static final Logger log = LoggerFactory.getLogger(RegressionSteps.class);

    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Step("Greeting endpoint returns 'Hello, {0}'")
    public void greetingReturnsHello(String name) {
        log.info("Calling greeting endpoint with name: {}", name);
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/greeting/" + name);
        log.info("Greeting response status: {}, body: {}", response.statusCode(), response.getBody().asString());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.getBody().asString()).isEqualTo("Hello, " + name);
    }

    @Step("Health endpoint reports status 200 and UP")
    public void healthReturnsUp() {
        log.info("Calling health endpoint");
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/health");
        log.info("Health response status: {}, body: {}", response.statusCode(), response.getBody().asString());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("status")).isEqualTo("UP");
    }

    @Step("Health endpoint contains service detail")
    public void healthContainsServiceDetail() {
        log.info("Verifying health endpoint contains service details");
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/health");
        log.info("Health details: {}", response.jsonPath().getMap("details"));
        Assertions.assertThat(response.jsonPath().getMap("details")).isNotNull();
    }

    @Step("Metrics endpoint returns status 200")
    public void metricsReturns200() {
        log.info("Calling metrics endpoint");
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/metrics");
        log.info("Metrics response status: {}", response.statusCode());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Step("Metrics endpoint contains all required fields")
    public void metricsContainsAllFields() {
        log.info("Verifying metrics endpoint contains all required fields");
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/metrics");
        log.info("Metrics body: {}", response.getBody().asString());
        Assertions.assertThat(response.jsonPath().getString("status")).isNotNull();
        Assertions.assertThat(response.jsonPath().getLong("uptime")).isGreaterThan(0L);
        Assertions.assertThat(response.jsonPath().getLong("memory")).isGreaterThanOrEqualTo(0L);
        Assertions.assertThat(response.jsonPath().getInt("processors")).isGreaterThan(0);
    }

    @Step("Metrics status field is 'healthy'")
    public void metricsStatusIsHealthy() {
        log.info("Verifying metrics status is 'healthy'");
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/metrics");
        String status = response.jsonPath().getString("status");
        log.info("Metrics status: {}", status);
        Assertions.assertThat(status).isEqualTo("healthy");
    }

    @Step("Create user with username '{0}' and email '{1}'")
    public long createUser(String username, String email) {
        log.info("Creating user: username={}, email={}", username, email);
        String body = String.format("{\"username\":\"%s\",\"email\":\"%s\"}", username, email);
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/api/users/register");
        log.info("Create user response status: {}, body: {}", response.statusCode(), response.getBody().asString());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        long id = response.jsonPath().getLong("id");
        log.info("Created user with id: {}", id);
        return id;
    }

    @Step("Create user '{0}' and verify username and email match")
    public void createUserAndVerify(String username, String email) {
        log.info("Creating and verifying user: username={}, email={}", username, email);
        String body = String.format("{\"username\":\"%s\",\"email\":\"%s\"}", username, email);
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/api/users/register");
        log.info("Create user response status: {}, body: {}", response.statusCode(), response.getBody().asString());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("username")).isEqualTo(username);
        Assertions.assertThat(response.jsonPath().getString("email")).isEqualTo(email);
    }

    @Step("Get user by ID {0} returns correct username")
    public void getUserByIdReturnsCorrectData(long id, String expectedUsername) {
        log.info("Getting user by id: {}, expecting username: {}", id, expectedUsername);
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users/" + id);
        log.info("Get user response status: {}, body: {}", response.statusCode(), response.getBody().asString());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("username")).isEqualTo(expectedUsername);
    }

    @Step("Update user {0} and verify username is '{1}'")
    public void updateUserAndVerify(long id, String username, String email) {
        log.info("Updating user id={} with username={}, email={}", id, username, email);
        String body = String.format("{\"username\":\"%s\",\"email\":\"%s\"}", username, email);
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/api/users/" + id);
        log.info("Update user response status: {}, body: {}", response.statusCode(), response.getBody().asString());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("username")).isEqualTo(username);
    }

    @Step("Delete user {0} returns 204 No Content")
    public void deleteUserReturnsNoContent(long id) {
        log.info("Deleting user with id: {}", id);
        Response response = RestAssured.given().baseUri(baseUrl).delete("/api/users/" + id);
        log.info("Delete user response status: {}", response.statusCode());
        Assertions.assertThat(response.statusCode()).isEqualTo(204);
    }

    @Step("Get all users returns 200")
    public void getAllUsersReturns200() {
        log.info("Getting all users");
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users");
        log.info("Get all users response status: {}", response.statusCode());
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Step("Get total number of users in the system")
    public int getAllUsersCount() {
        log.info("Getting total user count");
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        int count = response.jsonPath().getList("$").size();
        log.info("Total user count: {}", count);
        return count;
    }

    @Step("Verify system has at least {0} users")
    public void verifyUserCountIsAtLeast(int minExpected) {
        log.info("Verifying user count is at least: {}", minExpected);
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        int count = response.jsonPath().getList("$").size();
        log.info("Current user count: {}, expected at least: {}", count, minExpected);
        Assertions.assertThat(count).isGreaterThanOrEqualTo(minExpected);
    }

    @Step("Verify user with id {0} is not retrievable")
    public void verifyUserNotFound(long id) {
        log.info("Verifying user with id {} is not found", id);
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users/" + id);
        log.info("Get deleted user response status: {}", response.statusCode());
        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }

    @Step("Verify metrics reports a positive processor count")
    public void verifyMetricsProcessorCountPositive() {
        log.info("Verifying metrics processor count is positive");
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/metrics");
        int processors = response.jsonPath().getInt("processors");
        log.info("Processor count: {}", processors);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(processors).isGreaterThan(0);
    }
}
