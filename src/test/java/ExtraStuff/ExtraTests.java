package ExtraStuff;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.NetworkInterceptor;
import org.openqa.selenium.devtools.events.DomMutationEvent;
import org.openqa.selenium.devtools.v85.log.Log;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.HasLogEvents;
import org.openqa.selenium.print.PageMargin;
import org.openqa.selenium.print.PageSize;
import org.openqa.selenium.print.PrintOptions;
import org.openqa.selenium.remote.http.HttpResponse;
import org.openqa.selenium.remote.http.Route;
import org.openqa.selenium.support.locators.RelativeLocator;
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
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.openqa.selenium.devtools.events.CdpEventTypes.domMutation;
import static org.openqa.selenium.remote.http.Contents.utf8String;

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

    @Test
    public void js_execution_test() {
        driver.get(baseURL);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement generatePressLink = driver.findElement(By.cssSelector("a[href='https://generatepress.com']"));
        js.executeScript("arguments[0].scrollIntoView();", generatePressLink);
    }

    @Test
    public void another_js_execution_test() {
        driver.get(baseURL);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement product_link = driver.findElement(By.cssSelector("div.wc-block-grid__product-title"));
        String product_link_text = js.executeScript("return arguments[0].innerText;", product_link).toString();
        System.out.println(product_link_text);
    }

    @Test
    public void console_logs_test() {
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());
        devTools.getDomains().events().addConsoleListener(
                log -> System.out.println(
                        log.getTimestamp() + " " + log.getType() + " " + log.getMessages()
                    )
        );
        driver.get("https://fakestore.testelka.pl/console-log-events");
    }

    @Test
    public void javascript_exceptions_test() {
        DevTools devTools = ((HasDevTools) driver).getDevTools();
        devTools.createSession();
        devTools.getDomains().events().addJavascriptExceptionListener(System.out::println);
        driver.get("https://fakestore.testelka.pl/javascript-exceptions/");
        driver.findElement(By.id("button-1")).click();
    }



    @Test   // Used to catch a DOM change through an AtomicReference (thread-safe, set when we log a dom change)
    public void dom_changes_test() {
        driver.get("https://fakestore.testelka.pl/zmiany-w-dom/");
        AtomicReference<DomMutationEvent> seen = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        ((HasLogEvents) driver).onLogEvent(domMutation(mutation -> {
            seen.set(mutation);
            latch.countDown();
        }));
        WebElement secondButton = driver.findElement(By.cssSelector("#second-button"));
        secondButton.click();

        Assertions.assertAll(
                () -> Assertions.assertTrue(latch.await(10, TimeUnit.SECONDS)),
                () -> Assertions.assertEquals("class", seen.get().getAttributeName()),
                () -> Assertions.assertEquals("primary-button", seen.get().getCurrentValue())
        );
    }

    @Test   // Used to intercept network traffic, this test changes all server-side loaded css files and changes their content
    public void network_traffic_test() {
        try (NetworkInterceptor networkInterceptor = new NetworkInterceptor(driver,
                Route.matching(req -> req.getUri().contains(".css"))
                        .to(() -> req -> new HttpResponse().setStatus(200)
                                .setHeader("Content-Type", "text/css")
                                .setContent(utf8String("Nie lubię CSSa")))))
        {
            driver.get("https://fakestore.testelka.pl");
        }
    }

    @Test   // Attaches an authenticator to the driver, which is then used to open sites (used instead of passing in site address (<username>:<password>))
    public void basic_auth_test() {
        ((HasAuthentication)driver).register(UsernameAndPassword.of("harrypotter", "Alohomora"));
        driver.get("https://fakestore.testelka.pl/wp-content/uploads/protected/cos.html");

    }

    @Test
    public void webStorage_chrome_test() {
        driver.get("https://airly.org/map/pl/#50.0626789849,19.9326583871");
        LocalStorage storage = ((ChromeDriver)driver).getLocalStorage();
        String map = storage.getItem("persist:map");
        storage.removeItem("persist:map");
        storage.setItem("some key", "some value");
        Set<String> keys = storage.keySet();
        int size = storage.size();
        storage.clear();
    }

    @Test
    public void webStorage_js_test() {
        driver.get("https://airly.eu/map/pl/#50.06237,19.93898");
        String key = "persist:map";
        String value = (String) ((JavascriptExecutor) driver)
                .executeScript("return localStorage.getItem(arguments[0]);", key);
        ((JavascriptExecutor) driver)
                .executeScript("localStorage.setItem(arguments[0], arguments[1]);", "some key", "some value");
        ((JavascriptExecutor) driver)
                .executeScript("localStorage.removeItem(arguments[0]);", key);
        String indexValue = (String) ((JavascriptExecutor) driver)
                .executeScript("return localStorage.key(arguments[0]);", 2);
        long size = (long) ((JavascriptExecutor) driver)
                .executeScript("return localStorage.length;");
        ((JavascriptExecutor) driver).executeScript("localStorage.clear();");
    }

    @Test
    public void alert_test() {
        driver.get("https://fakestore.testelka.pl/alerty");
        driver.findElement(By.cssSelector("[onclick='alertFunction()']")).click();
        Alert alert = driver.switchTo().alert();

        Assertions.assertEquals("To jest po prostu alert", alert.getText());
        //    alert.accept();
    }

    @Test
    public void confirm_alert_test() {
        driver.get("https://fakestore.testelka.pl/alerty");
        driver.findElement(By.cssSelector("[onclick='confirmFunction()']")).click();
        Alert alert = driver.switchTo().alert();
        // Accept the alert
        // alert.accept();
        //  OR dismiss the alert
        alert.dismiss();
        String message = driver.findElement(By.cssSelector("#demo")).getText();


        // Assertions.assertEquals("Wybrana opcja to OK!", message);
        // OR
        Assertions.assertEquals("Wybrana opcja to Cancel!", message);
    }

    @Test
    public void prompt_test() {
        driver.get("https://fakestore.testelka.pl/alerty");
        driver.findElement(By.cssSelector("[onclick='promptFunction()']")).click();
        Alert alert = driver.switchTo().alert();
        alert.sendKeys("Szymon");
        alert.accept();
        String message = driver.findElement(By.cssSelector("#prompt-demo")).getText();

        Assertions.assertEquals("Cześć Szymon! Jak leci?", message);
    }
    @Test
    public void delayed_prompt_test() {
        driver.get("https://fakestore.testelka.pl/alerty");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        driver.findElement(By.cssSelector("[onclick='delayedAlertFunction()']")).click();
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        Assertions.assertEquals("Miałem mały poślizg", alert.getText());
    }

    @Test
    public void multiple_windows_test() {
        driver.get(baseURL + "/product/calculus-made-easy-by-silvanus-p-thompson/");
        String originalWindow = driver.getWindowHandle();
        driver.switchTo().newWindow(WindowType.TAB);
        // driver.switchTo().newWindow(WindowType.WINDOW);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        String otherWindow = driver.getWindowHandle();
        driver.get(baseURL);
        driver.switchTo().window(originalWindow);
        driver.get(baseURL + "/wishlist/");
        driver.switchTo().window(otherWindow);
        driver.close();
    }

    @Test   // using the has() pseudo-class to find an element based on child element
    public void has_pseudo_test() {
        driver.get(baseURL + "/cart/");
        // Finding a TR element based on a child element, which has the product ID attribute
        WebElement element = driver.findElement(By.cssSelector("tr:has(a[data-product_id='12'])"));
    }

    @Test   // using the relative locator, finding an element by referencing another
    public void relative_locator_test() {
        driver.get(baseURL + "/product/history-of-astronomy-by-george-forbes/");
        // Finding a TR element based on a child element, which has the product ID attribute
        WebElement element = driver.findElement(By.cssSelector(")"));
        By quantity = By.cssSelector("input[name='quantity]");
        // Using the relative locator to select the button by referencing the previously found element
        By addToCart = RelativeLocator.with(By.cssSelector("button")).toRightOf(quantity);
    }

    @Test
    public void action_api_test_click_with_button() {
        driver.get("https://fakestore.testelka.pl/select/");
        List<WebElement> items = driver.findElements(By.cssSelector(".ui-selectee"));
        Actions action = new Actions(driver)
                .keyDown(Keys.CONTROL)
                .click(items.get(0))
                .click(items.get(3))
                .click(items.get(4))
                .keyUp(Keys.CONTROL);
        action.perform();
        List<WebElement> selectedItems = driver.findElements(By.cssSelector(".ui-selected"));

        Assertions.assertEquals(3, selectedItems.size(), "Number of selected items is not what was expected.");
    }

    @Test
    public void action_api_test_drag_and_drop() {
        driver.get("https://fakestore.testelka.pl/actions/");
        Actions actions = new Actions(driver);
        WebElement draggable = driver.findElement(By.cssSelector("#draggable"));
        WebElement droppable = driver.findElement(By.cssSelector("#droppable"));
        //actions.clickAndHold(draggable).moveToElement(droppable, 74, 74).release().perform();
        actions.dragAndDrop(draggable, droppable);
        actions.perform();

        Assertions.assertEquals("Dropped!", droppable.getText(), "Message in the droppable box was not changed. Was the element dropped?");
    }
}
