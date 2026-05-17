package org.example.unit;

import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.WithTagValuesOf;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.example.steps.ApiSteps;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
@Tag("unit")
@WithTagValuesOf({"unit", "user-ops"})
public class SampleUserOpsTest {

    @Steps
    ApiSteps apiSteps;

    @Test
    public void sampleApiTest3() throws Exception {
        apiSteps.getUserStep();
    }

    @Test
    public void sampleApiTest4() throws Exception {
        apiSteps.createUserStep();
    }

    @Test
    public void sampleApiTest6() throws Exception {
        apiSteps.deleteUserStep();
    }
}
