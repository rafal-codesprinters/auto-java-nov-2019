package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WpMainPage extends WpPage{
    private static final By LOCATOR_POST_LINK = By.cssSelector("article.post .entry-title > a");
    private static final By LOCATOR_MAIN_PAGE_BODY = By.cssSelector("body.home");

    private WpMainPage(WebDriver driver) {
        super(driver);
        driver.get(BLOG_BASE_URL);
        waitForElementPresence(LOCATOR_MAIN_PAGE_BODY);
    }

    public static WpMainPage open(WebDriver driver) {
        return new WpMainPage(driver);
    }

    public WpNotePage openLatestNote() {
        waitForElementPresence(LOCATOR_POST_LINK).click();
        return new WpNotePage(driver);
    }
}
