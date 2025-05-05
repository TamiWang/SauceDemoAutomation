package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Optional;

public final class AllureReporter {
    private static final Logger logger = LoggerFactory.getLogger(AllureReporter.class);

    // Private constructor to prevent object creation
    private AllureReporter() {}

    public static void captureScreenshot(WebDriver driver, String attachmentName) {
        getScreenshot(driver).ifPresent(screenshot ->
                Allure.addAttachment(attachmentName, "image/png", "png", Arrays.toString(screenshot))
        );
    }

    public static void capturePageSource(WebDriver driver, String attachmentName) {
        Optional.ofNullable(driver)
                .map(WebDriver::getPageSource)
                .ifPresent(pageSource ->
                        Allure.addAttachment(attachmentName, "text/html", pageSource)
                );
    }

    private static Optional<byte[]> getScreenshot(WebDriver driver) {
        try {
            return Optional.ofNullable(driver)
                    .filter(TakesScreenshot.class::isInstance)
                    .map(TakesScreenshot.class::cast)
                    .map(ts -> ts.getScreenshotAs(OutputType.BYTES));
        } catch (WebDriverException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }
}
