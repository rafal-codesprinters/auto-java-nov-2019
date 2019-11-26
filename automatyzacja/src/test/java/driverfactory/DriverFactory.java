package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
    public static WebDriver getDriver() {
        String defaultBrowser = System.getProperty("browser", System.getProperty("defaultBrowser")).trim().toUpperCase();
        switch (Browsers.valueOf(defaultBrowser)) {
            case CHROME:
                return new ChromeDriver();
            case FIREFOX:
                return new FirefoxDriver();
            default:
                return null;
        }
    }
}
