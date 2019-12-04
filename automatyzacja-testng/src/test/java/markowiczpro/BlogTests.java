package markowiczpro;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testframework.driverfactory.DriverFactory;

import java.time.Duration;
import java.util.List;

public class BlogTests {

    /*W tych testach wykorzystywana jest klasa DriverFactory, ale nie korzystają one z Page Object Patter. Dlatego też
    * na końcu zdefiniowane są wprost w tej klasie z testami metody "waitFor...()".*/

    private static final String BLOG_HOME_URL = "https://markowicz.pro";
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    @BeforeMethod
    void setup() {
        driver.set(DriverFactory.getDriver());
        driver.get().manage().window().maximize();
        /*Możliwe jest określenie implicit timeoutu dla wszystkich operacji w przeglądarce w ten sposób:
        * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);*/
    }

    @Test
    void homePageShowsTenArticles() {
        /*Aby w kolejnych testach nie pisać wielokroktnie tego samego, kod otwierający stronę główną bloga przeniesiony
        * został do metody "openHomePage()" w ramach tej samej klasy.*/
        openHomePage();
        List<WebElement> articles = driver.get().findElements(By.cssSelector("article.post"));
        Assert.assertEquals(articles.size(), 10);
    }

    @Test
    void navLinksAreDisplayed() {
        openHomePage();
        List<WebElement> navLinks = driver.get().findElements(By.cssSelector(".pagination"));
        Assert.assertEquals(navLinks.size(), 1);
    }

    @Test
    void canDisplaySecondPageUsingItsNumber() {
        openHomePage();
        /*Ponieważ na stronie pojawia się okienko z informacją o polityce cookies, które może przysłonić przyciski
        * nawigacji do kolejnych stron, w metodzie "closeCookieInfo()" obsłużone jest zamknięcie tego okienka.*/
        closeCookieInfo();
        WebElement secondPageLink = waitForElementClickable(By.cssSelector("a.page-numbers:first-of-type"));
        secondPageLink.click();
        Assert.assertEquals(driver.get().getCurrentUrl(), BLOG_HOME_URL + "/page/2/");
    }


    @Test
    void canDisplaySecondPageUsingNextPageLink() {
        openHomePage();
        closeCookieInfo();
        WebElement nextPageLink = waitForElementClickable(By.cssSelector("a.page-numbers.next"));
        nextPageLink.click();
        Assert.assertEquals(driver.get().getCurrentUrl(), BLOG_HOME_URL + "/page/2/");
    }

    @AfterMethod
    void tearDown() {
        driver.get().quit();
    }

    /*Ponniżej znajdują się metody wykorzystywane w testach aby nie duplikować kodu.*/

    private void openHomePage() {
        driver.get().get(BLOG_HOME_URL);
        fluentWaitForElement(By.cssSelector("body.home"));
    }

    private void closeCookieInfo() {
        /*Testy w tej klasie nie są związane ze sprawdzeniem, czy cookie info się wyświetliło i że da się je zamknąć.
        * W szczególności jest możliwe, że w konfiguracji strony wyświetlanie cookie info zostanie w ogóle wyłączone.
        * Dlatego potrzebna jest metoda, która:
        * - znajdzie cookie info o ile ono sie pojawi
        * - zamknie je
        * Kod poniżej jest implementacją pokazującą jak to zrobić w sposób bezpieczny.
        *
        * Najpierw znajdowane są metodą "findElements()" wszystkie takie okienka cookie info. Celowo używam tej metody
        * a nie "findElement()" bo ta pierwsza nie zwróci wyjątku, jeśli nic nie znajdzie, zaś ta druga zwróci w takim
        * przypadu "NoSuchElementExcception". Oczywiście spodziewam się zero lub jednej instancji okienka z polityką
        * cookie.
        *
        * Ponieważ jednak "findElements()" zwraca listę (kolekcję), muszę obsłużyć każdy z jej elementów. Dlatego dla
        * każdego znalezionego przycisku akceptacji polityki cookies wykonuję metodę "click()".
        *
        * Zapis poniżej "forEach(WebElement::click)" należy rozumieć tak: dla każdego znalezionego WebElementu wywołaj
        * metodę "click()". Jest to tożsame z dużo bardziej rozwlekłym zapisem:
        * for (WebElement webElement : driver.findElements(By.cssSelector("#eu-cookie-law input"))) {
            webElement.click();
        }*/
        driver.get().findElements(By.cssSelector("#eu-cookie-law input")).forEach(WebElement::click);
        /*Sprawdzanie, że czegoś nie ma w teście nie ma sensu - chyba, że wiemy, że wcześniej istniało i sprawdzamy, że
        * znikło. Tak jest w tym przypadku: spodziewam się, że znajdę coockie info, więc jak go zamknę, powinno zniknąć.
        * A ponieważ jest to animowane (na bazie JQuery), czekam aż cookie info zniknie po kliknięciu.
        *
        * Gdyby żadnego cookie info nie było od samego początku, to metoda "waitForElementNotVisible(...)" od razu
        * zwróci "true" i test będzie wykonywał się dalej bez czekania.*/
        waitForElementNotVisible(By.id("eu_cookie_law_widget-2"));
    }

    private WebElement explicitlWaitForElement(By locator) {
        /*Przykład oczekiwania na pojawienie się elementu z podanym locatorem (obiektem klasy By) z wykorzystaniem
        explicit waita. Uwaga: umieszczony tu dla porównania z fluent waitem poniżej, nieużyty w teście.*/
        return new WebDriverWait(driver.get(), 5).until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    private WebElement fluentWaitForElement(By locator) {
        /*Przykład oczekiwania na element za pomocą fluent waita.*/
        Wait<WebDriver> wait = new FluentWait<>(driver.get())
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(250))
                .ignoring(NoSuchElementException.class);
        return wait.until(webDriver -> driver.get().findElement(locator));
    }

    private WebElement waitForElementClickable(By locator) {
        /*Metoda oczekuje, aż element o podanym locatorze (np. xpath) będzie klikalny i zwracająca ten element.
        * Uwaga: to, że element jest klikalny nie gwarantuje, że da się w niego kliknąć - może być przysłonięty innym
        * elementem, wtedy zwrócony zostanie exception "ElementClickInterceptedException".*/
        return new WebDriverWait(driver.get(), 5).until(ExpectedConditions.elementToBeClickable(locator));
    }

    private void waitForElementNotVisible(By locator) {
        /*Metoda oczekująca na zniknięcie elementu o podanym locatorze (np. xpath). Uwaga: użycie takiego sprawdzenia
        * ma sens tylko jeśli wiemy, że wcześniej element istniał (np. znaleźliśmy go) lub przynajmniej mógł istnieć -
        * podobnie jak ja zrobiłem to w metodzie "closeCookieInfo()".
        *
        * Jeśli element wcześniej istniał, możliwe jest jeszcze inne sprawdzenie - przykład w poniżej metodzie
        * "waitForElementStaleness(...)".*/
        new WebDriverWait(driver.get(), 5).until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    private void waitForElementStaleness(WebElement element) {
        /*Jeśli istnieje element opisujący jakiś kawałek strony (czyli mamy obiekt klasy WebElement), możliwe jest
        * spradzenie, czy już znikł tak jak poniżej. Zamiast locatora przekazujemy wtedy sam element i sprawdzamy, czy
        * jest on tzw. "stale element", czyli elementem usuniętym z DOM przeglądarki.
        *
        * Metody tej trzeba użyć jeśli spodziewamy się że zniknie np. piąty element z dziesięciu istniejących na
        * początku na stronie. Skorzystanie z rozwiązania pokazanego powyżej (metoda "waitForElementNotVisible(...)"
        * nie ma sensu, ponieważ tam przekazujemy locator i po usunięciu jednego z wielu elementów po prostu znajdziemy
        * inny niż ten usunięty.
        *
        * W tym przykładzie uzywamy metody "stalenessOf(...)", do której przekazujemy konkretny element - więc jego
        * obecność będzie sprawdzana Przy czym uwaga: to nie zadziała, jeśli tego konkretnego elementu wcześniej
        * poszukiwaliśmy poprzez PageFactory, ponieważ ono z natury zakrywa wszystkie wyjątki
        * "StaleElementReferenceException".*/
        new WebDriverWait(driver.get(), 5).until(ExpectedConditions.stalenessOf(element));
    }

}
