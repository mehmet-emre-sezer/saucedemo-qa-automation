package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;

public class CheckoutTest extends BaseTest {

    @BeforeEach
    public void goToCheckout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addBackpackToCart();
        productsPage.openCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.clickCheckout();
    }

    @Test
    @DisplayName("TC-CHECKOUT-001 - Valid info proceeds to overview")
    void fillInfoProceedsToOverview() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillInformation("Mehmet", "Sezer", "34343");

        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("https://www.saucedemo.com/checkout-step-two.html",currentUrl);
    }

    @Test
    @DisplayName("TC-CHECKOUT-002 - Missing First name Shows Error")
    void missingFirstNameShowsError() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterLastName("Sezer");
        checkoutPage.enterPostalCode("34343");
        checkoutPage.clickContinue();

        String error = checkoutPage.getErrorMessage();
        Assertions.assertEquals("Error: First Name is required", error);
    }


}
