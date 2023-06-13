package TestelkaLessonTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductTests  extends BaseTests{
    @Test
    public void new_product_quantity_typed_in_should_product_quantity_changed() {
        driver.get(baseUrl + "/product/a-popular-history-of-astronomy-during-the-nineteenth-century-by-agnes-m-clerke/");
        WebElement productQuantity = driver.findElement(By.className("qty"));
        productQuantity.clear();
        productQuantity.sendKeys("3");

        Assertions.assertEquals("3", productQuantity.getDomProperty("value"), "Product quantity not changed.");
    }
}
