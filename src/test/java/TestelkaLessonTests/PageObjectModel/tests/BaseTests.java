package TestelkaLessonTests.PageObjectModel.tests;


import TestelkaLessonTests.PageObjectModel.helpers.Browser;
import TestelkaLessonTests.PageObjectModel.helpers.BrowserFactory;
import TestelkaLessonTests.PageObjectModel.helpers.ConfigurationReader;
import TestelkaLessonTests.PageObjectModel.helpers.NoSuchBrowserException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public class BaseTests {
    protected Browser browser;
    private static ConfigurationReader configuration;
    @BeforeAll
    public static void loadConfiguration() {
        configuration = new ConfigurationReader();
    }
    @BeforeEach
    public void setup() {
        BrowserFactory browserFactory = new BrowserFactory();
        try {
            browser = browserFactory.createInstance(configuration);
        } catch (NoSuchBrowserException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterEach
    public void quitDriver() {
        browser.driver.quit();
    }
}
