package steps;

import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import pages.ProductsPage;
import utils.DriverFactory;

import java.util.List;
import java.util.Map;

public class GenericSteps {
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
}
