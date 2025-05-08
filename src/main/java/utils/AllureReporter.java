package utils;

import io.qameta.allure.Allure;
import org.openqa.selenium.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.Optional;

public final class AllureReporter {
    private static final Logger logger = LoggerFactory.getLogger(AllureReporter.class);

    // Private constructor to prevent object creation
    private AllureReporter() {}

    public static void captureScreenshot(WebDriver driver, String attachmentName) {
        if (driver == null) return;

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment(attachmentName, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            logger.error("Could not capture screenshot: {}", e.getMessage());
        }
    }

    public static void capturePageSource(WebDriver driver, String attachmentName) {
        Optional.ofNullable(driver)
                .map(WebDriver::getPageSource)
                .ifPresent(pageSource ->
                        Allure.addAttachment(attachmentName, "text/html", pageSource)
                );
    }

    public static void attachText(String attachmentName, String content) {
        Allure.addAttachment(attachmentName, "text/plain", content);
    }

    public static void stepWithScreenshot(WebDriver driver, String stepDescription, Runnable stepAction) {
        Allure.step(stepDescription, () -> {
            stepAction.run();
            captureScreenshot(driver, stepDescription);
        });
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
