package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AllureReporter;

import java.time.Duration;

public class ProductsPage {
    private final WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(ProductsPage.class);

    private final By inventoryContainer = By.id("inventory_container");
    private final By inventoryList = By.cssSelector(".inventory_list");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

//    public boolean isDisplayed() {
//        return driver.findElement(inventoryContainer).isDisplayed();
//    }

    public boolean waitForInventoryPage(Duration timeout) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, timeout);
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.urlContains("inventory.html"),
                    ExpectedConditions.visibilityOfElementLocated(inventoryContainer)
            ));
            return true;
        } catch (TimeoutException e) {
            logger.info("Login timeout – inventory page not loaded.");
            AllureReporter.captureScreenshot(driver, "LoginTimeout");
            AllureReporter.capturePageSource(driver, "LoginTimeout_PageSource");
            return false;
        }
    }
}

