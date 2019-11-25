package trainersblog;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class BlogTests {

    public static final By LOC_NAVLINKS = By.cssSelector(".nav-links > a");
    public static final String BASE_URL = "https://markowicz.pro";
    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    void homePageShowsTenArticles() {
        openHomePage();
        List<WebElement> articles = driver.findElements(By.cssSelector("article.post"));
        Assertions.assertEquals(10,articles.size(), "Wrong number of articles on home page");
    }

    @Test
    void navLinksAreDisplayed() {
        openHomePage();
        List<WebElement> navLinks = driver.findElements(LOC_NAVLINKS);
        Assertions.assertTrue(navLinks.size() > 1, "At least two nav link expected");
    }

    @Test
    void canDisplaySecondPageUsingItsNumber() {
        openHomePage();
        List<WebElement> navLinks = driver.findElements(LOC_NAVLINKS);
        WebElement secondPageLink = navLinks.stream()
                .filter(nl -> nl.getAttribute("class").equals("page-numbers") && nl.getAttribute("href").matches("^.*/page/2/$"))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No link to second page found"));
        secondPageLink.click();
        Assertions.assertEquals(BASE_URL + "/page/2/", driver.getCurrentUrl());
    }

    @Test
    void canDisplaySecondPageUsingNextPageLink() {
        openHomePage();
        List<WebElement> navLinks = driver.findElements(LOC_NAVLINKS);
        WebElement nextPageLink = navLinks.stream()
                .filter(nl -> nl.getAttribute("class").matches("^(next page-numbers|page-numbers next)$"))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No link to next page found"));
        nextPageLink.click();
        Assertions.assertEquals(BASE_URL + "/page/2/", driver.getCurrentUrl());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    private void openHomePage() {
        driver.get(BASE_URL);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body.home")));
    }

}
