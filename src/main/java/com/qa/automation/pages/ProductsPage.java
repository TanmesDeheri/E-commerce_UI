package com.qa.automation.pages;

import com.qa.automation.utils.WaitUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductsPage {
    private WebDriver driver;
    private WaitUtility waitUtility;

    // Locators
    private By pageTitle = By.className("title");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartIcon = By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtility = new WaitUtility(driver);
    }

    public String getPageTitle() {
        return waitUtility.waitForElementVisible(pageTitle).getText();
    }

    public void addProductToCart(String productName) {
        // dynamic locator based on product name
        // e.g., Sauce Labs Backpack -> add-to-cart-sauce-labs-backpack
        String formattedName = productName.toLowerCase().replace(" ", "-");
        By addToCartBtn = By.id("add-to-cart-" + formattedName);
        waitUtility.waitForElementClickable(addToCartBtn).click();
    }

    public String getCartItemCount() {
        return waitUtility.waitForElementVisible(cartBadge).getText();
    }

    public CartPage goToCart() {
        waitUtility.waitForElementClickable(cartIcon).click();
        return new CartPage(driver);
    }
}
