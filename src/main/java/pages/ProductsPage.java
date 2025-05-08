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
    private final By firstAddToCartButton = By.cssSelector(".inventory_list .inventory_item:first-of-type button");
    private final By firstProductName = By.cssSelector(".inventory_list .inventory_item:first-of-type .inventory_item_name");
    private final By firstProductPrice = By.cssSelector(".inventory_list .inventory_item:first-of-type .inventory_item_price");
    private final By cartIcon = By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

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

    public void addFirstProductToCart() {
        driver.findElement(firstAddToCartButton).click();
    }

    public String getFirstProductName() {
        return driver.findElement(firstProductName).getText();
    }

    public String getFirstProductPrice() {
        return driver.findElement(firstProductPrice).getText();
    }

    public void clickCart() {
        driver.findElement(cartIcon).click();
    }

    public void clickMenuButton() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
    }

    public void clickLogoutLink() {
        driver.findElement(By.id("logout_sidebar_link")).click();
    }
}

