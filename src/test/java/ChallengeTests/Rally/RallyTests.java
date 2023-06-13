package ChallengeTests.Rally;

import ChallengeTests.Rally.API.PollFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class RallyTests extends BaseTests {
    @Test
    public void option_for_time_apply_to_all_dates_should_work_as_expected() {
        driver.get(baseUrl + "/new");
        driver.findElement(By.id("title")).sendKeys("Miesięczne spotkanie");
        driver.findElement(By.id("location")).sendKeys("Sklep z kawą Joe");
        driver.findElement(By.id("description")).sendKeys("Cześć wszystkim, wybierzcie terminy, które Wam pasują!");
        driver.findElement(By.className("btn-primary")).click();
        driver.findElement(By.xpath(".//div[contains(@class, 'h-12')]/button/span[text() = '10']")).click();
        driver.findElement(By.xpath(".//div[contains(@class, 'h-12')]/button/span[text() = '11']")).click();
        driver.findElement(By.xpath(".//div[contains(@class, 'h-12')]/button/span[text() = '15']")).click();
        driver.findElement(By.cssSelector("[data-testid='specify-times-switch']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("divide-y")));

        List<WebElement> firstDateHours = driver.findElements(By.xpath(".//div[@class='space-y-3 p-3 sm:flex sm:space-y-0 sm:space-x-4 sm:p-4'][1]/div/div/div/button[contains(@id, 'headlessui-listbox-button')]"));
        for (int i = 0; i < firstDateHours.size(); i++) {
            if (i == 0) {
                firstDateHours.get(i).click();
                driver.findElement(By.xpath(".//li[contains(@class, 'menu-item')]/div/span[text() = '1:00 PM']")).click();
            } else {
                firstDateHours.get(i).click();
                driver.findElement(By.xpath(".//li[contains(@class, 'menu-item')]/div/span[text() = '1:30 PM']")).click();
            }
        }
        driver.findElement(By.xpath(".//div[@class = 'space-y-3 p-3 sm:flex sm:space-y-0 sm:space-x-4 sm:p-4'][1]//div[contains(@id, 'headlessui-menu-button')]")).click();
        driver.findElement(By.xpath(".//div[contains(@id, 'headlessui-menu-items')]/button[text() = 'Apply to all dates']")).click();

        List<WebElement> allDateHours = driver.findElements(By.xpath(".//div[@class='space-y-3 p-3 sm:flex sm:space-y-0 sm:space-x-4 sm:p-4']/div/div/div/button[contains(@id, 'headlessui-listbox-button')]"));
        boolean hoursAreCorrect = allDateHours.stream().allMatch(e ->  e.getText().equals("1:00 PM") || e.getText().equals("1:30 PM"));
        Assertions.assertTrue(hoursAreCorrect, "Apply to all dates option did not work for specify times");
    }

    @Test
    public void add_comments_to_poll_as_user_should_be_possible() {
        PollFactory pollFactory = new PollFactory();
        String urlId = pollFactory.createPoll();
        driver.get(baseUrl + "/admin/" + urlId);
        String guestLink = driver.findElement(By.cssSelector("input.w-full.rounded-md")).getDomAttribute("value");
        driver.get(guestLink);
        String comment = "This is my comment!";
        wait.until(ExpectedConditions.elementToBeClickable(By.className("btn-default")));
        driver.findElement(By.id("comment")).sendKeys(comment);
        driver.findElement(By.name("authorName")).sendKeys("AutTestAuthor");
        driver.findElement(By.cssSelector(".btn-default[type='submit']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='comment']")));
        String textFromNewComment = driver.findElement(By.cssSelector("[data-testid='comment'] div.w-fit")).getText();
        pollFactory.deletePoll(urlId);
        Assertions.assertEquals(comment, textFromNewComment, "The text in the new comment is not what's expected.");
    }

    @Test
    public void create_new_meeting_should_be_possible() {
        driver.get(baseUrl + "/new");
        driver.findElement(By.id("title")).sendKeys("Miesięczne spotkanie");
        driver.findElement(By.id("location")).sendKeys("Sklep z kawą Joe");
        driver.findElement(By.id("description")).sendKeys("Cześć wszystkim, wybierzcie terminy, które Wam pasują!");
        driver.findElement(By.className("btn-primary")).click();


        driver.findElement(By.className("focus:rounded")).click();
        driver.findElement(By.className("btn-primary")).click();

        driver.findElement(By.id("name")).sendKeys("Szym Test");
        driver.findElement(By.id("contact")).sendKeys("szym@test.net");
        driver.findElement(By.className("btn-primary")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("comment")));
        Assertions.assertEquals("Miesięczne spotkanie", driver.findElement(By.className("text-slate-800")).getText(), "Poll might not have been created correctly.");
    }

    @Test
    public void getting_to_second_step_should_be_possible() {
        driver.get(baseUrl + "/new");
        driver.findElement(By.id("title")).sendKeys("Miesięczne spotkanie");
        driver.findElement(By.id("location")).sendKeys("Sklep z kawą Joe");
        driver.findElement(By.id("description")).sendKeys("Cześć wszystkim, wybierzcie terminy, które Wam pasują!");
        driver.findElement(By.className("btn-primary")).click();
        Assertions.assertEquals("Step 2 of 3", driver.findElement(By.className("tracking-tight")).getText(), "You may not be on the second step of the process.");
    }

    @Test
    public void placeholders_for_new_event_should_be_correct() {
        driver.get(baseUrl + "/new");
        String titleText = driver.findElement(By.id("title")).getDomAttribute("placeholder");
        String locationText = driver.findElement(By.id("location")).getDomAttribute("placeholder");
        String descriptionText = driver.findElement(By.id("description")).getDomAttribute("placeholder");
        boolean titleMatches = titleText.matches("Monthly Meetup");
        boolean locationMatches = locationText.matches("Joe's Coffee Shop");
        boolean descriptionMatches = descriptionText.matches("Hey everyone, please choose the dates that work for you!");
        Assertions.assertAll(
                () -> Assertions.assertTrue(titleMatches,
                        "Title field placeholder is not correct."),
                () -> Assertions.assertTrue(locationMatches,
                        "Location field placeholder is not correct."),
                () -> Assertions.assertTrue(descriptionMatches,
                        "Description field placeholder is not correct.")
        );

    }
}
