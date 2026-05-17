package org.example;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.steps.ApiSteps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class SampleTest {

    @Steps
    ApiSteps apiSteps;

    @Test
    public void sampleApiTest() {
        apiSteps.iNavigateToHomepage();
        apiSteps.iGetRequestToEndpoint();
    }

    @Test
    public void sampleApiTest2() {
        apiSteps.iNavigateToHomepage();
        apiSteps.iGetRequestToEndpoint();
    }

    @Test
    public void sampleApiTest3() {
        apiSteps.getUserStep();
    }

    @Test
    public void sampleApiTest4() {
        apiSteps.createUserStep();
    }

    @Test
    public void sampleApiTest5() {
        apiSteps.iGetRequestToEndpoint();
    }

    @Test
    public void sampleApiTest6() {
        apiSteps.deleteUserStep();
    }
}
