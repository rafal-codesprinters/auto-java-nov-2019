package driverfactory;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.net.UrlChecker;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;

public class DriverFactory {
    public static WebDriver getDriver() {

        String browser = System.getProperty("browser", System.getProperty("default-browser"));
        String gridHost = System.getProperty("grid-host");

        /*Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        proxy.setHttpProxy("proxy.net:3337");
        proxy.setSslProxy("proxy.net:3337");*/

        if (gridHost != null) {
            URL gridUrl = null;
            try {
                gridUrl = new URL(gridHost + "/wd/hub");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            switch (Browsers.valueOf(browser.trim().toUpperCase())) {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    //chromeOptions.setCapability(CapabilityType.PROXY, proxy);
                    chromeOptions.addArguments("--headless");
                    return new RemoteWebDriver(gridUrl, chromeOptions);
                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    /*firefoxOptions.setCapability(CapabilityType.PROXY, proxy);
                    firefoxOptions.addArguments("-headless");*/
                    return new RemoteWebDriver(gridUrl, firefoxOptions);
            }
        } else {
            String localBrowser = System.getenv("local-browser");
            switch (Browsers.valueOf((localBrowser != null ? localBrowser : browser).trim().toUpperCase())) {
                case CHROME:
                    return new ChromeDriver();
                case FIREFOX:
                    return new FirefoxDriver();
            }
        }
        return null;

    }
}
