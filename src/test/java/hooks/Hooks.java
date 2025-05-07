package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverFactory;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp() {
        WebDriver driver = DriverFactory.initDriver();
        driver.manage().window().maximize();
        logger.info(">>> Starting scenario - browser initialized");
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
        logger.info(">>> Ending scenario - browser quitting");
    }
}
