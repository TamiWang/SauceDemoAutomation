package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogoutPage {

    private final WebDriver driver;

    // Locators
    private final By menuButton = By.id("react-burger-menu-btn");
    private final By logoutLink = By.id("logout_sidebar_link");

    public LogoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void logout() {
        try {
            driver.findElement(menuButton).click();
            Thread.sleep(500); // wait for menu animation (avoid if using explicit wait)
            driver.findElement(logoutLink).click();
        } catch (Exception e) {
            System.out.println("Logout failed: " + e.getMessage());
        }
    }
}
