package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WpNotePage extends WpPage {
    public static final By LOCATOR_COMMENT_FORM = By.id("commentform");

    public WpNotePage(WebDriver driver) {
        super(driver);
    }

    public boolean commentsAreAllowed() {
        waitForElementPresence(LOCATOR_COMMENT_FORM);
        return driver.findElements(LOCATOR_COMMENT_FORM).size() == 1;
    }

}
