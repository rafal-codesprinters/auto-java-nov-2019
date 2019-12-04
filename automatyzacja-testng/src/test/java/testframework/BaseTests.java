package testframework;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import testframework.driverfactory.DriverFactory;

public class BaseTests {

    /*Pole "driver" musi być zdeklarowane jako ThreadLocal (można dokonać konwersji w IntelliJ jak się wybierze
     * możliwe akcje dla zmiennej lokalnej "driver".*/
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();

    /*Odpowiednik @BeforeEach w JUnit5 - ale uwaga: jeśli zdeklarowany w klasie bazowej, bez jawnego oznaczenia metody
    * jako publiczna (public) testy napisane w klasach pochodnych wykonują się bez wywołania tej metody...*/
    @BeforeMethod
    public void setup() {
        /*Skoro zmienna "driver" jest różna per wątek, trzeba ją ustawić (metodą "set(...)") dla wątku. Sama klasa
         * DriverFactory nie wymaga żadnej adaptacji, ponieważ ona zwraca za każdym razem nowy obiekt drivera, który
         * zapisujemy w referencji będącej lokalną zmienną typu ThreadLocal.*/
        driver.set(DriverFactory.getDriver());
        /*W klasie bazowej, gdzie "driver" to zmienna ThreadLocal, każde użycie musi być od teraz poprzedzone zawołaniem
         * metody "get()" pobierającej instancję związaną z bieżącym wątkiem.*/
        driver.get().manage().window().maximize();
    }

    /*Odpowiednik @AfterEach w Junit5 przy czym podobnie jak dla @BeforeMethod: metoda musi być publiczna jeśli ma się
    * wykonywać przed metodami testowymi w klasie pochodnej.*/
    @AfterMethod
    public void cleanup() {
        /*Znów: aby zamknąć własciwą przeglądarkę i zakończyć właściwy proces drivera, trzeba dla "driver" uzyć najpierw
         * metody "get()".*/
        driver.get().quit();
    }

    /*Metoda pozwalająca w testach uniknąć tworzenia drivera - zamiast tego pojawia się dużo bardziej zrozumiała metoda
    * "getBrowser()".*/
    protected WebDriver getBrowser() {
        /*Dokładnie to samo tutaj - "get()" zwraca instancję "driver" związaną z bieżącym wątkiem.*/
        return driver.get();
    }

}
