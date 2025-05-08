package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    private static WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);

    public DriverFactory() {
        initDriver();
    }

    public void initDriver() {
        if (driver == null) {
            String browser = System.getProperty("browser", "chrome").toLowerCase();

            switch (browser) {
                case "firefox":
                    driver = new FirefoxDriver(getFirefoxOptions());
                    break;
                case "edge":
                    driver = new EdgeDriver(getEdgeOptions());
                    break;
                case "chrome":
                default:
                    driver = new ChromeDriver(getChromeOptions());
            }

            driver.manage().window().maximize();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();

        // Add headless mode if specified
        if (Boolean.parseBoolean(System.getProperty("headless", "true"))) {
            options.addArguments("--headless=new");
            logger.info("Running in headless mode");
        }

        if (Boolean.parseBoolean(System.getProperty("chrome.guest.mode", "true"))) {
            options.addArguments("--guest");
            logger.info("Running in guest mode");
        }

        // Add other common Chrome options
        options.addArguments(Arrays.asList(
                "--disable-save-password-bubble",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--window-size=1920,1080",
                "--disable-notifications",
                "--disable-infobars"
        ));

        // Disable password manager popups
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        return options;
    }


    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();

        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("-headless");
        }

        options.addArguments(Arrays.asList(
                "--width=1920",
                "--height=1080"
        ));

        return options;
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();

        if (Boolean.parseBoolean(System.getProperty("headless", "false"))) {
            options.addArguments("--headless=new");
        }

        options.addArguments(Arrays.asList(
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage"
        ));

        return options;
    }

    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}