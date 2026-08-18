package com.qa.automation.pages;

import com.qa.automation.utils.WaitUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage {
    private WebDriver driver;
    private WaitUtility waitUtility;

    // Locators
    private By completeHeader = By.className("complete-header");
    private By completeText = By.className("complete-text");

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtility = new WaitUtility(driver);
    }

    public String getCompleteHeader() {
        return waitUtility.waitForElementVisible(completeHeader).getText();
    }

    public String getCompleteText() {
        return waitUtility.waitForElementVisible(completeText).getText();
    }
}
