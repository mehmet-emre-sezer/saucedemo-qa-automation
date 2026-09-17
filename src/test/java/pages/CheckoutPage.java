package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ElementHelper;

public class CheckoutPage {

    private final WebDriver driver;
    private final ElementHelper helper;

    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("[data-test='error']");


    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.helper = new ElementHelper(driver);
    }

    public void enterFirstName(String firstName) {
        helper.type(firstNameInput,firstName);
    }

    public void enterLastName(String lastName) {
        helper.type(lastNameInput,lastName);
    }

    public void enterPostalCode(String postalCode) {
        helper.type(postalCodeInput,postalCode);
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
    }
}
