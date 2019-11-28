package sandbox;

import driverfactory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.stream.Collectors;

public class SeleniumTests {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        //System.setProperty("webdriver.chrome.driver", "C:\WebDrivers\chromedriver.exe");
        driver = DriverFactory.getDriver();
    }

    @Test
    void canOpenGooglePage() {

        driver.get("https://google.com");
        List<WebElement> image = driver.findElements(By.xpath("//img[@alt='Google']"));
        Assertions.assertEquals(1, image.size());

    }

    @Test
    void canFindRafalsBlog() {

        driver.get("https://google.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Rafał Markowicz blog");
        searchBox.submit();

        List<WebElement> searchResults = driver.findElements(By.className("rc"))
                .stream()
                .filter(res -> res.findElement(By.cssSelector(".r > a")).getAttribute("href").equals("https://markowicz.pro/"))
                .collect(Collectors.toList());

        Assertions.assertEquals(1, searchResults.size());

        String resultTitle = searchResults.get(0).findElement(By.tagName("h3")).getText();
        Assertions.assertEquals("Rafał Markowicz – Kolejny piękny dzień", resultTitle);

        String resultDisplayLink = searchResults.get(0).findElement(By.tagName("cite")).getText();
        Assertions.assertEquals("https://markowicz.pro", resultDisplayLink);

    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

}
