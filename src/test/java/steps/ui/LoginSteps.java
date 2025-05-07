package steps.ui;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductsPage;
import utils.AllureReporter;
import utils.CsvDataLoader;
import utils.DriverFactory;

import java.time.Duration;
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
        loginPage = new LoginPage(driver);
    }

    @When("^the user logs in using credentials from (.+)$")
    public void login_using_csv(String filename) {
        testData = CsvDataLoader.loadTestData(filename);
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
                loginPage.open();

                AllureReporter.stepWithScreenshot(driver, "Enter username", () -> loginPage.enterUsername(username));
                AllureReporter.stepWithScreenshot(driver, "Enter password", () -> loginPage.enterPassword(password));

                // Record start time before clickLogin() to measure the performance for performance_glitch case
                long start = System.currentTimeMillis();

                AllureReporter.stepWithScreenshot(driver, "Click login", () -> loginPage.clickLogin());

                productsPage = new ProductsPage(driver);

                switch (expectedResult) {
                    case "success":
                        Assertions.assertTrue(productsPage.waitForInventoryPage(Duration.ofSeconds(5)),
                                "Expected successful login for user: " + username);
                        break;
                    case "locked_out":
                        Assertions.assertEquals(expectedMessage, loginPage.getErrorMessage());
                        break;
                    case "performance_glitch":
                        boolean pageLoaded = productsPage.waitForInventoryPage(Duration.ofSeconds(5));
                        long duration = System.currentTimeMillis() - start;

                        AllureReporter.attachText("Performance result",
                                "Username: " + username + "\n" +
                                        "Duration: " + duration + " ms\n" +
                                        "Page Loaded: " + pageLoaded);

                        // Expect it to be slow (> 4000ms)
                        Assertions.assertTrue(pageLoaded, "Page did not load at all.");
                        Assertions.assertTrue(duration > 4000, "Expected slow login, but it completed in " + duration + " ms.");
                        break;
                    default:
                        Assertions.fail("Unhandled expected result: " + expectedResult);
                }
            } catch (AssertionError | Exception e) {
                AllureReporter.captureScreenshot(driver, "Failure - " + username);
                AllureReporter.capturePageSource(driver, "PageSource - " + username);
                throw new RuntimeException("Login test failed for user: " + username, e);
            }
        }
    }
}