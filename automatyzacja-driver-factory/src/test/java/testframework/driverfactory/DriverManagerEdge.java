package testframework.driverfactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

abstract class DriverManagerEdge {
    static WebDriver getDriver(URL gridUrl) {
        EdgeOptions edgeOptions = new EdgeOptions();
        if (gridUrl != null) {
            return new RemoteWebDriver(gridUrl, edgeOptions);
        } else {
            return new EdgeDriver(edgeOptions);
        }
    }
}
