package driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.NoSuchElementException;

public class DriverFactory {
    public static WebDriver getDriver() {
        String browser = System.getenv("local-browser");
        if (browser == null) browser = System.getProperty("browser", System.getProperty("default-browser"));
        switch (Browsers.valueOf(browser.trim().toUpperCase())) {
            case CHROME:
                return new ChromeDriver();
            case FIREFOX:
                return new FirefoxDriver();
            default:
                return null;
        }
    }
}
