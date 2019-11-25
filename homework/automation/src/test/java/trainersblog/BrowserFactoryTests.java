package trainersblog;

import browserfactory.BrowserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class BrowserFactoryTests {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = BrowserFactory.getBrowser();
        driver.manage().window().maximize();
    }

    @Test
    void canOpenBlog() {
        driver.get("https://markowicz.pro");
        String title = driver.findElement(By.className("site-title")).getText();
        Assertions.assertEquals("Rafał Markowicz", title);
        String description = driver.findElement(By.className("site-description")).getText();
        Assertions.assertEquals("Kolejny piękny dzień", description);
    }

    @AfterEach
    void TearDown() {
        driver.quit();
    }

}
