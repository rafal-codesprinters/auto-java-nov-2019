package pageobjectspattern;

import driverfactory.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

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

    WebDriver getBrowser() {
        return driver;
    }

}
