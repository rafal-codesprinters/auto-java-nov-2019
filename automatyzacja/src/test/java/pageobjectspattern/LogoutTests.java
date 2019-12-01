package pageobjectspattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testframework.BaseTests;
import testframework.pageobjects.WpAdminPage;
import testframework.pageobjects.WpLoginPage;
import testframework.testdata.User;

public class LogoutTests extends BaseTests {

    @Test
    void userCanLogOut() {
        User admin = User.getAdminUser();
        WpLoginPage loginPage = WpLoginPage.open(getBrowser());
        WpAdminPage adminPage = loginPage.logIn(admin);
        WpLoginPage loggedOutPage = adminPage.logOut();
        Assertions.assertFalse(loggedOutPage.userIsLoggedIn());
        WpLoginPage redirectPage = adminPage.openWithoutLogin();
        Assertions.assertTrue(redirectPage.requiresLogin());
    }

}
