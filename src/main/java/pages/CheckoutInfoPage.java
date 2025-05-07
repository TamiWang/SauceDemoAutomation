package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutInfoPage {
    private final WebDriver driver;

    private final By firstNameValue = By.id("first-name");
    private final By lastNameValue = By.id("last-name");
    private final By postalCodeValue = By.id("postal-code");
    private final By continueButton = By.id("continue");

    public CheckoutInfoPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterFirstName(String firstName) {
        driver.findElement(firstNameValue).sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        driver.findElement(lastNameValue).sendKeys(lastName);
    }

    public void enterPostalCode(String zip) {
        driver.findElement(postalCodeValue).sendKeys(zip);
    }

    public void clickContinue() {
        driver.findElement(continueButton).click();
    }
}
