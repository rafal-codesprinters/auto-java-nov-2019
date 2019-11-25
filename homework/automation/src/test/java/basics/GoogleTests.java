package basics;

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

public class GoogleTests {

    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @Test
    void canFindRafalsBlog() {

        driver.get("https://google.com");

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Rafał Markowicz blog");
        searchBox.submit();

        List<WebElement> results = driver.findElements(By.className("rc"));
        List<WebElement> filteredResults = results.stream()
                .filter(res -> res.findElement(By.cssSelector(".r > a")).getAttribute("href").equals("https://markowicz.pro/"))
                .collect(Collectors.toList());

        Assertions.assertEquals(1,filteredResults.size(), "One result expected");

        WebElement result = filteredResults.get(0);
        String title = result.findElement(By.tagName("h3")).getText();
        String link = result.findElement(By.tagName("cite")).getText();

        Assertions.assertEquals("Rafał Markowicz – Kolejny piękny dzień", title);
        Assertions.assertEquals("https://markowicz.pro", link);

    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

}
