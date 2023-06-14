package TestelkaLessonTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ActionBot {
    private final WebDriver driver;
    private final String baseUrl;
    public ActionBot(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl;
    }

    public void go(String slug) {
        driver.get(baseUrl + slug);
    }

    public int getNumberOfElements(String elementsSelectorCss) {
        return driver.findElements(By.cssSelector(elementsSelectorCss)).size();
    }

    public void click(String elementCssSelector) {
        driver.findElement(By.cssSelector(elementCssSelector)).click();
    }

    public void type(String fieldCssSelector, String text) {
        WebElement field = driver.findElement(By.cssSelector(fieldCssSelector));
        field.clear();
        field.sendKeys(text);
    }

    public void waitForElementToDisappear(String elementCssSelector, int timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeInSeconds));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.cssSelector(elementCssSelector), 0));
    }

    public String getText(String elementCssSelector) {
        return driver.findElement(By.cssSelector(elementCssSelector)).getText();
    }
}
