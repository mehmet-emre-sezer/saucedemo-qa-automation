package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartItems = By.className("cart_item");
    private final By itemName = By.className("inventory_item_name");
    private final By itemPrice = By.className("inventory_item_price");
    private final By removeButton = By.id("remove-sauce-labs-backpack");
    private final By continueToShoppingButton = By.id("continue-shopping");
    private final By checkoutButton = By.id("checkout");


    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getItemName() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemName)).getText();
    }

    public String getItemPrice() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(itemPrice)).getText();
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public void removeItem() {
        driver.findElement(removeButton).click();
    }

    public void clickContinueShopping() {
        driver.findElement(continueToShoppingButton).click();
    }

    public void clickCheckout() {
        driver.findElement(checkoutButton).click();
    }


}
