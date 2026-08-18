package com.qa.automation.utils;

import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtility {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtility.class);
    private static final String SCREENSHOT_DIR = "reports/screenshots/";

    static {
        File directory = new File(SCREENSHOT_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static String captureScreenshot(WebDriver driver, String testName) {
        if (driver == null) {
            logger.warn("Driver is null. Cannot capture screenshot.");
            return null;
        }

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        Path targetPath = Paths.get(SCREENSHOT_DIR, fileName);

        try {
            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile.toPath(), targetPath);
            logger.info("Screenshot saved at: {}", targetPath.toAbsolutePath());
            
            // Attach to Allure
            attachScreenshotToAllure(driver);
            
            return targetPath.toAbsolutePath().toString();
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    @Attachment(value = "Page Screenshot on Failure", type = "image/png")
    public static byte[] attachScreenshotToAllure(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
