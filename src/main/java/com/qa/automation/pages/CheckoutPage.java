package com.qa.automation.pages;

import com.qa.automation.utils.WaitUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage {
    private WebDriver driver;
    private WaitUtility waitUtility;

    // Locators
    private By firstNameInput = By.id("first-name");
    private By lastNameInput = By.id("last-name");
    private By postalCodeInput = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtility = new WaitUtility(driver);
    }

    public void enterCustomerInformation(String firstName, String lastName, String postalCode) {
        org.openqa.selenium.WebElement fName = waitUtility.waitForElementVisible(firstNameInput);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", fName);
        fName.clear();
        fName.sendKeys(firstName);
        
        org.openqa.selenium.WebElement lName = waitUtility.waitForElementVisible(lastNameInput);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", lName);
        lName.clear();
        lName.sendKeys(lastName);
        
        org.openqa.selenium.WebElement pCode = waitUtility.waitForElementVisible(postalCodeInput);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", pCode);
        pCode.clear();
        pCode.sendKeys(postalCode);
    }

    public CheckoutOverviewPage clickContinue() {
        org.openqa.selenium.WebElement continueBtn = waitUtility.waitForElementClickable(continueButton);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", continueBtn);
        continueBtn.click();
        return new CheckoutOverviewPage(driver);
    }
    
    public String getErrorMessage() {
        return waitUtility.waitForElementVisible(errorMessage).getText();
    }
}
