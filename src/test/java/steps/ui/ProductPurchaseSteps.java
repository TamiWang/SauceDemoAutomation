package steps.ui;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductsPage;
import utils.CsvDataLoader;

import java.util.List;
import java.util.Map;

public class ProductPurchaseSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productsPage;
    private List<Map<String, String>> testData;

    @Given("^the user logs in as (.+)$")
    public void theUserLogsInAsUsername(String username) {
        List<Map<String, String>> users = CsvDataLoader.loadTestData("users.csv");

        for (Map<String, String> user : users) {
            if (username.equals(user.get("username"))) {
                loginPage.open();
                loginPage.login(user.get("username"), user.get("password"));
                return;
            }
        }
        throw new RuntimeException("User not found in CSV: " + userKey);
    }

}