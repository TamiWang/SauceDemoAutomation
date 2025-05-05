package utils;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceAsserter {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceAsserter.class);

    private PerformanceAsserter() {}

    public static void assertUnderMillis(String actionName, long durationMillis, long thresholdMillis) {
        String message = actionName + " took " + durationMillis + " ms (threshold: " + thresholdMillis + " ms)";

        // Log to console
        logger.info(message);

        // Attach to Allure
        Allure.addAttachment("Performance - " + actionName, message);

        // Assert
        Assertions.assertTrue(durationMillis <= thresholdMillis, "FAIL: " + message);
    }
}

