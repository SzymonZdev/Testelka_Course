package TestelkaLessonTests.PageObjectModel.Tests;

import TestelkaLessonTests.PageObjectModel.Pages.CheckoutPage;
import TestelkaLessonTests.PageObjectModel.Pages.ProductPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CheckoutTests extends BaseTests {
    private final String chemicalAnalysisSlug = "/the-elements-of-qualitative-chemical-analysis-vol-1-parts-1-and-2-by-stieglitz/";

    @Test
    public void successful_purchase_should_show_order_received_message() {
        ProductPage productPage = new ProductPage(driver);
        CheckoutPage checkoutPage = productPage.go(chemicalAnalysisSlug).addToCart().goToCheckout();
        checkoutPage.fillBillingDetails().placeOrder();

        Assertions.assertEquals("Order received",
                checkoutPage.getHeaderText(),
                "\"Order received\" text is not found in the header. Order was probably not successful.");
    }
}
