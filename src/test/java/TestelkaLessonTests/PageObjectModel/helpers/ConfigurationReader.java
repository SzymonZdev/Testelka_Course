package TestelkaLessonTests.PageObjectModel.helpers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigurationReader {
    private final String propertyNotSpecifiedMessage = "is not specified in the Configuration.properties file.";
    private String browser;
    private String baseURL;
    private String headless;
    private String waitInSeconds;
    private String target;
    private String remoteUrl;
    private String browserVersion;

    public ConfigurationReader() {
        String configurationPath = "src/test/resources/configuration.properties";

        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(configurationPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration file not found at: " + configurationPath);
        }
        Properties properties = new Properties();
        try {
            properties.load(reader);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        baseURL = properties.getProperty("baseURL");
        browser = properties.getProperty("browser");
        headless = properties.getProperty("headless");
        waitInSeconds = properties.getProperty("waitInSeconds");
        target = properties.getProperty("target");
        remoteUrl = properties.getProperty("remoteUrl");
        browserVersion = properties.getProperty("browserVersion");
    }

    public String getBrowser() {
        if (!browser.isEmpty()) return browser;
        else throw new RuntimeException("\"browser\" " + propertyNotSpecifiedMessage);
    }
    public boolean isHeadless() {
        if (!headless.isEmpty()) return Boolean.parseBoolean(headless);
        else throw new RuntimeException("\"headless\" " + propertyNotSpecifiedMessage);
    }
    public String getBaseUrl() {
        if (!baseURL.isEmpty()) return baseURL;
        else throw new RuntimeException("\"baseUrl\" " + propertyNotSpecifiedMessage);
    }

    public int getWaitInSeconds() {
        if (!waitInSeconds.isEmpty()) return Integer.parseInt(waitInSeconds);
        else throw new RuntimeException("\"waitInSeconds\" " + propertyNotSpecifiedMessage);
    }

    public String getTarget() {
        if (!target.isEmpty()) return target;
        else throw new RuntimeException("\"target\" " + propertyNotSpecifiedMessage);
    }

    public String getRemoteUrl() {
        if (!remoteUrl.isEmpty()) return remoteUrl;
        else throw new RuntimeException("\"remoteUrl\" " + propertyNotSpecifiedMessage);
    }
    public String getBrowserVersion() {
        if (!browserVersion.isEmpty()) return browserVersion;
        else throw new RuntimeException("\"browserVersion\" " + propertyNotSpecifiedMessage);
    }
}
