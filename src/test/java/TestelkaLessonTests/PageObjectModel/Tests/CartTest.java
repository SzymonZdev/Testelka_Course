package TestelkaLessonTests.PageObjectModel.Tests;

import TestelkaLessonTests.PageObjectModel.Pages.CartPage;
import TestelkaLessonTests.PageObjectModel.Pages.ProductPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartTests  extends BaseTests{
    private final String chemicalAnalysisSlug = "/the-elements-of-qualitative-chemical-analysis-vol-1-parts-1-and-2-by-stieglitz/";
    private final String calculusSlug = "/calculus-made-easy-by-silvanus-p-thompson/";
    private final String historyOfAstronomySlug = "/a-popular-history-of-astronomy-during-the-nineteenth-century-by-agnes-m-clerke/";

    @Test
    public void no_product_added_to_cart_should_cart_be_empty() {
        CartPage cartPage = new CartPage(driver);
        cartPage.go();

        Assertions.assertEquals(0, cartPage.getNumberOfProducts(), "Number of products in cart is not 0.");
    }

    @Test
    public void product_added_to_cart_should_cart_have_one_product() {
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = productPage.go(calculusSlug).addToCart().goToCart();
        int numberOfProducts = cartPage.getNumberOfProducts();

        Assertions.assertEquals(1, numberOfProducts,
                "Expected number of products in cart: 1" +
                        "\nActual: " + numberOfProducts);
    }

    @Test
    public void cart_not_changed_should_update_button_be_disabled() {
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = productPage.go(chemicalAnalysisSlug).addToCart().goToCart();

        Assertions.assertFalse(cartPage.isUpdateCartButtonEnabled(), "Update button is enabled when it shouldn't, as no changes were made in the cart.");
    }

    @Test
    public void update_quantity_in_cart_should_update_total_price() {
        ProductPage productPage = new ProductPage(driver);
        CartPage cartPage = productPage.go(chemicalAnalysisSlug).addToCart().goToCart();
        cartPage.updateSingleProductQuantity(2);

        Assertions.assertEquals("28,00 €", cartPage.getTotalPrice(), "Total is not correct.");
    }

    @Test
    public void add_product_to_cart_should_section_show_correct_subtotal_price() {
        ProductPage productPage = new ProductPage(driver);
        productPage.go(historyOfAstronomySlug).addToCart().displayCartSection();
        String productPrice = productPage.getProductPrice();

        Assertions.assertEquals(productPrice, productPage.getSubtotalPrice(), "The price displayed in the subtotal is not correct.");
    }

    @Test
    @DisplayName("Adding one product to cart should show that product's price in header cart icon.")
    public void add_product_to_cart_should_header_show_product_price() {
        ProductPage productPage = new ProductPage(driver);
        productPage.go(historyOfAstronomySlug).addToCart();
        String productPrice = productPage.getProductPrice();

        Assertions.assertEquals(productPrice, productPage.getMiniCartPrice(), "The price displayed in the header icon is not correct.");
    }
}
