package com.qa.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class WaitUtility {
    private static final Logger logger = LoggerFactory.getLogger(WaitUtility.class);
    private final WebDriverWait wait;

    public WaitUtility(WebDriver driver) {
        long timeout = Long.parseLong(ConfigManager.getProperty("timeout", "10"));
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
    }

    public WaitUtility(WebDriver driver, long timeoutInSeconds) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
    }

    public WebElement waitForElementVisible(WebElement element) {
        logger.debug("Waiting for element to be visible: {}", element);
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public WebElement waitForElementVisible(By locator) {
        logger.debug("Waiting for element to be visible by locator: {}", locator);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitForElementClickable(WebElement element) {
        logger.debug("Waiting for element to be clickable: {}", element);
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public WebElement waitForElementClickable(By locator) {
        logger.debug("Waiting for element to be clickable by locator: {}", locator);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementPresent(By locator) {
        logger.debug("Waiting for element to be present: {}", locator);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public boolean waitForUrlContains(String text) {
        logger.debug("Waiting for URL to contain: {}", text);
        return wait.until(ExpectedConditions.urlContains(text));
    }
}
