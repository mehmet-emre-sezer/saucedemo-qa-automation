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

    @Test
    @DisplayName("TC-LOGIN-002 - Wrong password")
    void wrongPassword(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "wrong_pass");

        String error = loginPage.getErrorMessage();
        Assertions.assertEquals("Epic sadface: Username and password do not match any user in " +
                "this service", error);
    }

}
