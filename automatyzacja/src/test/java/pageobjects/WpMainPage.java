package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WpMainPage {
    public static final By LOCATOR_POST_LINK = By.cssSelector("article.post .entry-title > a");
    private final WebDriver driver;

    public WpMainPage(WebDriver driver) {
        this.driver = driver;
        driver.get("http://www.automation.markowicz.pro");
    }

    public static WpMainPage open(WebDriver driver) {
        return new WpMainPage(driver);
    }

    public WpNotePage openLatestNote() {
        driver.findElement(LOCATOR_POST_LINK).click();
        return new WpNotePage(driver);
    }
}
