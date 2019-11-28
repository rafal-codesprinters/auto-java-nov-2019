package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

abstract class WpPage {
    protected static final String BLOG_BASE_URL = "http://www.automation.markowicz.pro";
    final WebDriver driver;

    WpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(this.driver, this);
    }

    WebElement waitForElementPresence(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    WebElement waitUntilElementIsClickable(WebElement element) {
        return new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(element));
    }
}
