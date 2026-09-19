package api.tests;

import api.base.ApiBaseTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

public class BookingTest extends ApiBaseTest {

    @Test
    @DisplayName("TC-API-001 - Get booking by ID")
    void getBookingById() {
        // Precondition: restful-booker paylaşımlı/uçucu; sabit bir id'ye (ör. 1)
        // güvenemeyiz. Testin kendi verisini yaratıp o id ile okuyoruz.
        int id = createBooking();

        given()
                .when()
                .get("/booking/" + id)
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

    @Test
    @DisplayName("TC-API-006 - Create booking")
    void createBookingReturnsIdAndData() {
        given()
                .contentType(ContentType.JSON)
                .body(bookingBody())
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .body("bookingid", greaterThan(0))
                .body("booking.firstname", equalTo("Jim"))
                .body("booking.lastname", equalTo("Brown"))
                .body("booking.totalprice", equalTo(111))
                .body("booking.depositpaid", equalTo(true))
                .body("booking.bookingdates.checkin", equalTo("2018-01-01"))
                .body("booking.bookingdates.checkout", equalTo("2019-01-01"))
                .body("booking.additionalneeds", equalTo("breakfast oder frühstück"));
    }

    // --- Ortak yardımcılar (DRY) ---

    // Test verisi tek kaynak: hem create testi hem createBooking() bunu kullanır.
    private Map<String, Object> bookingBody() {
        return Map.of(
                "firstname", "Jim",
                "lastname", "Brown",
                "totalprice", 111,
                "depositpaid", true,
                "bookingdates", Map.of(
                        "checkin", "2018-01-01",
                        "checkout", "2019-01-01"),
                "additionalneeds", "breakfast oder frühstück"
        );
    }

    // Bir booking oluşturur ve oluşan id'yi döndürür (precondition olarak kullanılır).
    private int createBooking() {
        return given()
                .contentType(ContentType.JSON)
                .body(bookingBody())
                .when()
                .post("/booking")
                .then()
                .statusCode(200)
                .extract()
                .path("bookingid");
    }
}
