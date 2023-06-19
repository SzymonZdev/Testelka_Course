package TestelkaLessonTests.PageObjectModel.pages;

import TestelkaLessonTests.PageObjectModel.Pages.WishlistPage;
import TestelkaLessonTests.PageObjectModel.helpers.Browser;
import org.openqa.selenium.By;

public class StoreHeaderComponent extends BasePage {
    private final By goToWishlistFromHeader = By.cssSelector("#menu-item-88 a");
    protected StoreHeaderComponent(Browser browser) {
        super(browser);
    }

    public WishlistPage goToWishlist() {
        driver.findElement(goToWishlistFromHeader).click();
        return new WishlistPage(browser);
    }
}
