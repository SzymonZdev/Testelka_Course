package TestelkaLessonTests.PageObjectModel.Tests;

import TestelkaLessonTests.PageObjectModel.Pages.AdminPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AdminPanelTests extends BaseTests{
    @Test
    public void admin_successful_login_should_display_my_account_content() {
        AdminPage adminPage = new AdminPage(driver);
        adminPage.go();

        Assertions.assertTrue(adminPage.myAccountContentIsVisible(), "The my account content is missing. User may not be logged in.");
    }

    @Test
    public void select_all_products_should_select_each_of_them() {
        AdminPage adminPage = new AdminPage(driver);
        adminPage.go().displayAllProducts().clickSelectAll();
        boolean noUnselectedCheckboxes = adminPage.noProductsAreUnselected();

        Assertions.assertTrue(noUnselectedCheckboxes, "A checkbox is unselected, even though select all was used.");
    }

    @Test
    public void virtual_product_should_not_show_shipping() {
        AdminPage adminPage = new AdminPage(driver);
        adminPage.goToNewProductPage().clickVirtualProductOption();

        Assertions.assertFalse(adminPage.isShippingOptionsDisplayed(), "Shipping option is displayed but should be hidden. The product has the virtual option chosen.");
    }
}
