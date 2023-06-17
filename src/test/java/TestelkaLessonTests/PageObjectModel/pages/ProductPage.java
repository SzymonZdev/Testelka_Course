package TestelkaLessonTests.PageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductPage {
    private final WebDriver driver;
    private final By addToCartLocator = By.cssSelector("[name='add-to-cart']");
    private final By goToCartLocator = By.cssSelector(".woocommerce-message>.button");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public ProductPage go(String productSlug) {
        String baseUrl = "http://localhost:8080";
        driver.get(baseUrl + "/product/" + productSlug);
        return this;
    }

    public ProductPage addToCart() {
        driver.findElement(addToCartLocator).click();
        return this;
    }

    public CartPage goToCart() {
        driver.findElement(goToCartLocator).click();
        return new CartPage(driver);
    }
}
