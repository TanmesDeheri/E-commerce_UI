package com.qa.automation.pages;

import com.qa.automation.utils.WaitUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
    private WebDriver driver;
    private WaitUtility waitUtility;

    // Locators
    private By usernameInput = By.id("user-name");
    private By passwordInput = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtility = new WaitUtility(driver);
    }

    public ProductsPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new ProductsPage(driver);
    }

    public void enterUsername(String username) {
        waitUtility.waitForElementVisible(usernameInput).sendKeys(username);
    }

    public void enterPassword(String password) {
        waitUtility.waitForElementVisible(passwordInput).sendKeys(password);
    }

    public void clickLogin() {
        waitUtility.waitForElementClickable(loginButton).click();
    }

    public String getErrorMessage() {
        return waitUtility.waitForElementVisible(errorMessage).getText();
    }
}
