package ChallengeTests.Mattermost;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTests {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterEach
    public void quitDriver() {
        driver.quit();
    }

    public void logIn() {
        driver.get("http://localhost:8065/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("get-app__continue"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("input_loginId"))).sendKeys("simonTest");
        driver.findElement(By.id("input_password-input")).sendKeys("12345");
        driver.findElement(By.id("saveSetting")).click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.id("loadingSpinner"), 0));
    }
}
