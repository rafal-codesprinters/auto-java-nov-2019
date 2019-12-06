package testframework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class WpAdminPage extends WpPage {

    /*W taki sposób wyszukuje się pojedynczy element za pomocą PageFactory.
    * Więcej o PageFactory: https://github.com/SeleniumHQ/selenium/wiki/PageFactory*/
    @FindBy(id = "wp-admin-bar-my-account") private WebElement myAccountBox;

    private static final By LOCATOR_LOGOUT_LINK = By.id("wp-admin-bar-logout");
    private static final String BLOG_ADMIN_PAGE_URL = BLOG_BASE_URL + "/wp-admin";

    /*Konstruktor*/
    WpAdminPage(WebDriver driver) {
        /*Konieczne jest wywołanie konstruktora klasy bazowej WpPage metodą "super(...)".*/
        super(driver);
        /*Na wszelki wypadek sprawdzamy, czy właściwa strona się załadowała - dzięki temu w teście poleci wyjątek,
        * gdyby Page Object nie pasował do tego, co rzeczywiście wyświetliło się na stronie w przeglądarce. Dodatkowo
        * spowoduje to, że nie nastąpi próba wykonania dalszych działań nim tag "body" się nie załaduje na stronie.*/
        waitForElementPresence(By.cssSelector("body.wp-admin"));
    }

    /*Poniżej metody publiczne, do użycia bezpośrednio w teście.*/
    public WpLoginPage openWithoutLogin() {
        driver.get(BLOG_ADMIN_PAGE_URL);
        return new WpLoginPage(driver);
    }

    public WpLoginPage logOut() {
        /*myAccountBox nie wymaga wcześniejszego znalezienia - zrobi to za nas PageFactory posługując się deklaracją
        * umieszczoną na początku ciała klasy WpAdminPage.*/
        hoverOverElement(myAccountBox);
        WebElement logoutLink = waitUntilElementIsClickable(LOCATOR_LOGOUT_LINK);
        logoutLink.click();
        return new WpLoginPage(driver);
    }
}
