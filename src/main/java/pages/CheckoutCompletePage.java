package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage {
    private final WebDriver driver;

    private final By completeHeader = By.className("complete-header");

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
    }

    public String getOrderCompleteMessage() {
        return driver.findElement(completeHeader).getText();
    }
}
