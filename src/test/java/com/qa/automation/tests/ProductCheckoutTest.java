package com.qa.automation.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.qa.automation.base.BaseTest;
import com.qa.automation.pages.*;
import com.qa.automation.utils.JsonDataReader;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("E-Commerce Flow")
@Feature("Product Checkout")
public class ProductCheckoutTest extends BaseTest {

    @Test(description = "End-to-End Checkout Validation", groups = {"e2e", "regression"})
    @Story("Complete Shopping Flow")
    @Severity(SeverityLevel.CRITICAL)
    public void testEndToEndCheckout() {
        // Retrieve Data
        JsonNode validUser = JsonDataReader.getTestData("validUser");
        JsonNode checkoutUser = JsonDataReader.getTestData("checkoutUser");
        JsonNode products = JsonDataReader.getTestData("products");
        String productName = products.get(0).get("name").asText();
        String expectedPrice = products.get(0).get("price").asText();

        // Step 1: Login
        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(
                validUser.get("username").asText(),
                validUser.get("password").asText()
        );

        // Step 2: Select Product & Add to Cart
        productsPage.addProductToCart(productName);
        Assert.assertEquals(productsPage.getCartItemCount(), "1", "Cart badge count is incorrect.");

        // Step 3: Go to Cart & Verify Product
        CartPage cartPage = productsPage.goToCart();
        Assert.assertTrue(cartPage.isProductInCart(productName), "Product was not found in the cart.");
        Assert.assertEquals(cartPage.getProductQuantity(productName), "1", "Product quantity in cart is incorrect.");
        Assert.assertEquals(cartPage.getProductPrice(productName), expectedPrice, "Product price in cart is incorrect.");

        // Step 4: Checkout
        CheckoutPage checkoutPage = cartPage.proceedToCheckout();
        checkoutPage.enterCustomerInformation(
                checkoutUser.get("firstName").asText(),
                checkoutUser.get("lastName").asText(),
                checkoutUser.get("postalCode").asText()
        );
        CheckoutOverviewPage overviewPage = checkoutPage.clickContinue();

        // Step 5: Verify Order Summary
        Assert.assertTrue(overviewPage.isOrderSummaryDisplayed(), "Order summary is not displayed.");
        Assert.assertTrue(overviewPage.getSubtotal().contains(expectedPrice.replace("$", "")), "Subtotal is incorrect.");

        // Step 6: Finish Order
        CheckoutCompletePage completePage = overviewPage.clickFinish();
        Assert.assertEquals(completePage.getCompleteHeader(), "Thank you for your order!", "Order completion header is incorrect.");
        Assert.assertTrue(completePage.getCompleteText().contains("Your order has been dispatched"), "Order completion text is missing.");
    }
}
