package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class WpPage {
    protected static final String BLOG_BASE_URL = "http://www.automation.markowicz.pro";
    final WebDriver driver;

    WpPage(WebDriver driver) {
        this.driver = driver;
    }

    WebElement waitForElementPresence(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}