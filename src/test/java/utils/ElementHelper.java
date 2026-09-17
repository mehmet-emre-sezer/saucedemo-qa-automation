package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Wait + retry + stale koruması içeren ortak yardımcı.
 * Page object'ler driver.findElement yerine bunu kullanır; böylece
 * bayatlama (stale) mantığı tek yerde toplanır, kod tekrarı olmaz.
 */
public class ElementHelper {

    private final WebDriverWait wait;

    public ElementHelper(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Güvenli yazma: element hazır olana kadar bekler, bayatlarsa tekrar dener.
    public void type(By locator, String text) {
        wait.until(d -> {
            try {
                WebElement element = d.findElement(locator);
                element.clear();
                element.sendKeys(text);
                return true;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    // Güvenli tıklama.
    public void click(By locator) {
        wait.until(d -> {
            try {
                d.findElement(locator).click();
                return true;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    // Güvenli okuma: elementin yazısını döndürür.
    public String getText(By locator) {
        return wait.until(d -> {
            try {
                return d.findElement(locator).getText();
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }
}
