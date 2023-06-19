package TestelkaLessonTests.PageObjectModel.tests;

import TestelkaLessonTests.PageObjectModel.pages.MainPage;
import TestelkaLessonTests.PageObjectModel.pages.ProductPage;
import TestelkaLessonTests.PageObjectModel.pages.WishlistPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WishListTests extends BaseTests {
    String calculusSlug = "/calculus-made-easy-by-silvanus-p-thompson/";
    @Test
    public void product_added_to_wishlist_should_wishlist_have_one_item() {
        ProductPage productPage = new ProductPage(browser).go(calculusSlug);
        WishlistPage wishlistPage = productPage.addToWishlist().storeHeader.goToWishlist();

        Assertions.assertEquals(1, wishlistPage.getNumberOfProducts(),
                "Number of products in wishlist is not what expected.");
    }

    @Test
    public void no_product_added_to_wishlist_should_wishlist_be_empty() {
        MainPage mainPage = new MainPage(browser);
        WishlistPage wishlistPage = mainPage.go().storeHeader.goToWishlist();

        Assertions.assertEquals(0, wishlistPage.getNumberOfProducts(),
                "Number of products in wishlist is not what expected.");
    }

}
