package testframework;

import testframework.driverfactory.Browsers;

abstract class BrowserSelector {
    static Browsers getBrowserType() {
        /*Metoda zwraca wartość enuma Browsers*/
        return Browsers.valueOf(
                /*Nie wszyscy lubią ternary opearator użyty poniżej, więc objaśniam logikę działania:
                *
                * - jeśli zmienna środowiskowa "local-browser" jest ustawiona, zwracana jest jej wartość
                * - jeśli zmienna środowiskowa "local-browser" nie jest ustawiona, sprawdzana jest zmienna systemowa
                *   "browser"
                * - jeśli "browser" jest ustawiona, zwracana jest jej wartość, a jeśli nie, zwracana jest wartość
                *   zmiennej systemowej "default-browser"
                *
                * Wartość wyciągnięta z którejkolwiek zmiennej jest trimowana i zamieniana na duże litery przed
                * sprawdzeniem z enumem Browsers.
                *
                * Zmienna "local-browser" może być ustawiona albo w systemie operacyjnym (zmienne środowiskowe
                * użytkownika), albo w IntelliJ w konfiguracji (Menu "Run" - "Edit Configurations...").
                *
                * Zmienna "browser" może być ustawiona albo w systemie operacyjnym (zmienne systemowe), albo może być
                * przekazana jako parametr w komendzie Maven: np. mvn -Dbrowser=chrome
                *
                * Zmienna "default-browser" jest ustawiona w POM.xml
                * */
                (System.getenv("local-browser") != null
                        ? System.getenv("local-browser")
                        : System.getProperty("browser", System.getProperty("default-browser"))
                )
                .trim()
                .toUpperCase()
        );
    }
}
