package blog;

import driverfactory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class BlogTests {

    private static final String BLOG_HOME_URL = "https://markowicz.pro";
    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
        // driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    void homePageShowsTenArticles() {
        openHomePage();
        List<WebElement> articles = driver.findElements(By.cssSelector("article.post"));
        Assertions.assertEquals(10, articles.size());
    }

    @Test
    void navLinksAreDisplayed() {
        openHomePage();
        List<WebElement> navLinks = driver.findElements(By.cssSelector(".pagination"));
        Assertions.assertTrue(navLinks.size() == 1);
       // List<WebElement> navLinks = driver.findElements(By.cssSelector("a.page-numbers"));
        //Assertions.assertTrue(navLinks.size() > 2);
    }

    @Test
    void canDisplaySecondPageUsingItsNumber() {
        openHomePage();
        /*List<WebElement> navLinks = driver.findElements(LOC_NAVLINKS);
        WebElement secondPageLink = navLinks.stream()
                .filter(nl -> nl.getAttribute("class").equals("page-numbers") && nl.getAttribute("href").equals(BASE_URL + "/page/2/"))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No link to second page found"));*/
        WebElement secondPageLink = driver.findElement(By.cssSelector("a.page-numbers:first-of-type"));
        secondPageLink.click();
        Assertions.assertEquals(BLOG_HOME_URL + "/page/2/", driver.getCurrentUrl());
    }

    @Test
    void canDisplaySecondPageUsingNextPageLink() {
        openHomePage();
        /*List<WebElement> navLinks = driver.findElements(LOC_NAVLINKS);
        WebElement nextPageLink = navLinks.stream()
                .filter(nl -> nl.getAttribute("class").matches("^(next page-numbers|page-numbers next)$"))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("No link to next page found"));*/
        // WebElement nextPageLink = driver.findElement(By.cssSelector("a.page-numbers.next"));
        WebElement nextPageLink = explicitlWaitForElement(By.cssSelector("a.page-numbers.next"));
        nextPageLink.click();
        Assertions.assertEquals(BLOG_HOME_URL + "/page/2/", driver.getCurrentUrl());
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    private void openHomePage() {
        driver.get(BLOG_HOME_URL);
        fluentWaitForElement(By.cssSelector("body.home"));
    }

    private WebElement explicitlWaitForElement(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private WebElement fluentWaitForElement(By locator) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(NoSuchElementException.class);
        return wait.until(drv -> drv.findElement(locator));
    }

}
