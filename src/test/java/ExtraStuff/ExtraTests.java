package ExtraStuff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class ExtraTests extends BaseTest {

    @Test
    public void browserSizeAndPositionTest() {
        System.out.println(driver.manage().window().getSize());
        driver.manage().window().setSize(new Dimension(1024, 768));
        System.out.println(driver.manage().window().getSize());
        System.out.println(driver.manage().window().getPosition().getX());
        System.out.println(driver.manage().window().getPosition().getY());
        driver.manage().window().setPosition(new Point(0, 0));
        driver.manage().window().maximize();
        driver.manage().window().minimize();
        driver.manage().window().fullscreen();
    }

    @Test
    public void browserNavigationTest() throws MalformedURLException {
        driver.navigate().to(new URL(baseURL));
        driver.findElement(By.cssSelector("#menu-item-88")).click();

        driver.navigate().back();
        driver.navigate().refresh();
        driver.navigate().forward();

        System.out.println(driver.getPageSource());
    }

    @Test
    public void fileUpload() {
        driver.get(baseURL + "/my-account/");
        driver.findElement(By.cssSelector("#username")).sendKeys("admin");
        driver.findElement(By.cssSelector("#password")).sendKeys("admin");
        driver.findElement(By.cssSelector("[name='login']")).click();

        driver.get(baseURL + "/wp-admin/upload.php");
        driver.findElement(By.cssSelector("a.page-title-action")).click();
        driver.findElement(By.cssSelector("input[type='file']"))
                .sendKeys("C:\\Users\\simon\\OneDrive\\Obrazy\\cabin.jpg");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[aria-label=\"cabin\"]"))).click();

        Assertions.assertEquals("File size: 2 MB", driver.findElement(By.cssSelector(".file-size")).getText());

        fileUrl = driver.getCurrentUrl();
    }
}
