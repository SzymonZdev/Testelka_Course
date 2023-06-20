package ExtraStuff;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class BaseTest {
    protected WebDriver driver;
    protected String fileUrl;
    protected String baseURL = "http://vps-6191b6eb.vps.ovh.net:8080";

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions().addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void quitDriver() {
        if (fileUrl != null) {
            driver.get(fileUrl);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".delete-attachment"))).click();
            driver.switchTo().alert().accept();
        }
        driver.quit();
    }
}
