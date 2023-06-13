package ChallengeTests.Mattermost;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;


public class MattermostTests extends BaseTests{
    @Test
    @DisplayName("Adding channel to favorites marks its star icon")
    public void making_channel_favorite_from_side_menu_should_mark_its_star_icon() {
        logIn();
        driver.get("http://localhost:8065/team1/channels/town-square");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("channelHeaderDropdownIcon")));
        WebElement menuForTownSquare = driver.findElement(By.id("sidebarItem_town-square")).findElement(By.className("SidebarMenu_menuButton"));
        driver.findElement(By.id("sidebarItem_town-square")).click();
        menuForTownSquare.click();
        List<WebElement> menuButtons = driver.findElements(By.className("label-elements"));
        for (WebElement el: menuButtons
        ) {
            if (el.findElement(By.tagName("span")).getText().equals("Favorite")) {
                el.click();
                break;
            }
        }
        WebElement buttonStar = driver.findElement(By.id("toggleFavorite"));
        Assertions.assertEquals("remove from favorites", buttonStar.getDomProperty("ariaLabel"), "Star button may not have been correctly marked.");
        buttonStar.click();
    }


    @Test
    @DisplayName("Before clicking on anything in System Scheme page, Save button should be disabled")
    public void system_scheme_page_should_be_disabled_with_no_input() {
        logIn();
        driver.get("http://localhost:8065/admin_console/user_management/permissions/system_scheme");
        WebElement saveButton = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("saveSetting")));
        Assertions.assertFalse(saveButton.isEnabled(), "The button is incorrectly loaded as enabled before clicking on anything.");
    }

    @Test
    @DisplayName("After clicking on View in Browser, user should see Log In page, then Access Problem page when clicking don't have an account")
    public void choosing_no_account_should_show_no_access() {
        driver.get("http://localhost:8065/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("get-app__continue"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("alternate-link__link"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("AccessProblem__title")));
        Assertions.assertEquals("http://localhost:8065/access_problem", driver.getCurrentUrl(), "Access Problem page is not shown after clicking on 'Don't have an account?'");
    }

    @Test
    @DisplayName("After correctly logging in, user should see Town Square page")
    public void correct_login_should_show_town_square() {
        logIn();
        String currentUrl = driver.getCurrentUrl();
        Assertions.assertEquals("town-square", currentUrl.substring(currentUrl.length() - 11));
    }
}
