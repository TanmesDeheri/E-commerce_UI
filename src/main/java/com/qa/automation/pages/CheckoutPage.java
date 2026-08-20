package com.qa.automation.pages;

import com.qa.automation.utils.WaitUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
        WebElement fName = waitUtility.scrollAndClick(firstNameInput);
        fName.clear();
        fName.sendKeys(firstName);
        
        WebElement lName = waitUtility.scrollAndClick(lastNameInput);
        lName.clear();
        lName.sendKeys(lastName);
        
        WebElement pCode = waitUtility.scrollAndClick(postalCodeInput);
        pCode.clear();
        pCode.sendKeys(postalCode);
    }

    public CheckoutOverviewPage clickContinue() {
        waitUtility.scrollAndClick(continueButton);
        return new CheckoutOverviewPage(driver);
    }
    
    public String getErrorMessage() {
        return waitUtility.waitForElementVisible(errorMessage).getText();
    }
}
