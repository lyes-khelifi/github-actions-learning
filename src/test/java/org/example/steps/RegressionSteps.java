package org.example.steps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import org.assertj.core.api.Assertions;

public class RegressionSteps {

    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Step("Greeting endpoint returns 'Hello, {0}'")
    public void greetingReturnsHello(String name) {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/greeting/" + name);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.getBody().asString()).isEqualTo("Hello, " + name);
    }

    @Step("Health endpoint reports status 200 and UP")
    public void healthReturnsUp() {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/health");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("status")).isEqualTo("UP");
    }

    @Step("Health endpoint contains service detail")
    public void healthContainsServiceDetail() {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/health");
        Assertions.assertThat(response.jsonPath().getMap("details")).isNotNull();
    }

    @Step("Metrics endpoint returns status 200")
    public void metricsReturns200() {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/metrics");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Step("Metrics endpoint contains all required fields")
    public void metricsContainsAllFields() {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/metrics");
        Assertions.assertThat(response.jsonPath().getString("status")).isNotNull();
        Assertions.assertThat(response.jsonPath().getLong("uptime")).isGreaterThan(0L);
        Assertions.assertThat(response.jsonPath().getLong("memory")).isGreaterThanOrEqualTo(0L);
        Assertions.assertThat(response.jsonPath().getInt("processors")).isGreaterThan(0);
    }

    @Step("Metrics status field is 'healthy'")
    public void metricsStatusIsHealthy() {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/metrics");
        Assertions.assertThat(response.jsonPath().getString("status")).isEqualTo("healthy");
    }

    @Step("Create user with username '{0}' and email '{1}'")
    public long createUser(String username, String email) {
        String body = String.format("{\"username\":\"%s\",\"email\":\"%s\"}", username, email);
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/api/users/register");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        return response.jsonPath().getLong("id");
    }

    @Step("Create user '{0}' and verify username and email match")
    public void createUserAndVerify(String username, String email) {
        String body = String.format("{\"username\":\"%s\",\"email\":\"%s\"}", username, email);
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .post("/api/users/register");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("username")).isEqualTo(username);
        Assertions.assertThat(response.jsonPath().getString("email")).isEqualTo(email);
    }

    @Step("Get user by ID {0} returns correct username")
    public void getUserByIdReturnsCorrectData(long id, String expectedUsername) {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users/" + id);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("username")).isEqualTo(expectedUsername);
    }

    @Step("Update user {0} and verify username is '{1}'")
    public void updateUserAndVerify(long id, String username, String email) {
        String body = String.format("{\"username\":\"%s\",\"email\":\"%s\"}", username, email);
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body)
                .put("/api/users/" + id);
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getString("username")).isEqualTo(username);
    }

    @Step("Delete user {0} returns 204 No Content")
    public void deleteUserReturnsNoContent(long id) {
        Response response = RestAssured.given().baseUri(baseUrl).delete("/api/users/" + id);
        Assertions.assertThat(response.statusCode()).isEqualTo(204);
    }

    @Step("Get all users returns 200")
    public void getAllUsersReturns200() {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
    }

    @Step("Get total number of users in the system")
    public int getAllUsersCount() {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        return response.jsonPath().getList("$").size();
    }

    @Step("Verify system has at least {0} users")
    public void verifyUserCountIsAtLeast(int minExpected) {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getList("$").size()).isGreaterThanOrEqualTo(minExpected);
    }

    @Step("Verify user with id {0} is not retrievable")
    public void verifyUserNotFound(long id) {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/users/" + id);
        Assertions.assertThat(response.statusCode()).isEqualTo(400);
    }

    @Step("Verify metrics reports a positive processor count")
    public void verifyMetricsProcessorCountPositive() {
        Response response = RestAssured.given().baseUri(baseUrl).get("/api/metrics");
        Assertions.assertThat(response.statusCode()).isEqualTo(200);
        Assertions.assertThat(response.jsonPath().getInt("processors")).isGreaterThan(0);
    }
}
