package TestelkaLessonTests.PageObjectModel;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {
    private final WebDriver driver;
    private final By productItem = By.cssSelector("tr.cart_item"); // Get number of products (rows)
    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void go() {
        String baseUrl = "http://localhost:8080";
        driver.get(baseUrl + "/cart/");
    }

    public int getNumberOfProducts() {
        return driver.findElements(productItem).size();
    }
}
