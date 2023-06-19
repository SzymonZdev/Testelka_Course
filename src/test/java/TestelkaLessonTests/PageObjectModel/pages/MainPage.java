package TestelkaLessonTests.PageObjectModel.pages;

import TestelkaLessonTests.PageObjectModel.helpers.Browser;

public class MainPage extends BasePage{
    public final TestelkaLessonTests.PageObjectModel.pages.StoreHeaderComponent storeHeader;

    public MainPage(Browser browser) {
        super(browser);
        storeHeader = new StoreHeaderComponent(browser);
    }

    public MainPage go() {
        driver.get(baseURL);
        return this;
    }
}
