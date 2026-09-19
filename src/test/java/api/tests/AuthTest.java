package api.tests;

import api.base.ApiBaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthTest extends ApiBaseTest {

    @Test
    @DisplayName("TC-API-004 - Get token with valid credentials")
    void getTokenWithValidCredentials() {
        Map<String, String> creds = Map.of(
                "username", "admin",
                "password", "password123"
        );

        given()
                .contentType(ContentType.JSON)
                .body(creds)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .body("token", not(emptyString()))
                .body("token.length()", greaterThan(10));
    }

    @Test
    @DisplayName("TC-API-005 - No token with invalid credentials")
    void noTokenWithInvalidCredentials() {
        Map<String, String> creds = Map.of(
                "username", "admin",
                "password", "wrong"      // yanlış şifre
        );

        given()
                .contentType(ContentType.JSON)
                .body(creds)
                .when()
                .post("/auth")
                .then()
                .statusCode(200)
                .body("reason", equalTo("Bad credentials"))
                .body("token", nullValue());
    }
}
