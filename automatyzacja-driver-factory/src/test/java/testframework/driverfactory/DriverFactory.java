package testframework.driverfactory;

import org.openqa.selenium.WebDriver;

import java.net.URL;

public abstract class DriverFactory {
    /*Ta klasa daje nam warstwę abstrakcji, dzięki której dodanie nowego typu drivera jest prostwsze i nie wymaga
    * żadnej zmiany w testach ani innych istniejących klasach. Żeby dodać nowy typ drivera, trzeba:
    *
    * 1. Dodać nową wartość enuma Browsers
    * 2. Dodać nową klasę asbtrakcyjną wzorując się np. na klasie DriverManagerChrome
    * 3. Dodać kolejny casedo instrukcji switch poniżej
    *
    * */

    /*Metoda "getDrtiver(...)" ma zwrócić instancję drivera dla wybranej przeglądarki. Jeśli gridURL nie jest nullem,
    * uruchomiony zostanie zdalny wrebdriver.*/
    public static WebDriver getDriver(Browsers browserType, URL gridUrl) {
        switch (browserType) {
            case CHROME:
                return DriverManagerChrome.getDriver(gridUrl);
            case FIREFOX:
                return DriverManagerFirefox.getDriver(gridUrl);
            case EDGE:
                return DriverManagerEdge.getDriver(gridUrl);
            case MSIE:
                return DriverManagerMsie.getDriver(gridUrl);
            default:
                return null;
        }
    }
}
