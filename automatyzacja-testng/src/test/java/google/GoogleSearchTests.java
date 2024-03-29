package google;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import testframework.driverfactory.DriverFactory;

import java.util.List;
import java.util.stream.Collectors;

public class GoogleSearchTests {

    private final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    @BeforeMethod
    void setup() {
        /*Zalecane rozwiązanie to umieszczenie driverów w lokalizacji dodanej do zmiennej PATH systemu. To pozwala
        * uniknąć konieczności zapisywania gdziekolwiek w kodzie lub konfiguracji lokalizacji driverów.
        * Poniżej przykłąd tego, jak można lokalizację drivera podać dla ChromeDrivera:
        * System.setProperty("webdriver.chrome.driver", "C:\WebDrivers\chromedriver.exe");*/
        driver.set(DriverFactory.getDriver());
    }

    @Test
    void canOpenGooglePage() {
        driver.get().get("https://google.com");
        List<WebElement> image = driver.get().findElements(By.xpath("//img[@alt='Google']"));
        /*W przypadku użycia TestNG i jego klasy Assert kolejność argumentów w metodzie "assertEquals(...)" jest
        * odwrotna niż w przypadku klasy Assertions używanej przez JUnita5 - najpierw wartość otrzymana, potem wartość
        * oczekiwana.*/
        Assert.assertEquals(image.size(), 1);
    }

    @Test
    void canFindRafalsBlog() {
        driver.get().get("https://google.com");
        WebElement searchBox = driver.get().findElement(By.name("q"));
        searchBox.sendKeys("Rafał Markowicz blog");
        searchBox.submit();

        /*Metoda "findElements()" znajduje listę elementów (kolekcję) rezultatów na stronie wyszukiwania Google. Lista
        * może obejmować, zero, jeden lub wiele elementów, ale w naszym przypadku jest ich 10. Aby łatwiej w niej
        * znaleźć poszukiwany element, tworzymy z listy stream metodą "stream()" a potem filtrujemy ten stream metodą
        * "filter()" i na koniec z powrotem zamieniamy na listę metodą "collect()".
        *
        * Zapis "filter(res -> res.findElement()...)" należy rozumieć tak:
        * - weź kolejne elementy w streamie (nazywane tu zmienną lokalną res") i sprawdź dla nich warunek podany po
        *   strzałce
        * - warunek to sprawdzenie, czy "res" (czyli pojedynczy bloczek rezultatów na stronie wyszukiwania) zawiera
        *   element z klasą "r" wewnątrz którego znajdowałby się link ("a") o atrybucie "href" równym warunkowi
        *   podanemu jako argument funkcji "equals()"
        * - resultatem jest pozostawienie w streamie tylko elementów speniających te warunki
        *
        * Filtrowanie takie jest możliwe nawet jak stream od samego początku jest pusty - wtedy wynikiem będzie pustry
        * stream. W wyniku filtrowania stream może zostać obcięty o elementy nie spełniające warunku, lub wręcz stać
        * się pusty.*/
        List<WebElement> searchResults = driver.get().findElements(By.className("rc"))
                .stream()
                .filter(res -> res.findElement(By.cssSelector(".r > a")).getAttribute("href").equals("https://markowicz.pro/"))
                .collect(Collectors.toList());

        /*Ponieważ na końcu stream zamieniony został na powrót na listę, możemy na niej sprawdzić, czy jej rozmiar
        * jest równy oczekiwanemu, czyli że rezultat jest jeden.*/
        Assert.assertEquals(searchResults.size(), 1);

        /*Jeśli elementów było mniej niż jeden, lub lista była pusta, test zostanie oznaczony jako failed. Natomiast
        * jeśli poprzednia asercja została sperłniona, sensowne staje się spradzenie, czy ten jeden znaleziony
        * rezultat zawiera poprawny tytuł. Metoda "get(0)" wyciąga z listy pierwszy element.*/
        String resultTitle = searchResults.get(0).findElement(By.tagName("h3")).getText();
        Assert.assertEquals(resultTitle, "Rafał Markowicz – Kolejny piękny dzień");

        /*Można też teraz sprawdzić, czy wyświetlany na zielono link pod tytułem zawiera właściwą wartość. Przy czym
        * w czasie szkolenia zdarzało się, że Google zwrwacał czasami samą nazwę domenową (bez https://) lub że w ogóle
        * tego linka nie wyświetlał. Gdy zdarzy nam się traki "flickering test", raz przechodzący a raz nie, ustalmy
        * zawsze co steruje tą logiką i zapewnijmy, że test zawsze wtkona się tak samo. Jeśli nie mamy wpływu na
        * przebieg testy, czyli jeśli to, czy przejdzie, będzie losowe - lepiej po prostu usunąć taką asercję.*/
        String resultDisplayLink = searchResults.get(0).findElement(By.tagName("cite")).getText();
        Assert.assertEquals(resultDisplayLink, "https://markowicz.pro");

    }

    @AfterMethod
    void tearDown() {
        driver.get().quit();
    }

}
