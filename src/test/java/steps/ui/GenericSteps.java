package steps.ui;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.AllureReporter;
import utils.CsvDataLoader;
import utils.DriverFactory;

import java.util.List;
import java.util.Map;

public class GenericSteps {
    private final WebDriver driver = DriverFactory.getInstance().getDriver();
    private LoginPage loginPage;

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
}
