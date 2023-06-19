package TestelkaLessonTests.BotPattern;

import TestelkaLessonTests.ActionBot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BotBasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected ActionBot bot;
    protected String baseURL = "http://vps-6191b6eb.vps.ovh.net:8080/";

    @BeforeEach
    public void setup() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        bot = new ActionBot(driver, baseURL);
    }

    @AfterEach
    public void quitDriver() {
        driver.quit();
    }

}
