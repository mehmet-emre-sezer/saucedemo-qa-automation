package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Wait + retry + stale koruması içeren ortak yardımcı.
 * Page object'ler driver.findElement yerine bunu kullanır; böylece
 * bayatlama (stale) mantığı tek yerde toplanır, kod tekrarı olmaz.
 *
 * Not: element yalnızca "var" olması yetmez; görünür ve etkileşilebilir
 * (isDisplayed/isEnabled) olana kadar beklenir. type() ayrıca yazdığı
 * değerin gerçekten yerleştiğini doğrular (yavaş CI'da sendKeys'in
 * boşa gitmesini engeller).
 */
public class ElementHelper {

    private final WebDriverWait wait;

    public ElementHelper(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Güvenli yazma: element hazır olana kadar bekler, yazar ve değeri doğrular.
    // SauceDemo React uygulaması: BOŞ bir input'a clear() çağırmak yavaş CI'da
    // sonraki sendKeys'i bozuyor (clear -> onChange -> re-render -> yazı kayboluyor).
    // Bu yüzden yalnızca alan doluysa temizliyoruz (LoginPage gibi düz sendKeys).
    public void type(By locator, String text) {
        wait.until(d -> {
            try {
                WebElement element = d.findElement(locator);
                if (!element.isDisplayed() || !element.isEnabled()) {
                    return null;
                }
                String current = element.getAttribute("value");
                if (current != null && !current.isEmpty()) {
                    element.clear();
                }
                element.sendKeys(text);
                // sendKeys gerçekten yerleşti mi? Yerleşmediyse tekrar dene.
                return text.equals(element.getAttribute("value")) ? Boolean.TRUE : null;
            } catch (StaleElementReferenceException | ElementNotInteractableException e) {
                return null;
            }
        });
    }

    // Güvenli tıklama: element görünür ve etkileşilebilir olana kadar bekler.
    public void click(By locator) {
        wait.until(d -> {
            try {
                WebElement element = d.findElement(locator);
                if (!element.isDisplayed() || !element.isEnabled()) {
                    return null;
                }
                element.click();
                return Boolean.TRUE;
            } catch (StaleElementReferenceException | ElementNotInteractableException e) {
                return null;
            }
        });
    }

    // Güvenli okuma: element görünür olana kadar bekler, yazısını döndürür.
    public String getText(By locator) {
        return wait.until(d -> {
            try {
                WebElement element = d.findElement(locator);
                return element.isDisplayed() ? element.getText() : null;
            } catch (StaleElementReferenceException e) {
                return null;
            }
        });
    }

    // Bir elementin görünür olmasını bekler (navigasyon/sayfa geçişi doğrulaması için).
    public void waitForVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
