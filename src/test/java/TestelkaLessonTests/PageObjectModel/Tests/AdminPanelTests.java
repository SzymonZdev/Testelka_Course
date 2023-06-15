package TestelkaLessonTests.PageObjectModel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class AdminPanelTests extends BaseTests{
    private final String newProductSlug = "post-new.php?post_type=product";
    @BeforeEach
    public void adminLogin() {
        driver.get(baseUrl + "/my-account/");
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void admin_successful_login_should_display_my_account_content() {
        Assertions.assertDoesNotThrow(() -> driver.findElement(By.className("woocommerce-MyAccount-content")), "The my account content is missing. User may not be logged in.");
    }

    @Test
    public void select_all_products_should_select_each_of_them() {
        driver.get(baseUrl + "/wp-admin/edit.php?post_type=product");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("activity-panel-tab-setup")));
        driver.findElement(By.id("cb-select-all-1")).click();
        List<WebElement> productCheckboxes = driver.findElements(By.name("post[]"));
        boolean noUnselectedCheckboxes = productCheckboxes.stream().allMatch(WebElement::isSelected);
        Assertions.assertTrue(noUnselectedCheckboxes, "A checkbox is unselected, even though select all was used.");
    }

    @Test
    public void virtual_product_should_not_show_shipping() {
        driver.get(baseUrl + "/wp-admin/" + newProductSlug);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("_virtual"))).click();
        WebElement shippingOptions = driver.findElement(By.className("hide_if_virtual"));
        Assertions.assertFalse(shippingOptions.isDisplayed(), "Shipping option is displayed but should be hidden. The product has the virtual option chosen.");
    }
}
