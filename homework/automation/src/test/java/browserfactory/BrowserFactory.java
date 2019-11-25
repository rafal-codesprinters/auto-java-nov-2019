package browserfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public abstract class BrowserFactory {

    public static WebDriver getBrowser() {

        String browser = System.getProperty("browser", System.getProperty("defaultBrowser")).trim().toUpperCase();
        switch (Browsers.valueOf(browser)) {
            case CHROME:
                return new ChromeDriver();
            case FIREFOX:
                return new FirefoxDriver();
            case EDGE:
                return new EdgeDriver();
            case MSIE:
                return new InternetExplorerDriver();
            default:
                return null;
        }

    }

}
