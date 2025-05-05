package steps.ui;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductsPage;
import utils.AllureReporter;
import utils.CsvDataLoader;
import utils.DriverFactory;
import utils.PerformanceAsserter;

import java.util.List;
import java.util.Map;

public class LoginSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private List<Map<String, String>> testData;

    @Given("The user opens the SauceDemo login page")
    public void open_browser() {
        driver = DriverFactory.initDriver();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);
    }

    @When("^the user logs in using credentials from (.+)$")
    public void login_using_csv(String filename) {
        String filePath = "test-data/" + filename;
        testData = CsvDataLoader.loadTestData(filePath);
    }

    @Then("the login outcomes should match the expected results")
    public void assert_login_outcomes() {
        for (Map<String, String> row : testData) {
            String testCaseId = row.get("test_case_id");
            String username = row.get("username");
            String password = row.get("password");
            String expectedResult = row.get("expected_result");
            String expectedMessage = row.get("expected_message");

            // Attach to Allure report for every row
            AllureReporter.attachText("Test Case Info",
                    "TestCase ID: " + testCaseId + "\n" +
                            "Username: " + username + "\n" +
                            "Password: " + password + "\n" +
                            "Expected Result: " + expectedResult + "\n" +
                            "Expected Message: " + expectedMessage);

            try {
                driver.get("https://www.saucedemo.com/");
                AllureReporter.stepWithScreenshot(driver, "Enter username", () -> loginPage.enterUsername(username));
                AllureReporter.stepWithScreenshot(driver, "Enter password", () -> loginPage.enterPassword(password));
                AllureReporter.stepWithScreenshot(driver, "Click login", () -> loginPage.clickLogin());


                // Performance assertion
                long startTime = System.currentTimeMillis();
                boolean success = loginPage.waitForInventoryPage();
                long duration = System.currentTimeMillis() - startTime;
                PerformanceAsserter.assertUnderMillis("Login", duration, 10000);
                Assertions.assertTrue(success, "Login failed — inventory page not loaded.");

                if ("success".equalsIgnoreCase(expectedResult)) {
                    Assertions.assertTrue(loginPage.isOnProductsPage(),
                            "Expected successful login for user: " + username);
                } else {
                    String actualMessage = loginPage.getErrorMessageIfExists();
                    Assertions.assertTrue(actualMessage.contains(expectedMessage),
                            "Expected error message: '" + expectedMessage + "', but got: '" + actualMessage + "'");
                }
            } catch (AssertionError | Exception e) {
                AllureReporter.captureScreenshot(driver, "Failure - " + username);
                AllureReporter.capturePageSource(driver, "PageSource - " + username);
                throw new RuntimeException("Login test failed for user: " + username, e);
            }
        }
    }
}