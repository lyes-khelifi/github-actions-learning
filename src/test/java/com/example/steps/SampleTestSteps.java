package com.example.steps;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.junit5.SerenityTestExecutionListener;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;

//@ExtendWith(net.serenitybdd.junit5.extensions.SerenityJUnit5Extension.class)
@ExtendWith(SerenityJUnit5Extension.class)
public class SampleTestSteps {

    @Step
    public void iNavigateToHomepage() {
        RestAssured.baseURI = "https://google.com";
    }

    @Step
    public void iGetRequestToEndpoint() {
        Response response = RestAssured.given().get("/get");
        Assertions.assertThat(response.getStatusCode()).isEqualTo(200);
    }

    @Test
    public void sampleApiTest() {
        iNavigateToHomepage();
        iGetRequestToEndpoint();
    }
}