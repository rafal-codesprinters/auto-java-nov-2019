package wordpresstests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testframework.BaseTests;
import testframework.pageobjects.WpAdminPage;
import testframework.pageobjects.WpLoginPage;
import testframework.testdata.User;

public class LogoutTests extends BaseTests {

    @Test
    void userCanLogOut() {
        /*Uzycie klasy User pozwala uniknąć przekazywania nazwy użytkownika i hasła w metodach Page Objectów. */
        User admin = User.getAdminUser();
        WpLoginPage loginPage = WpLoginPage.open(getBrowser());
        WpAdminPage adminPage = loginPage.logIn(admin);
        /*Wylogowanie kończy się nawigacją do strony logowania - ale nie możemy użyć wcześniejszego obiektu WpLoginPage
        * ponieważ to nie ta sama strona - dlatego zapisujemy referencję do strony wyświetlonej po wylogowaniu jako
        * nowy obiekt WpLoginPage.*/
        WpLoginPage loggedOutPage = adminPage.logOut();
        Assertions.assertFalse(loggedOutPage.userIsLoggedIn());
        /*Przeglądarka już nie wyświtla strony, która reprezentowana jest w obiekcie "adminPage" w tym teście. Natomiast
        * mogę go w teście użyć wciąż, bo jako obiekt Javy wciąż istnieje. W szczególności mogę użyć go do sprawdzenia,
        * co by się stało, gdybym próbował otworzyć stronę administracyjną nie będąc zalogowabym - do tego służy metoda
        * "openWithourLogin()". Ta próba kończy się przekierowaniem na stronę logowania, reprezentowaną przez kolejny
        * obiekt klasy WpLoginPage, tu nazwany "redirectPage".*/
        WpLoginPage redirectPage = adminPage.openWithoutLogin();
        Assertions.assertTrue(redirectPage.requiresLogin());
    }

}
