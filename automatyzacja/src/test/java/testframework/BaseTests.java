package testframework;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import testframework.driverfactory.DriverFactory;

public class BaseTests {

    /*Klasa bazowa testów, w której inicjalizowany jest driver.*/

    WebDriver driver;

    @BeforeEach
    void setup() {
        driver = DriverFactory.getDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    void cleanup() {
        driver.quit();
    }

    /*Metoda pozwalająca w testach uniknąć tworzenia drivera - zamiast tego pojawia się dużo bardziej zrozumiała metoda
    * "getBrowser()".*/
    protected WebDriver getBrowser() {
        return driver;
    }

}
