package tests;

import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

public class CartTest extends BaseTest {

    @BeforeEach
    public void loginFirst(){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
    }

    @Test
    @DisplayName("TC-CART-001 - Added product appears in cart")
    void addedProductAppearsInCart() {

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addBackpackToCart();
        productsPage.openCart();

        CartPage cartPage = new CartPage(driver);
        Assertions.assertEquals("Sauce Labs Backpack", cartPage.getItemName());
        Assertions.assertEquals("$29.99", cartPage.getItemPrice());
    }

    @Test
    @DisplayName("TC-CART-002 - Removed Product disappears in Cart")
    void removedProductDisappearsInCart() {

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addBackpackToCart();
        productsPage.openCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.removeItem();
        Assertions.assertEquals(0, cartPage.getCartItemCount());
    }
}
