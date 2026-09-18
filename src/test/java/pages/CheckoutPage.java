package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementHelper;

public class CheckoutPage {

    private final ElementHelper helper;

    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("[data-test='error']");
    private final By finishButton = By.id("finish");
    private final By totalLabel = By.cssSelector("[data-test='total-label']");
    private final By itemName = By.cssSelector("[data-test='inventory-item-name']");
    private final By completeHeader = By.cssSelector("[data-test='complete-header']");


    public CheckoutPage(WebDriver driver) {
        this.helper = new ElementHelper(driver);
    }

    public void enterFirstName(String firstName) {
        helper.type(firstNameInput, firstName);
    }

    public void enterLastName(String lastName) {
        helper.type(lastNameInput, lastName);
    }

    public void enterPostalCode(String postalCode) {
        helper.type(postalCodeInput, postalCode);
    }

    public void clickContinue() {
        helper.click(continueButton);
    }

    public void clickCancel() {
        helper.click(cancelButton);
    }

    public String getErrorMessage() {
        return helper.getText(errorMessage);
    }

    public void fillInformation(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        clickContinue();
        // Continue sonrası step-two yüklenene kadar bekle (finish butonu step-two'ya özgü).
        // Böylece yavaş CI'da navigasyon tamamlanmadan URL/element okunmaz.
        helper.waitForVisible(finishButton);
    }

    public String getItemName() {
        return helper.getText(itemName);
    }

    public String getTotalText() {
        return helper.getText(totalLabel);
    }

    public void clickFinish() {
        helper.click(finishButton);
    }

    public String getCompleteHeader() {
        return helper.getText(completeHeader);
    }

}
