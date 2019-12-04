package testframework.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testframework.testdata.Comment;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WpNotePage extends WpPage {
    private static final By LOCATOR_COMMENT_FORM = By.id("commentform");
    private static final By LOCATOR_NOTE_PAGE_BODY = By.cssSelector("body.single-post");
    private static final By LOCATOR_COMMENT_AUTHOR = By.cssSelector(".comment-author .fn");
    private static final By LOCATOR_COMMENT_TEXT = By.cssSelector(".comment-content");
    private static final By LOCATOR_COMMENT_BODY = By.cssSelector("article.comment-body");
    private static final By LOCATOR_COMMENT_TEXT_BOX = By.id("comment");
    private static final By LOCATION_COMMENT_NAME_BOX = By.id("author");
    private static final By LOCATION_COMMENT_MAIL_BOX = By.id("email");
    private static final By LOCATION_COMMENT_SUBMIT = By.id("submit");

    /*Konstruktor*/
    WpNotePage(WebDriver driver) {
        /*Konieczne jest wywołanie konstruktora klasy bazowej WpPage metodą "super(...)".*/
        super(driver);
        /*Na wszelki wypadek sprawdzamy, czy właściwa strona się załadowała - dzięki temu w teście poleci wyjątek,
         * gdyby Page Object nie pasował do tego, co rzeczywiście wyświetliło się na stronie w przeglądarce. Dodatkowo
         * spowoduje to, że nie nastąpi próba wykonania dalszych działań nim tag "body" się nie załaduje na stronie.*/
        waitForElementPresence(LOCATOR_NOTE_PAGE_BODY);
    }

    /*Poniżej metody publiczne, do użycia bezpośrednio w teście.*/
    public boolean commentsAreAllowed() {
        waitForElementPresence(LOCATOR_COMMENT_FORM);
        return driver.findElements(LOCATOR_COMMENT_FORM).size() == 1;
    }

    public WpNotePage postComment(Comment comment) {
        WebElement commentForm = driver.findElement(LOCATOR_COMMENT_FORM);

        fillOutCommentText(comment, commentForm);
        fillOutName(comment);
        fillOutEmail(comment);

        WebElement commentSubmit = driver.findElement(LOCATION_COMMENT_SUBMIT);
        commentSubmit.click();

        return new WpNotePage(driver);
    }

    public boolean hasComment(Comment comment) {
        /*Te dwa predykaty zostaną użyte w metodzie "filter()" przy przeszukiwaniu listy elementów poniżej.*/
        Predicate<WebElement> nameFilter = c -> c.findElement(LOCATOR_COMMENT_AUTHOR).getText().equals(comment.getName());
        Predicate<WebElement> textFilter = c -> c.findElement(LOCATOR_COMMENT_TEXT).getText().equals(comment.getText());

        /*Metoda "and(...)" pozwala zrobić warunek logiczny predykatów. Alternatywnie możnaby zapisać ten sam warunek
         * logiczny w ten sposób bez użycia zdefiniowanych predykatów:
         *
         *   List<WebElement> comments = driver.findElements(LOCATOR_COMMENT_BODY).stream()
         *       .filter(c -> c.findElement(LOCATOR_COMMENT_AUTHOR).getText().equals(comment.getName()) && c.findElement(LOCATOR_COMMENT_TEXT).getText().equals(comment.getText()))
         *       .collect(Collectors.toList());
         *
         * Jak widać jest to o wiele mniej czytelne.*/
        List<WebElement> comments = driver.findElements(LOCATOR_COMMENT_BODY).stream()
                .filter(nameFilter.and(textFilter))
                .collect(Collectors.toList());

        return comments.size() == 1;
    }

    /*Poniżej metody prywatne - przy pomomcy których zaimplementowane zostały metody publiczne. Tych nie da się użyć
    * w teście bezpośrednio, co gwarantuje separację implementacji Page Objectu od jego logiki.*/
    private void fillOutEmail(Comment comment) {
        WebElement commentMailBox = driver.findElement(LOCATION_COMMENT_MAIL_BOX);
        commentMailBox.click();
        commentMailBox.sendKeys(comment.getMail());
    }

    private void fillOutName(Comment comment) {
        WebElement commentNameBox = driver.findElement(LOCATION_COMMENT_NAME_BOX);
        commentNameBox.click();
        commentNameBox.sendKeys(comment.getName());
    }

    private void fillOutCommentText(Comment comment, WebElement commentForm) {
        WebElement commentTextBox = commentForm.findElement(LOCATOR_COMMENT_TEXT_BOX);
        commentTextBox.click();
        commentTextBox.sendKeys(comment.getText());
    }
}
