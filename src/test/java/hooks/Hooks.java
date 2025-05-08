package hooks;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.AllureReporter;
import utils.DriverFactory;

public class Hooks {
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    @Before
    public void setUp(Scenario scenario) {
        DriverFactory.initDriver();
        logger.info(">>> Starting scenario : {} - browser initialized", scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        DriverFactory.quitDriver();
        logger.info(">>> Ending scenario: {} - Status: {}", scenario.getName(), scenario.getStatus());
    }
}
