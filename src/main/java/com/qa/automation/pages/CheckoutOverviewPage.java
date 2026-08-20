package com.qa.automation.pages;

import com.qa.automation.utils.WaitUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutOverviewPage {
    private WebDriver driver;
    private WaitUtility waitUtility;

    // Locators
    private By finishButton = By.id("finish");
    private By summarySubtotalLabel = By.className("summary_subtotal_label");
    private By summaryTotalLabel = By.className("summary_total_label");
    private By cartList = By.className("cart_item");

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtility = new WaitUtility(driver);
    }

    public boolean isOrderSummaryDisplayed() {
        return waitUtility.waitForElementVisible(cartList).isDisplayed();
    }

    public String getSubtotal() {
        return waitUtility.waitForElementVisible(summarySubtotalLabel).getText();
    }

    public String getTotal() {
        return waitUtility.waitForElementVisible(summaryTotalLabel).getText();
    }

    public CheckoutCompletePage clickFinish() {
        org.openqa.selenium.WebElement finishBtn = waitUtility.waitForElementClickable(finishButton);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", finishBtn);
        finishBtn.click();
        return new CheckoutCompletePage(driver);
    }
}
