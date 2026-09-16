package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    @DisplayName("TC-LOGIN-001 - Valid credentials login")
    void validLogin(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("inventory.html"));
    }

}
