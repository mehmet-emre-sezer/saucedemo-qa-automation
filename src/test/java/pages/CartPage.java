package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ElementHelper;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final ElementHelper helper;

    private final By cartItems = By.className("cart_item");
    private final By itemName = By.className("inventory_item_name");
    private final By itemPrice = By.className("inventory_item_price");
    private final By removeButton = By.id("remove-sauce-labs-backpack");
    private final By continueToShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");


    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.helper = new ElementHelper(driver);
    }

    public String getItemName() {
        return safeGetText(itemName);
    }

    public String getItemPrice() {
        return safeGetText(itemPrice);
    }

    // Cart sayfası okuma sırasında yeniden render olup elementi bayatlatabiliyor;
    // bayatlarsa yeniden bulup tekrar okuyana kadar bekler.
    private String safeGetText(By locator) {
        return wait.until(d -> {
            try {
                return d.findElement(locator).getText();
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    // Cart React; buton native click ile bayatlayabiliyor/işlemeyebiliyor.
    // JS click + ürün (remove butonu) kaybolana kadar retry.
    public void removeItem() {
        helper.jsClickUntil(removeButton, ExpectedConditions.invisibilityOfElementLocated(removeButton));
    }

    public void clickContinueShopping() {
        driver.findElement(continueToShoppingButton).click();
    }

    // Cart React; yeni yüklendiğinde checkout onClick handler'ı henüz bağlı
    // olmayabiliyor, native tıklama yavaş CI'da navigasyonu tetiklemiyor.
    // JS click + step-one'a geçene kadar retry.
    public void clickCheckout() {
        helper.jsClickUntil(checkoutButton, ExpectedConditions.urlContains("checkout-step-one"));
    }

}
