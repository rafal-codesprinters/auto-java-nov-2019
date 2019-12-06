package testframework.driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

abstract class DriverManagerFirefox {
    static WebDriver getDriver(URL gridUrl) {
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        if (gridUrl != null) {
            firefoxOptions.addArguments("-headless");
            return new RemoteWebDriver(gridUrl, firefoxOptions);
        } else {
            return new FirefoxDriver(firefoxOptions);
        }
    }
}
