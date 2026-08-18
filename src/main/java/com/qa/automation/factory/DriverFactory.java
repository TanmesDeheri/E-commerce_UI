package com.qa.automation.factory;

import com.qa.automation.utils.ConfigManager;
import com.qa.automation.utils.ExecutionDelay;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverFactory {
    private static final Logger logger = LoggerFactory.getLogger(DriverFactory.class);
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static WebDriver initDriver() {
        String browser = ConfigManager.getProperty("browser", "chrome").toLowerCase();
        boolean isHeadless = Boolean.parseBoolean(ConfigManager.getProperty("headless", "false"));
        WebDriver driver;

        logger.info("Initializing browser: {}", browser);

        switch (browser) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (isHeadless) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                    chromeOptions.addArguments("--window-size=1920,1080");
                    chromeOptions.addArguments("--disable-gpu"); // Optional but good for CI
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (isHeadless) {
                    firefoxOptions.addArguments("-headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (isHeadless) {
                    edgeOptions.addArguments("--headless=new");
                }
                driver = new EdgeDriver(edgeOptions);
                break;
            default:
                logger.warn("Browser '{}' not supported. Defaulting to Chrome.", browser);
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        driver.manage().window().maximize();
        
        // Apply ExecutionDelay Listener
        ExecutionDelay executionDelay = new ExecutionDelay();
        WebDriver decoratedDriver = new org.openqa.selenium.support.events.EventFiringDecorator<>(executionDelay).decorate(driver);
        
        driverThreadLocal.set(decoratedDriver);
        logger.info("{} started successfully.", browser);
        
        return getDriver();
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            driverThreadLocal.remove();
            logger.info("Browser closed successfully.");
        }
    }
}
