package testframework.driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

abstract class DriverManagerMsie {
    static WebDriver getDriver(URL gridUrl) {
        InternetExplorerOptions msieOptions = new InternetExplorerOptions();
        if (gridUrl != null) {
            return new RemoteWebDriver(gridUrl, msieOptions);
        } else {
            return new InternetExplorerDriver(msieOptions);
        }
    }
}
