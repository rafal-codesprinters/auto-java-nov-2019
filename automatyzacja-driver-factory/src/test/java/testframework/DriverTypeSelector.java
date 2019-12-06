package testframework;

import java.net.MalformedURLException;
import java.net.URL;

abstract class DriverTypeSelector {
    /*Metoda zwraca URL do Grid Hosta lub null*/
    static URL getDriverType() {
        /*Zmienna "grid-host" może być ustawiona w systemie oporacyjnym (zmienne systemowe) albo przekazana jako
        * parametr Mavena, np. mvn -Dgrid-host=https://testbox.testlab.com:8889
        *
        * Na jej bazie kod poniżej buduje adres servisu Selenium Grid.
        *
        * Jeśli zmienna nie jest ustawiona, zwracany jest null, co interpretowane jest przez DriverFactory jako
        * zlecenie do uruchomienia przeglądarki lokalnie.*/
        String gridHost = System.getProperty("grid-host");
        if (gridHost != null) {
            URL gridUrl = null;
            try {
                gridUrl = new URL(gridHost + "/wd/hub");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return gridUrl;
        } else {
            return null;
        }
    }
}
