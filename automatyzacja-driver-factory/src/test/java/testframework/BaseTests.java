package testframework;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import testframework.driverfactory.DriverFactory;

public class BaseTests {

    private WebDriver driver;

    @BeforeEach
    void setupTest() {
        /*Metoda "getDriver(...)" klasy DriverFactory wymaga przekazania typu przeglądarki oraz rodzaju drivera.
        * Ponieważ nie chcemy "zaszywać" na sztywno w teście tych wartości, nasz framerowk ma klasy pomocnicze
        * BrowserSelector oraz DriverTypeSelector, które wyciągają stosowne informacje z POM.xml lub zmiennych
        * systemowych.
        *
        * Jeśli nie przeszkadza ustawienie tego na sztywno, można w teście po prostu napisać:
        *
        * driver = DriverFactory.getDriver(Browsers.CHROME, null);
        *
        * Natomiast nie ma to większego sensu, bo po co DriverFactory jeśli później konfigurowane byłoby ono zawsze
        * w jeden sposób.
        * */
        driver = DriverFactory.getDriver(BrowserSelector.getBrowserType(), DriverTypeSelector.getDriverType());
        driver.manage().window().maximize();
    }

    @AfterEach
    void cleanup() {
        driver.quit();
    }

    protected WebDriver getBrowser() {
        return driver;
    }

}
