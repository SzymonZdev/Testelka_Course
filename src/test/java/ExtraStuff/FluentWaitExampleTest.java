package ExtraStuff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FluentWaitExampleTest extends BaseTest{

    @Test
    public void test() {
        driver.get(baseURL +
                "/product/a-popular-history-of-astronomy-during-the-nineteenth-century-by-agnes-m-clerke/");
        By addToWishlistLinkLocator = By.cssSelector("a.add_to_wishlist");

        driver.findElement(addToWishlistLinkLocator).click();

        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(5))
                .pollingEvery(Duration.ofSeconds(1))
                .ignoring(NoSuchElementException.class);


        WebElement removeFromList = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".delete_item")));
        removeFromList.click();

        Assertions.assertDoesNotThrow(() -> wait.until(ExpectedConditions.presenceOfElementLocated(addToWishlistLinkLocator)));
    }
}
