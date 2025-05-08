package steps.ui;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import pages.*;
import utils.AllureReporter;
import utils.DriverFactory;

public class ProductPurchaseSteps {
    private final WebDriver driver = DriverFactory.getDriver();
    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutInfoPage checkoutInfoPage;
    private CheckoutOverviewPage checkoutOverviewPage;
    private CheckoutCompletePage checkoutCompletePage;
    String selectedProductName;
    String selectedProductPrice;

    @When("the user adds the first product to the cart")
    public void theUserAddsTheFirstProductToTheCart() {
        productsPage = new ProductsPage(driver);
        selectedProductName = productsPage.getFirstProductName();
        selectedProductPrice = productsPage.getFirstProductPrice();

        AllureReporter.stepWithScreenshot(driver, "Add first product to cart", () -> productsPage.addFirstProductToCart());
        AllureReporter.stepWithScreenshot(driver, "Click cart icon", () -> productsPage.clickCart());
    }

    @When("^the user proceeds to checkout and fills in their (.+), (.+) and (.+)$")
    public void theUserProceedsToCheckoutAndFillsDetails(String firstName, String lastName, String postalCode) {
        cartPage = new CartPage(driver);
        checkoutInfoPage = new CheckoutInfoPage(driver);

        cartPage.clickCheckOutBtn();
        AllureReporter.stepWithScreenshot(driver, "Enter first name", () -> checkoutInfoPage.enterFirstName(firstName));
        AllureReporter.stepWithScreenshot(driver, "Enter last name", () -> checkoutInfoPage.enterLastName(lastName));
        AllureReporter.stepWithScreenshot(driver, "Enter postal code", () -> checkoutInfoPage.enterPostalCode(postalCode));
        AllureReporter.stepWithScreenshot(driver, "Click continue", () -> checkoutInfoPage.clickContinue());
    }

    @And("the user reviews the order and completes the purchase")
    public void theUserReviewsAndCompletesPurchase() {
        checkoutOverviewPage = new CheckoutOverviewPage(driver);

        Assertions.assertEquals(selectedProductName, checkoutOverviewPage.getProductName(),
                "Product name in cart doesn't match selected product name.");
        Assertions.assertEquals(selectedProductPrice, checkoutOverviewPage.getProductPrice(),
                "Product price in cart doesn't match selected product price.");

        AllureReporter.stepWithScreenshot(driver, "Click finish to complete order", () -> checkoutOverviewPage.clickFinish());
    }

    @Then("^the order should be confirmed with a success message (.+)$")
    public void theOrderShouldBeConfirmedWithASuccessMessage(String expectedCompleteMsg) {
        checkoutCompletePage = new CheckoutCompletePage(driver);

        Assertions.assertEquals(expectedCompleteMsg, checkoutCompletePage.getOrderCompleteMessage(),
                    "Order confirmation message was not displayed correctly.");
    }
}