package com.qa.automation.pages;

import com.qa.automation.utils.WaitUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WaitUtility waitUtility;

    // Locators
    private By cartItems = By.className("cart_item");
    private By checkoutButton = By.id("checkout");
    private By inventoryItemName = By.className("inventory_item_name");
    private By inventoryItemPrice = By.className("inventory_item_price");
    private By cartQuantity = By.className("cart_quantity");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtility = new WaitUtility(driver);
    }

    public boolean isProductInCart(String productName) {
        List<WebElement> items = driver.findElements(inventoryItemName);
        for (WebElement item : items) {
            if (item.getText().equals(productName)) {
                return true;
            }
        }
        return false;
    }
    
    public String getProductPrice(String productName) {
        // XPath to find price based on sibling item name
        String xpath = String.format("//div[text()='%s']/../../..//div[@class='inventory_item_price']", productName);
        return waitUtility.waitForElementVisible(By.xpath(xpath)).getText();
    }
    
    public String getProductQuantity(String productName) {
        // XPath to find qty based on sibling item name
        String xpath = String.format("//div[text()='%s']/../../..//div[@class='cart_quantity']", productName);
        return waitUtility.waitForElementVisible(By.xpath(xpath)).getText();
    }

    public CheckoutPage proceedToCheckout() {
        WebElement checkoutBtn = waitUtility.waitForElementClickable(checkoutButton);
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", checkoutBtn);
        checkoutBtn.click();
        return new CheckoutPage(driver);
    }
}
