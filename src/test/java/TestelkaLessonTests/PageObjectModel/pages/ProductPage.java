package TestelkaLessonTests.PageObjectModel.pages;

import TestelkaLessonTests.PageObjectModel.CartPage;
import TestelkaLessonTests.PageObjectModel.helpers.Browser;
import TestelkaLessonTests.PageObjectModel.pages.BasePage;
import TestelkaLessonTests.PageObjectModel.pages.StoreHeaderComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage extends BasePage {
    private By addToCart = By.cssSelector("[name=add-to-cart]");
    private By goToCart = By.cssSelector(".woocommerce-message>.button");
    private By addToWishlist = By.cssSelector("a.add_to_wishlist");

    public final StoreHeaderComponent storeHeader;

    public ProductPage(Browser browser) {
        super(browser);
        storeHeader = new StoreHeaderComponent(browser);
    }

    public ProductPage go(String productSlug) {
        driver.get(baseURL + "/product/" + productSlug);
        return this;
    }

    public ProductPage addToCart() {
        driver.findElement(addToCart).click();
        return this;
    }

    public CartPage goToCart() {
        driver.findElement(goToCart).click();
        return new CartPage(browser);
    }

    public ProductPage addToWishlist() {
        driver.findElement(addToWishlist).click();
        waitForLoadingIconDisappear();
        return this;
    }
}
