package pageobjectspattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import testframework.pageobjects.WpAdminPage;
import testframework.pageobjects.WpLoginPage;
import testframework.BaseTests;
import testframework.pageobjects.WpPage;
import testframework.testdata.User;

import java.util.stream.Stream;

public class LoginTests extends BaseTests {

    /*Testy napisane z wykorzystaniem Page Object Pattern.
     * Więcej o tym wzorcu tutaj: https://github.com/SeleniumHQ/selenium/wiki/PageObjects*/

    @Test
    void canLoginWithValidCredentials() {
        /*Dzięki użyciu klasy User nie jest konieczne przekazywanie wprost w metodach poniżej jako argumentów nazwy
        * użytkownika i hasła.*/
        User admin = User.getAdminUser();
        WpLoginPage loginPage = WpLoginPage.open(getBrowser());
        /*Metoda "logIn(...)" służy do udanego logowania, więc zwraca obiekt klasy WpAdminPage.*/
        WpAdminPage adminPage = loginPage.logIn(admin);
        /*Metoda "userIsLoggedIn()" jest na tyle generyczna, że zdefiniowana została w klasie bazowej WpPage (bo jest
        * sens jej użycia na dowolnej stronie). W tym teście używamy jej na obiekcie klasy WpAdminPage ale z racji
        * polimorfizmu obiekt klasy WpAdminPage jest także obiektem klasy WpPage.*/
        Assertions.assertTrue(adminPage.userIsLoggedIn());
    }

    /*Ten test wykona się trzykrotnie dla różnego zestawu danych dostarczonych poprzez metodę wskazaną w anotacji
    * @MethodSource. Dla wszystkich przypadków oczekujemy takigo samego zachowania: że nie uda się zalogować.*/

    @ParameterizedTest
    @MethodSource("provideInvalidCredentials")
    void cannotLoginWithInvalidCredentials(User user) {
        WpLoginPage loginPage = WpLoginPage.open(getBrowser());
        /*Metoda "attemptLogin(...)" służy do nieudanego logowania, więc zwraca obiekt klasy WpLoginPage (ponieważ
        * wyświetla sie ponownie odświeżona strona logowania).*/
        WpLoginPage failedLoginPage = loginPage.attemptLogin(user);
        Assertions.assertFalse(failedLoginPage.userIsLoggedIn());
        Assertions.assertTrue(failedLoginPage.loginErrorIsDisplayed());
    }

    @Test
    void cannotLoginWithoutCredentials() {
        /*Przykład nieeleganckiego rozwiązania z jedną generyczną metodą do logowani "userLogin(...)".*/
        User user = new User();
        user.setUserName("");
        user.setPassword("");
        WpLoginPage loginPage = WpLoginPage.open(getBrowser());
        /*Ponieważ "userLogin(...)" zwraca obiekt klasy WpPage konieczne jest jego rzutowanie na WpLoginPage aby możliwe
        * było skorzystanie z metody "loginErrorIsDisplayed()" zdefiniowanej w WpLoginPage.*/
        WpLoginPage failedLoginPage = (WpLoginPage) loginPage.userLogin(user);
        Assertions.assertFalse(failedLoginPage.userIsLoggedIn());
        Assertions.assertTrue(failedLoginPage.loginErrorIsDisplayed());
    }

    private static Stream<Arguments> provideInvalidCredentials() {
        /*Metoda ta zwraca stream obiektów klasy Argument (oczekiwanych przez JUnita). Pierwszy będzie zawierał
        * obiekt klasy User (zdefiniowanej w package "testdata") i reprezentujący admina strony z niepoprawnym hasłem.*/
        User adminInvalidPass = User.getAdminUser();
        adminInvalidPass.setPassword("invalidpassword");
        /*Drugi obiekt clasy Argument to również admin, ale z pustym hasłem.*/
        User adminBlankPass = User.getAdminUser();
        adminBlankPass.setPassword("");
        /*Trzeci obiekt clasy Argument zawierał będzie losowo wygenerowanego użytkownika.*/
        return Stream.of(
                Arguments.of(adminInvalidPass),
                Arguments.of(adminBlankPass),
                Arguments.of(User.randomUser())
        );
    }
}
