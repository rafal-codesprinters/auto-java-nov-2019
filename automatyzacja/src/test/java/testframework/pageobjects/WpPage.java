package testframework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WpPage {
    static final String BLOG_BASE_URL = "http://www.automation.markowicz.pro";
    private static final By LOCATOR_ADMIN_BAR = By.cssSelector("body.wp-admin #wpadminbar");
    private static final By LOCATOR_LOGIN_FORM = By.id("loginform");
    final WebDriver driver;

    /*Konstruktor*/
    WpPage(WebDriver driver) {
        /*Konieczne jest przepisanie otrzymanego obiektu drivera (driver) do lokalnej zmiennej obiektu (this.driver).*/
        this.driver = driver;
        /*Inicjalizacja PageFactory - dzięki temu możliwe jest korzystanie z PageFactory w każdym z page objectów.
        * Przykład użycia dla pojedynczego elementu znajduje się w klasie WpAdminPage.
        * Przykład użycia dla listy elementów znajduje się w klasie WpLoginPage.
        * Więcej o PageFactory: https://github.com/SeleniumHQ/selenium/wiki/PageFactory*/
        PageFactory.initElements(this.driver, this);
    }

    /*Poniżej metody publiczne, do użycia bezpośrednio w teście.*/
    public boolean userIsLoggedIn() {
        return driver.findElements(LOCATOR_ADMIN_BAR).size() == 1;
    };

    public boolean requiresLogin() {
        return driver.findElements(LOCATOR_LOGIN_FORM).size() == 1;
    };

    /*Poniżej metody package-private - przy pomomcy których zaimplementowane zostały metody publiczne. Tych nie da się
    * użyć w teście bezpośrednio, co gwarantuje separację implementacji Page Objectu od jego logiki.
    *
    * Zwracam uwagę, że nie mogą być to metody prywatne, ponieważ z klasy WpPage dziedziczą pozostałe Page Objecty.
    * Dzięki trzymaniu wszystkich Page Objectów w osobnym package możliwe staje się łatwe separowanie tego, co dostępne
    * z poziomu testu.*/
    WebElement waitForElementPresence(By locator) {
        /*Oczekiwanie na pojawienie się elementu w DOM przeglądarki i zwrócenie tego elementu*/
        WebDriverWait wait = new WebDriverWait(driver, 5);
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    void waitUntilElementIsClickable(WebElement element) {
        /*Oczekiwanie aż element będzie klikalny - z przekazaniem elementu wcześniej znalezionego.*/
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(element));
    }

    WebElement waitUntilElementIsClickable(By locator) {
        /*Oczekiwanie aż element będzie klikalny połączone z wyszukaniem tego elementu po przekazanym locatorze.*/
        return new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
    }

    void hoverOverElement(WebElement element) {
        /*Najechanie kursorem myszy nad element przekazany w agrumencie metody.*/
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }


}
