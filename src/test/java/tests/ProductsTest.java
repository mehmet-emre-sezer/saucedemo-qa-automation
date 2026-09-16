package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProductsPage;

public class ProductsTest extends BaseTest {

    @BeforeEach
    public void loginFirst() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
    }

    @Test
    @DisplayName("TC-PROD-001 - Products are listed")
    void productsAreListed() {
        ProductsPage productsPage = new ProductsPage(driver);
        int productCount = productsPage.getProductCount();
        Assertions.assertEquals(6, productCount);

    }

    @Test
    @DisplayName("TC-PROD-002 - Sort name A to Z")
    void sortNameAToZ() {
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortBy("az");
        String firstProductName = productsPage.getFirstProductName();
        Assertions.assertEquals("Sauce Labs Backpack", firstProductName);
    }
}
