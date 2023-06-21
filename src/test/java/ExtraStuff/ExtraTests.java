package ExtraStuff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.print.PageMargin;
import org.openqa.selenium.print.PageSize;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

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

    @Test
    public void cookieTest() {
        driver.get(baseURL + "/product/calculus-made-easy-by-silvanus-p-thompson/");
        driver.findElement(By.cssSelector("[name='add-to-cart']")).click();
        int noOfCookies = driver.manage().getCookies().size();

        Cookie itemsInCartCookie = driver.manage().getCookieNamed("woocommerce_items_in_cart");
        driver.manage().deleteCookie(itemsInCartCookie);

        Assertions.assertEquals(noOfCookies-1, driver.manage().getCookies().size());
    }

    @Test
    public void dropdownSelectsTest() {
        driver.get("https://fakestore.testelka.pl/lista-rozwijana/");
        WebElement selectElement = driver.findElement(By.cssSelector("select#flavors"));

        Select select = new Select(selectElement);
        select.selectByIndex(3);
        // select.selectByVisibleText("marakuja");
        // select.selectByValue("passion-fruit");

        Assertions.assertEquals(4, select.getOptions().size());
    }

    @Test
    public void multiple_choice_select_with_selected_options_example() {
        driver.get("https://fakestore.testelka.pl/lista-rozwijana/");
        WebElement selectElement = driver.findElement(By.cssSelector("select#flavors-multiple-selected"));
        Select select = new Select(selectElement);

        // select.deselectByIndex(1);
        select.deselectByValue("chocolate");
        // select.deselectByVisibleText("czekoladowy");

        Assertions.assertEquals(1, select.getAllSelectedOptions().size());
    }

    @Test
    public void printPageExample() {
        driver.get(baseURL);
        PrintsPage printer = (PrintsPage) driver;
        PrintOptions printOptions = new PrintOptions();
        printOptions.setPageSize(new PageSize(27.94, 21.59));
        printOptions.setPageMargin(new PageMargin(0, 0, 0, 0));
        printOptions.setBackground(true);
        printOptions.setScale(0.50);


        Pdf pdf = printer.print(printOptions);
        String content = pdf.getContent();
        byte[] decodedBytes = Base64.getDecoder().decode(content);
        try {
            Files.write(Paths.get("./target/output_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".pdf"), decodedBytes);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while writing the PDF file: " + e);
        }
    }

    @Test
    public void screenshotExample() {
        driver.get(baseURL);
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Path destinationPath = Paths.get("./target/screenshot_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".png");
        try {
            Files.copy(screenshot.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while writing the screenshot file: " + e);
        }
    }

    @Test
    public void elementScreenshotExample() {
        driver.get(baseURL);
        WebElement element = driver.findElement(By.cssSelector(".wc-block-grid__product-link"));

        File screenshot = element.getScreenshotAs(OutputType.FILE);
        Path destinationPath = Paths.get("./target/screenshot_of_element_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss")) + ".png");
        try {
            Files.copy(screenshot.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred while writing the screenshot file: " + e);
        }
    }

    @Test
    public void failed_test_for_screenshots() {
        driver.get(baseURL);
        WebElement element = driver.findElement(By.cssSelector(".main-title"));

        Assertions.assertEquals("Testium Appium", element.getText());
    }
}
