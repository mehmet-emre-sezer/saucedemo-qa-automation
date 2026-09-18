package api.tests;

import api.base.ApiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class PingTest extends ApiBaseTest {

    @Test
    @DisplayName("TC-API-010 - Health check")
    void healthCheckReturns201() {
        given()
        .when()
                .get("/ping")
        .then()
                .statusCode(201);
    }
}
