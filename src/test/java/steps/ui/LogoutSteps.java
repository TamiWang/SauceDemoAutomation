package steps.ui;

import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.junit.jupiter.api.Assertions;
import pages.LoginPage;
import pages.ProductsPage;
import utils.AllureReporter;
import utils.DriverFactory;

public class LogoutSteps {
    private final WebDriver driver = DriverFactory.getDriver();
    private ProductsPage productsPage;
    private LoginPage loginPage;

    @When("the user clicks on the menu button")
    public void theUserClicksOnTheMenuButton() {
        productsPage = new ProductsPage(driver);
        AllureReporter.stepWithScreenshot(driver, "Click Menu Button", () -> productsPage.clickMenuButton());
    }

    @When("the user clicks the logout link")
    public void theUserClicksTheLogoutLink() {
        AllureReporter.stepWithScreenshot(driver, "Click Logout Link", () -> productsPage.clickLogoutLink());
    }

    @Then("the user should be redirected to the login page")
    public void theUserShouldBeRedirectedToTheLoginPage() {
        loginPage = new LoginPage(driver);
        boolean isOnLoginPage = loginPage.isOnLoginPage();
        AllureReporter.captureScreenshot(driver, "Login Page After Logout");

        Assertions.assertTrue(isOnLoginPage, "User was not redirected to the login page after logout.");
    }
}
