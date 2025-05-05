package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductsPage {
    WebDriver driver;

    @FindBy(className = "title")
    WebElement pageHeader;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isAtProductsPage() {
        return driver.getCurrentUrl().contains("inventory.html");
    }

    public boolean isHeaderVisible() {
        return pageHeader.isDisplayed();
    }
}

