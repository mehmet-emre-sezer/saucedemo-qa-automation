package api.tests;

import api.base.ApiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BookingTest extends ApiBaseTest {

    @Test
    @DisplayName("TC-API-001 - Get booking by ID")
    void getBookingById() {
        given()
                .when()
                .get("/booking/1")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("api/schemas/booking-schema.json"));
    }

    @Test
    @DisplayName("TC-API-002 - Get booking by non existent ID")
    void getBookingByNonExistentId() {
        given()
                .when()
                .get("/booking/99999999")
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("TC-API-003 - Get all bookings")
    void getAllBookings() {
        given()
                .when()
                .get("/booking")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("api/schemas/bookings-list-schema.json"));
    }
}
