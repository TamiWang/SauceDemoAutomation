package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.AllureReporter;

import java.time.Duration;

public class LoginPage {
    private final WebDriver driver;

    // Locators
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("login-button");
    private final By errorMessage  = By.cssSelector("h3[data-test='error']");
    private final By inventoryTitle = By.cssSelector(".title");
    private final By closeErrorButton = By.className("error-button");
    private final By inventoryList = By.cssSelector(".inventory_list");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    public boolean waitForInventoryPage() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.urlContains("inventory.html"),
                    ExpectedConditions.visibilityOfElementLocated(inventoryList)
            ));
            return true;
        } catch (TimeoutException e) {
            System.out.println("Login timeout – inventory page not loaded.");
            AllureReporter.captureScreenshot(driver, "LoginTimeout");
            AllureReporter.capturePageSource(driver, "LoginTimeout_PageSource");
            return false;
        }
    }

    public String getErrorMessageIfExists() {
        try {
            WebElement error = driver.findElement(errorMessage);
            return error.isDisplayed() ? error.getText() : "";
        } catch (Exception e) {
            return ""; // no error message displayed
        }
    }

    public boolean isOnProductsPage() {
        try {
            WebElement title = driver.findElement(inventoryTitle);
            return title.isDisplayed() && title.getText().equalsIgnoreCase("Products");
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public boolean isErrorVisible() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    public void closeErrorMessage() {
        driver.findElement(closeErrorButton).click();
    }
}
