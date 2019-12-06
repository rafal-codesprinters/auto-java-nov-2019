package testframework.driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

abstract class DriverManagerChrome {
    static WebDriver getDriver(URL gridUrl) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if (gridUrl != null) {
            chromeOptions.addArguments("--headless");
            return  new RemoteWebDriver(gridUrl, chromeOptions);
        } else {
            return new ChromeDriver(chromeOptions);
        }
    }
}
