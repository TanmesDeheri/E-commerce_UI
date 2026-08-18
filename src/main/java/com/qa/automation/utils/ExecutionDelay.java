package com.qa.automation.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutionDelay implements WebDriverListener {
    private static final Logger logger = LoggerFactory.getLogger(ExecutionDelay.class);
    private long executionSpeed;

    public ExecutionDelay() {
        try {
            String speedStr = ConfigManager.getProperty("execution.speed", "0");
            this.executionSpeed = Long.parseLong(speedStr);
            if (this.executionSpeed > 0) {
                logger.info("Execution delay set to {} ms", this.executionSpeed);
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid execution.speed value. Defaulting to 0.");
            this.executionSpeed = 0;
        }
    }

    private void applyDelay() {
        if (executionSpeed > 0) {
            try {
                Thread.sleep(executionSpeed);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Execution delay interrupted", e);
            }
        }
    }

    @Override
    public void afterClick(WebElement element) {
        applyDelay();
    }

    @Override
    public void afterSendKeys(WebElement element, CharSequence... keysToSend) {
        applyDelay();
    }

    @Override
    public void afterGet(WebDriver driver, String url) {
        applyDelay();
    }

    @Override
    public void afterTo(WebDriver.Navigation navigation, String url) {
        applyDelay();
    }

    @Override
    public void afterBack(WebDriver.Navigation navigation) {
        applyDelay();
    }

    @Override
    public void afterForward(WebDriver.Navigation navigation) {
        applyDelay();
    }

    @Override
    public void afterRefresh(WebDriver.Navigation navigation) {
        applyDelay();
    }
}
