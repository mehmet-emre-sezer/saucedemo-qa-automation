package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {

    @Test
    @DisplayName("TC-LOGIN-001 - Valid credentials login")
    void validLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertTrue(currentUrl.contains("inventory.html"));
    }

    @Test
    @DisplayName("TC-LOGIN-002 - Wrong password")
    void wrongPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "wrong_pass");

        String error = loginPage.getErrorMessage();
        Assertions.assertEquals("Epic sadface: Username and password do not match any user in " +
                "this service", error);
    }

    @Test
    @DisplayName("TC-LOGIN-003 - Wrong Username")
    void wrongUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user", "secret_sauce");

        String error = loginPage.getErrorMessage();
        Assertions.assertEquals("Epic sadface: Username and password do not match any user in " +
                "this service", error);
    }

    @Test
    @DisplayName("TC-LOGIN-004 - Empty Username")
    void emptyUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "secret_sauce");

        String error = loginPage.getErrorMessage();
        Assertions.assertEquals("Epic sadface: Username is required", error);
    }

    @Test
    @DisplayName("TC-LOGIN-005 - Empty Password")
    void emptyPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "");

        String error = loginPage.getErrorMessage();
        Assertions.assertEquals("Epic sadface: Password is required", error);
    }

    @Test
    @DisplayName("TC-LOGIN-006 - Locked User")
    void lockedUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");

        String error = loginPage.getErrorMessage();
        Assertions.assertEquals("Epic sadface: Sorry, this user has been locked out.", error);
    }

}
