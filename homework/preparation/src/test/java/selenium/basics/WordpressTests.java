package selenium.basics;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;

class WordpressTests {

    private static final String BASE_URL = "http://www.automation.markowicz.pro/";
    private WebDriver driver;

    @BeforeEach
    void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void canOpenMainPage() {
        driver.get(BASE_URL);
        waitForElementPresent(By.cssSelector("body.home"));
        WebElement blogTitle = driver.findElement(By.className("site-title"));
        Assertions.assertEquals("Automation", blogTitle.getText());
    }

    @Test
    void canOpenFirstNote() {
        driver.get(BASE_URL);
        driver.findElements(By.cssSelector(".entry-title a"))
                .stream()
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No notes found on main page"))
                .click();
        waitForElementPresent(By.cssSelector("body.single-post"));
        WebElement noteTitle = driver.findElement(By.className("entry-title"));
        Assertions.assertEquals("Initial Post", noteTitle.getText());
    }

    void waitForElementPresent(By selector) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(selector));
    }

}
