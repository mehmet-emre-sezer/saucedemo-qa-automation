package api.base;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import java.util.Locale;

/**
 * Tüm API testlerinin ortak temeli.
 * baseURI'yi tek yerde ayarlar; test class'ları bunu extends eder,
 * böylece her testte tam adresi tekrar yazmaya gerek kalmaz (DRY).
 */
public class ApiBaseTest {

    @BeforeAll
    static void setup() {
        Locale.setDefault(Locale.ENGLISH);
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }
}
