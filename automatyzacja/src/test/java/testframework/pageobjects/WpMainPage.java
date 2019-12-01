package testframework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class WpMainPage extends WpPage{
    private static final By LOCATOR_POST_LINK = By.cssSelector("article.post .entry-title > a");
    private static final By LOCATOR_MAIN_PAGE_BODY = By.cssSelector("body.home");

    /*Konstruktor*/
    private WpMainPage(WebDriver driver) {
        /*Konieczne jest wywołanie konstruktora klasy bazowej WpPage metodą "super(...)".*/
        super(driver);
        /*Na wszelki wypadek sprawdzamy, czy właściwa strona się załadowała - dzięki temu w teście poleci wyjątek,
         * gdyby Page Object nie pasował do tego, co rzeczywiście wyświetliło się na stronie w przeglądarce. Dodatkowo
         * spowoduje to, że nie nastąpi próba wykonania dalszych działań nim tag "body" się nie załaduje na stronie.*/
        waitForElementPresence(LOCATOR_MAIN_PAGE_BODY);
    }

    /*Poniżej metody publiczne, do użycia bezpośrednio w teście.*/
    public static WpMainPage open(WebDriver driver) {
        driver.get(BLOG_BASE_URL);
        return new WpMainPage(driver);
    }

    public WpNotePage openLatestNote() {
        waitForElementPresence(LOCATOR_POST_LINK).click();
        return new WpNotePage(driver);
    }
}
