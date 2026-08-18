package com.qa.automation.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.LoginPage;
import com.qa.automation.pages.ProductsPage;
import com.qa.automation.utils.JsonDataReader;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Epic("Authentication")
@Feature("Login Feature")
public class LoginTest extends BaseTest {

    @Test(description = "Verify that a valid user can successfully log in", groups = {"smoke", "regression"})
    @Story("Valid Login")
    @Severity(SeverityLevel.CRITICAL)
    public void testValidLogin() {
        JsonNode validUser = JsonDataReader.getTestData("validUser");
        
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(
                validUser.get("username").asText(),
                validUser.get("password").asText()
        );

        Assert.assertEquals(productsPage.getPageTitle(), "Products", "Page title does not match expected value");
    }

    @Test(dataProvider = "invalidCredentialsProvider", description = "Verify that invalid credentials display an appropriate error message", groups = {"regression"})
    @Story("Invalid Login")
    @Severity(SeverityLevel.NORMAL)
    public void testInvalidLogin(String username, String password) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(errorMessage.contains("Epic sadface:"), "Error message was not displayed correctly.");
    }

    @DataProvider(name = "invalidCredentialsProvider")
    public Object[][] invalidCredentialsProvider() {
        JsonNode invalidUser = JsonDataReader.getTestData("invalidUser");
        return new Object[][]{
                {invalidUser.get("username").asText(), invalidUser.get("password").asText()},
                {"invalid_user", "invalid_pass"},
                {"standard_user", ""}
        };
    }
}
