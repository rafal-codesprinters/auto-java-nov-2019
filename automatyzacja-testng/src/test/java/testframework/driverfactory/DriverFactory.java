package testframework.driverfactory;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class DriverFactory {
    public static WebDriver getDriver() {

        /*Jeśli zmienna systemowa "browser" okaże się nullerm, "default-browser" zostanie użyty (jest zdefniowany
        * w pom.xml).*/
        String browser = System.getProperty("browser", System.getProperty("default-browser"));
        String gridHost = System.getProperty("grid-host");

        /*Poniżej przykład konfigurowania proxy:*/
        /*Proxy proxy = new Proxy();
        proxy.setProxyType(Proxy.ProxyType.MANUAL);
        proxy.setHttpProxy("proxy.net:3337");
        proxy.setSslProxy("proxy.net:3337");*/

        if (gridHost != null) {
            /*Jeśli określony został grid host (zmienna systemowa "grid-host") uruchomiony zostanie zdalny driver.
            * Przykładowe pliki konfiguracyjne Selenium Grid znajdują się w repozytorium w katologu "grid".
            * Więcej na temat Selenium Grid: https://selenium.dev/documentation/en/grid/*/
            URL gridUrl = null;
            try {
                gridUrl = new URL(gridHost + "/wd/hub");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            switch (Browsers.valueOf(browser.trim().toUpperCase())) {
                case CHROME:
                    ChromeOptions chromeOptions = new ChromeOptions();
                    /*Poniżej przykład użycia proxy i uruchomienia Chrome w trybie headless:*/
                    //chromeOptions.setCapability(CapabilityType.PROXY, proxy);
                    //chromeOptions.addArguments("--headless");
                    return new RemoteWebDriver(gridUrl, chromeOptions);
                case FIREFOX:
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    /*Poniżej przykład użycia proxy i uruchomienia Firefoxa w trybie headless:*/
                    //firefoxOptions.setCapability(CapabilityType.PROXY, proxy);
                    //firefoxOptions.addArguments("-headless");
                    return new RemoteWebDriver(gridUrl, firefoxOptions);
            }
        } else {
            /*Jeśli nie podany został grid host, uruchamiona zostanie lkalna przeglądarka. Jeśli w IntelliJ lub systemie
            * określono zmienną "local-browser", wtedy ona zostaje użyta, a jeśli nie, brany jest "default-browser"
            * (zdefiniowany w pom.xml).*/
            String localBrowser = System.getenv("local-browser");
            switch (Browsers.valueOf((localBrowser != null ? localBrowser : browser).trim().toUpperCase())) {
                case CHROME:
                    /*Uruchmienie Chrome lokalnie.
                    * Opis jak używać ChromeDrivera: https://chromedriver.chromium.org/home*/
                    return new ChromeDriver();
                case FIREFOX:
                    /*Uruchmienie Firefox lokalnie.
                    * Opis jak używać FirefoxFrivera: https://github.com/mozilla/geckodriver*/
                    return new FirefoxDriver();
                case EDGE:
                    /*Uruchmienie Edge lokalnie.
                    * Opis jak używać Edge drivera: https://developer.microsoft.com/en-us/microsoft-edge/tools/webdriver/*/
                    return new EdgeDriver();
                case MSIE:
                    /*Uruchmienie Internet Explorera lokalnie.
                    * Opis jak używać IE Drivera: https://github.com/SeleniumHQ/selenium/wiki/InternetExplorerDriver*/
                    return new InternetExplorerDriver();
            }
        }
        return null;

    }
}
