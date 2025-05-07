package steps.ui;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.AllureReporter;
import utils.CsvDataLoader;
import utils.DriverFactory;

import java.util.List;
import java.util.Map;

public class ProductPurchaseSteps {
    private final WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutInfoPage checkoutInfoPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;
    String selectedProductName;

    public ProductPurchaseSteps(DriverFactory driverFactory) {
        this.driver = driverFactory.getDriver();
    }

    @Given("^the user logs in as (.+)$")
    public void theUserLogsInAsUsername(String username) {
        loginPage = new LoginPage(driver);

        List<Map<String, String>> users = CsvDataLoader.loadTestData("credentials.csv");

        for (Map<String, String> user : users) {
            if (username.equals(user.get("username"))) {
                loginPage.open();

                AllureReporter.stepWithScreenshot(driver, "Enter username", () -> loginPage.enterUsername(username));
                AllureReporter.stepWithScreenshot(driver, "Enter password", () -> loginPage.enterPassword(user.get("password")));
                AllureReporter.stepWithScreenshot(driver, "Click login", () -> loginPage.clickLogin());

                // check if login is successful
                boolean isLoggedIn = loginPage.isLoginSuccessful();
                if (!isLoggedIn) {
                    throw new AssertionError("Login failed for user: " + username);
                }
                return;
            }
        }

        throw new RuntimeException("User not found in CSV: " + username);
    }

    @When("the user adds the first product to the cart")
    public void theUserAddsTheFirstProductToTheCart() {
        productsPage = new ProductsPage(driver);
        selectedProductName = productsPage.getFirstProductName();

        AllureReporter.stepWithScreenshot(driver, "Add first product to cart", () -> productsPage.addFirstProductToCart());
        AllureReporter.stepWithScreenshot(driver, "Click cart icon", () -> productsPage.clickCart());
    }

    @When("^the user proceeds to checkout and fills in their (.+), (.+) and (.+)$")
    public void theUserProceedsToCheckoutAndFillsDetails(String firstName, String lastName, String postalCode) {
        cartPage = new CartPage(driver);
        checkoutInfoPage = new CheckoutInfoPage(driver);

    //    Assertions.assertEquals(selectedProductName, cartPage.getCartItemName(), "Product in cart doesn't match selected product");

        cartPage.clickCheckOutBtn();

        AllureReporter.stepWithScreenshot(driver, "Enter first name", () -> checkoutInfoPage.enterFirstName(firstName));
        AllureReporter.stepWithScreenshot(driver, "Enter last name", () -> checkoutInfoPage.enterLastName(lastName));
        AllureReporter.stepWithScreenshot(driver, "Enter postal code", () -> checkoutInfoPage.enterPostalCode(postalCode));
        AllureReporter.stepWithScreenshot(driver, "Click continue", () -> checkoutInfoPage.clickContinue());
    }

    @And("the user reviews the order and completes the purchase")
    public void theUserReviewsAndCompletesPurchase() {
        checkoutOverviewPage = new CheckoutOverviewPage(driver);
        AllureReporter.stepWithScreenshot(driver, "Click finish to complete order", () -> checkoutOverviewPage.clickFinish());
    }

    @Then("^the order should be confirmed with a success message (.+)$")
    public void theOrderShouldBeConfirmedWithASuccessMessage(String expectedCompleteMsg) {
        checkoutCompletePage = new CheckoutCompletePage(driver);
        AllureReporter.stepWithScreenshot(driver, "Check order confirmation message", () -> {
            Assertions.assertEquals(expectedCompleteMsg,
                    checkoutCompletePage.getOrderCompleteMessage(),
                    "Order confirmation message was not displayed correctly."
            );
        });
    }
}