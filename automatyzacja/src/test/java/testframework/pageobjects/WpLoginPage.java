package testframework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import testframework.testdata.User;

import java.util.List;

public class WpLoginPage extends WpPage {
    /*Przykład użycia PageFactory do znalezienia listy elementów.
    * Więcej o PageFactory: https://github.com/SeleniumHQ/selenium/wiki/PageFactory*/
    @FindAll({@FindBy(id = ("login_error"))}) private List<WebElement> loginError;

    private static final String LOGIN_BASE_URL = "http://www.automation.markowicz.pro/wp-login.php";
    private static final By LOCATOR_LOGIN_PAGE_BODY = By.cssSelector("body.login");
    private static final By LOCATOR_USER_BOX = By.id("user_login");
    private static final By LOCATOR_PASSWORD_BOX = By.id("user_pass");
    private static final By LOCATOR_LOGIN_BUTTON = By.id("wp-submit");

    /*Konstruktor*/
    WpLoginPage(WebDriver driver) {
        /*Konieczne jest wywołanie konstruktora klasy bazowej WpPage metodą "super(...)".*/
        super(driver);
        /*Na wszelki wypadek sprawdzamy, czy właściwa strona się załadowała - dzięki temu w teście poleci wyjątek,
         * gdyby Page Object nie pasował do tego, co rzeczywiście wyświetliło się na stronie w przeglądarce. Dodatkowo
         * spowoduje to, że nie nastąpi próba wykonania dalszych działań nim tag "body" się nie załaduje na stronie.*/
        waitForElementPresence(LOCATOR_LOGIN_PAGE_BODY);
    }

    /*Poniżej metody publiczne, do użycia bezpośrednio w teście.*/
    public static WpLoginPage open(WebDriver driver) {
        driver.get(LOGIN_BASE_URL);
        return new WpLoginPage(driver);
    }

    /*Zamiast dwóch metow "logIn(...)" oraz "attemptLogin(...)" Możnaby zrobić jedną metodę, przy czym wtedy
    * musiałaby ona zwracać obiekt klasy WpPage. Konieczna byłaby też refaktoryzacja tej klasy (bo obecnie WpPage jest
    * klasą abstrakcyjną i nie można tworzyć obiektów bezpośrednio tej klasy, a jedynie tych, które po niej dziedziczą
    * i nie są abstrakcyjne.
    *
    * Refactoringu tego możnaby uniknąć, choć to już byłoby nieeleganckie. Możnaby w sygnaturze metody do logowania
    * zwracać WpPage, a w ciele metody zwracać odpowiednio WpAdminPage jeśli się uda zalogować, lub WpLoginPage, jeśli
    * logowanie się nie uda.
    *
    * Poniżej przykład implementacji metody z użyciem ternary operatora sprawdzającego, czy użytkownik
    * jest zalogowany (metoda "userIsLoggedIn()" musi być wywołana na obiekcie klasy WpPage, więc możemy to zrobić
    * posługując się słówkiem kluczowym "this".
    *
    * W klasie LoginTests pokazany jest przykad użycia metody "userLogin(...)".*/

    public WpPage userLogin (User user) {
        fillOutLoginFormAndSubmit(user);
        return (this.userIsLoggedIn() ? new WpAdminPage(driver) : new WpLoginPage(driver));
    }

    public WpAdminPage logIn(User user) {
        fillOutLoginFormAndSubmit(user);
        return new WpAdminPage(driver);
    }

    public WpLoginPage attemptLogin(User user) {
        fillOutLoginFormAndSubmit(user);
        return new WpLoginPage(driver);
    }

    public boolean loginErrorIsDisplayed() {
        /*Przykład użycia listy elementów zwróconej poprzez PageFactory - po prostu używamy nazwy i PageFactory buduje
        * dla nas listę.*/
        return loginError.size() == 1;
    }

    /*Poniżej metody prywatne - przy pomomcy których zaimplementowane zostały metody publiczne. Tych nie da się użyć
     * w teście bezpośrednio, co gwarantuje separację implementacji Page Objectu od jego logiki.*/
    private void fillOutLoginFormAndSubmit(User user) {
        fillOutUserBox(user);
        fillOutPasswordBox(user);
        WebElement submit = driver.findElement(LOCATOR_LOGIN_BUTTON);
        submit.click();
    }

    private void fillOutPasswordBox(User user) {
        WebElement passwordBox = driver.findElement(LOCATOR_PASSWORD_BOX);
        passwordBox.click();
        passwordBox.sendKeys(user.getPassword());
    }

    private void fillOutUserBox(User user) {
        WebElement userBox = driver.findElement(LOCATOR_USER_BOX);
        userBox.click();
        userBox.sendKeys(user.getUserName());
    }
}
