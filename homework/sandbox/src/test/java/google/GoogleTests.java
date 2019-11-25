package google;

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

class GoogleTests {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        System.setProperty("webdriver.chrome.driver", "c:\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    void canFindMyBlog() {

        driver.get("https://google.com");

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Rafał Markowicz blog");
        searchBox.submit();

        List<WebElement> results = driver.findElements(By.className("rc"))
                .stream()
                .filter(e -> e.findElement(By.cssSelector(".r > a")).getAttribute("href").equals("https://markowicz.pro/"))
                .collect(Collectors.toList());

        Assertions.assertEquals(1, results.size(), "Incorrect number of results on results page");

        String title = results.get(0).findElement(By.tagName("h3")).getText();
        String link = results.get(0).findElement(By.tagName("cite")).getText();

        Assertions.assertEquals("Rafał Markowicz – Kolejny piękny dzień", title, "Incorrect result title");
        Assertions.assertEquals("https://markowicz.pro", link, "Incorrect result link");

    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

}
