package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
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

    // Cart sayfası sürekli yeniden render olduğu için buton bulunup tıklanana kadar
    // bayatlayabiliyor; başarana (ürün silinene) kadar birkaç kez tekrar dener.
    public void removeItem() {
        for (int attempt = 0; attempt < 5; attempt++) {
            try {
                WebElement button = driver.findElement(removeButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                wait.until(ExpectedConditions.stalenessOf(button));
                return;
            } catch (StaleElementReferenceException | TimeoutException e) {
                // yeniden render oldu ya da tıklama işlemedi; tekrar dene
            }
        }
        throw new IllegalStateException("Ürün birkaç denemeye rağmen sepetten kaldırılamadı");
    }

    public void clickContinueShopping() {
        driver.findElement(continueToShoppingButton).click();
    }

    // Cart sayfası React; yeni yüklendiğinde checkout butonunun onClick handler'ı
    // henüz bağlı olmayabiliyor, native tıklama yavaş CI'da navigasyonu tetiklemiyor
    // (removeItem ile aynı ders). JS click + step-one'a geçene kadar retry.
    public void clickCheckout() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(2));
        for (int attempt = 0; attempt < 6; attempt++) {
            try {
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
                shortWait.until(ExpectedConditions.urlContains("checkout-step-one"));
                return;
            } catch (StaleElementReferenceException | TimeoutException e) {
                // handler henüz hazır değil / tıklama navigasyonu tetiklemedi; tekrar dene
            }
        }
        throw new IllegalStateException("Checkout step-one'a birkaç denemeye rağmen geçilemedi");
    }


}
