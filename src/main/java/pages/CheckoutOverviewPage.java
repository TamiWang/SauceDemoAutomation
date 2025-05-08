package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutOverviewPage {
    private final WebDriver driver;

    private final By finishButton = By.id("finish");
    private final By productName = By.cssSelector("[data-test='inventory-item-name']");
    private final By productPrice = By.cssSelector("[data-test='inventory-item-price']");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getProductName() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement productNameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productName));
        return productNameElement.getText();
    }

    public String getProductPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        WebElement productPriceElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
        return productPriceElement.getText();
    }

    public void clickFinish() {
        driver.findElement(finishButton).click();
    }
}
