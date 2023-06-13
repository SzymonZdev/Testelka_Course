package TestelkaLessonTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ShadowTests extends BaseTests {

    @Test
    public void shadow_DOM_example_chrome_edge_tests() {
        driver.get("https://fakestore.testelka.pl/shadow-dom-w-selenium/");

        WebElement shadowHost = driver.findElement(By.cssSelector("#host"));
        SearchContext shadowRoot = shadowHost.getShadowRoot();
        shadowRoot.findElement(By.cssSelector("#input")).sendKeys("Hello there!");
        shadowRoot.findElement(By.cssSelector("button")).click();

        String displayedText = shadowRoot.findElement(By.cssSelector("#output")).getText();

        Assertions.assertEquals("Wprowadzony tekst: Hello there!", displayedText, "Displayed text is not what was expected.");
    }

    @Test
    public void shadow_DOM_example_firefox_test() {
        driver.get("https://fakestore.testelka.pl/shadow-dom-w-selenium/");

        WebElement shadowHost = driver.findElement(By.cssSelector("#host"));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        List<WebElement> elements = (List<WebElement>) js.executeScript("return arguments[0].shadowRoot.children", shadowHost);

        WebElement shadowRoot = elements.get(0);
        shadowRoot.findElement(By.cssSelector("#input")).sendKeys("Hello there!");
        shadowRoot.findElement(By.cssSelector("button")).click();



        String displayedText = shadowRoot.findElement(By.cssSelector("#output")).getText();

        Assertions.assertEquals("Wprowadzony tekst: Hello there!", displayedText, "Displayed text is not what was expected.");
    }
}
