package TestelkaLessonTests.PageObjectModel.Tests;

import TestelkaLessonTests.PageObjectModel.Pages.ProductPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductTests  extends BaseTests{
    private final String historyOfAstronomySlug = "/a-popular-history-of-astronomy-during-the-nineteenth-century-by-agnes-m-clerke/";
    @Test
    public void new_product_quantity_typed_in_should_product_quantity_changed() {
        ProductPage productPage = new ProductPage(driver);
        productPage.go(historyOfAstronomySlug).changeQuantity(3);

        Assertions.assertEquals("3", productPage.getQuantity(), "Product quantity not changed.");
    }
}
