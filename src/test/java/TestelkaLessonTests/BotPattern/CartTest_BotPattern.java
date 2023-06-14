package TestelkaLessonTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartTest_BotPattern extends BaseTests{
        private final By addToCartButtonLocator = By.name("add-to-cart");
        private final String addToCartButtonSelector = "[name='add-to-cart']";
        private final String gotToCartFromProductButtonLocator = ".woocommerce-message>.button";
        private final String productsInCartSelector = "tr.cart_item";
        private final String quantityFieldInCartSelector = "input.qty";
        private final By updateCartButtonLocator = By.name("update_cart");
        private final String updateCartButtonSelector = "[name='update_cart']";
        private final String loadingIconSelector = ".blockUI";
        private final String totalPriceInCartSelector = "[data-title='Total']";
        private final String chemicalAnalysisSlug = "the-elements-of-qualitative-chemical-analysis-vol-1-parts-1-and-2-by-stieglitz/";
        private final String calculusSlug = "/product/calculus-made-easy-by-silvanus-p-thompson/";
        private final String historyOfAstronomySlug = "a-popular-history-of-astronomy-during-the-nineteenth-century-by-agnes-m-clerke/";

        @Test
        public void changing_quantity_in_cart_should_change_total_price() {
            bot.go(calculusSlug);
            bot.click(addToCartButtonSelector);
            bot.click(gotToCartFromProductButtonLocator);
            bot.type(quantityFieldInCartSelector, "3");
            bot.click(updateCartButtonSelector);

            bot.waitForElementToDisappear(loadingIconSelector, 5);

            Assertions.assertEquals("39,00 €",
                    bot.getText(totalPriceInCartSelector),
                    "Total price after quantity update is not what was expected.");
        }

        @Test
        public void no_product_added_to_cart_should_cart_be_empty() {
            bot.go("/cart/");

            Assertions.assertEquals(0, bot.getNumberOfElements(".shop_table"), "Products table was found in cart while no product was added.");
        }

        @Test
        public void product_added_to_cart_should_cart_have_one_product() {
            bot.go(calculusSlug);
            bot.click(addToCartButtonSelector);
            bot.click(gotToCartFromProductButtonLocator);
            int numberOfProducts = bot.getNumberOfElements(productsInCartSelector);

            Assertions.assertEquals(1, numberOfProducts, "Expected number of products in cart: 1" + "\nActual: " + numberOfProducts);
        }

        @Test
        public void cart_not_changed_should_update_button_disabled() {
            driver.get(baseUrl + "/product/" + chemicalAnalysisSlug);
            driver.findElement(addToCartButtonLocator).click();
            driver.get(baseUrl + "/cart");
            WebElement updateButton = driver.findElement(updateCartButtonLocator);
            Assertions.assertFalse(updateButton.isEnabled(), "Update button is enabled when it shouldn't, as no changes were made in the cart.");
        }

        @Test
        public void update_quantity_in_cart_should_update_total_price() {
            driver.get(baseUrl + "/product/" + chemicalAnalysisSlug);
            driver.findElement(addToCartButtonLocator).click();
            driver.get(baseUrl + "/cart");
            WebElement quantityField = driver.findElement(By.className("qty"));
            quantityField.clear();
            quantityField.sendKeys("2");
            driver.findElement(updateCartButtonLocator).click();

            wait.until(ExpectedConditions.numberOfElementsToBe(By.className("blockUI"), 0));  // wait until loading icons are not present (their count is equal to 0)


            WebElement totalPrice = driver.findElement(By.className("order-total")).findElement(By.className("amount"));
            Assertions.assertEquals("28,00 €", totalPrice.getText(), "Total is not correct.");
        }

        @Test
        public void add_product_to_cart_should_section_show_correct_subtotal_price() {
            driver.get(baseUrl + "/product/" + historyOfAstronomySlug);
            WebElement addToCartButton = driver.findElement(addToCartButtonLocator);
            addToCartButton.click();
            driver.findElement(By.className("wc-block-mini-cart__button")).click();
            WebElement subtotal_price = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("wc-block-components-totals-item__value")));
            Assertions.assertEquals("12,00 €", subtotal_price.getText(), "The price displayed in the subtotal is not correct.");
        }

        @Test
        @DisplayName("Adding one product to cart should show that product's price in header cart icon.")
        public void add_product_to_cart_should_header_show_product_price() {
            driver.get(baseUrl + "/product/" + historyOfAstronomySlug);
            WebElement addToCartButton = driver.findElement(addToCartButtonLocator);
            addToCartButton.click();
            WebElement miniCartPrice = wait.until(driver -> driver.findElement(By.className("wc-block-mini-cart__amount")));

            Assertions.assertEquals("12,00 €", miniCartPrice.getText(), "The price displayed in the header icon is not correct.");
        }
}
