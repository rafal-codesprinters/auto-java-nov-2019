package testframework;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import testframework.driverfactory.DriverFactory;

public class BaseTests {

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

    protected WebDriver getBrowser() {
        return driver;
    }

}
