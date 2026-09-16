package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    @Test
    @DisplayName("TC-PROD-003 - Sort name Z to A")
    void sortNameZToA() {
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortBy("za");
        String firstProductName = productsPage.getFirstProductName();
        Assertions.assertEquals("Test.allTheThings() T-Shirt (Red)", firstProductName);
    }

    @Test
    @DisplayName("TC-PROD-004 - Sort price Low to High")
    void sortPriceLowToHigh() {
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortBy("lohi");

        List<Double> actualPrices = productsPage.getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices);

        Assertions.assertEquals(expectedPrices, actualPrices);
    }

    @Test
    @DisplayName("TC-PROD-005 - Sort price High to Low")
    void sortPriceHighToLow() {
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.sortBy("hilo");

        List<Double> actualPrices = productsPage.getProductPrices();
        List<Double> expectedPrices = new ArrayList<>(actualPrices);
        Collections.sort(expectedPrices, Collections.reverseOrder());

        Assertions.assertEquals(expectedPrices, actualPrices);
    }

    @Test
    @DisplayName("TC-PROD-006 - Add product to Cart")
    void addProductToCart() {
        ProductsPage productsPage = new ProductsPage(driver);

        productsPage.addBackpackToCart();
        Assertions.assertEquals("1", productsPage.getCartBadgeCount());
    }

    @Test
    @DisplayName("TC-PROD-007 - Remove product from Cart")
    void removeProductFromCart() {
        ProductsPage productsPage = new ProductsPage(driver);

        productsPage.addBackpackToCart();
        productsPage.removeBackpackFromCart();
        Assertions.assertFalse(productsPage.isCartBadgeVisible());
    }

}
