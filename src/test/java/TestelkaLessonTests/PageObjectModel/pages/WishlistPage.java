package TestelkaLessonTests.PageObjectModel.Pages;

import TestelkaLessonTests.PageObjectModel.helpers.Browser;
import TestelkaLessonTests.PageObjectModel.pages.BasePage;
import org.openqa.selenium.By;

public class WishlistPage  extends BasePage {
    private final By productItems = By.cssSelector(".wishlist-items-wrapper tr td.product-remove");
    public WishlistPage(Browser browser) {
        super(browser);
    }

    public int getNumberOfProducts() {
        return driver.findElements(productItems).size();
    }
}
