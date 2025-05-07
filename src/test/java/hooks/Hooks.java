package hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DriverFactory;

public class Hooks {
    private final DriverFactory driverFactory;
    private static final Logger logger = LoggerFactory.getLogger(Hooks.class);

    public Hooks(DriverFactory driverFactory) {
        this.driverFactory = driverFactory;
    }

    @Before
    public void setUp() {
        logger.info(">>> Starting scenario - browser initialized");
    }

    @After
    public void tearDown() {
        driverFactory.quitDriver();
        logger.info(">>> Ending scenario - browser quitting");
    }
}
