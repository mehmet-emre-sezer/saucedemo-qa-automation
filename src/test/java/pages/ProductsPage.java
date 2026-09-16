package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ProductsPage {

    private final WebDriver driver;

    private final By inventoryItems = By.className("inventory_item");
    private final By sortDropdown = By.className("product_sort_container");
    private final By itemNames = By.className("inventory_item_name");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    public int getProductCount() {
        return driver.findElements(inventoryItems).size();
    }

    public void sortBy(String value) {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByValue(value);
    }

    public String getFirstProductName() {
        return driver.findElements(itemNames).get(0).getText();
    }

}
