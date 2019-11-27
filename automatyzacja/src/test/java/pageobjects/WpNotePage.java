package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WpNotePage extends WpPage {
    private static final By LOCATOR_COMMENT_FORM = By.id("commentform");
    private static final By LOCATOR_NOTE_PAGE_BODY = By.cssSelector("body.single-post");

    WpNotePage(WebDriver driver) {
        super(driver);
        waitForElementPresence(LOCATOR_NOTE_PAGE_BODY);
    }

    public boolean commentsAreAllowed() {
        waitForElementPresence(LOCATOR_COMMENT_FORM);
        return driver.findElements(LOCATOR_COMMENT_FORM).size() == 1;
    }

}
