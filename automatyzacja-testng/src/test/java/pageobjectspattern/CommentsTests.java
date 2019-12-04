package pageobjectspattern;

import org.testng.Assert;
import org.testng.annotations.Test;
import testframework.BaseTests;
import testframework.pageobjects.WpMainPage;
import testframework.pageobjects.WpNotePage;
import testframework.testdata.Comment;

public class CommentsTests extends BaseTests {

    /*Logika testu (z wyjątkiem samego assertu używającego innej klasy niż w JUnit5 - komentarz poniżej) jest taka sama.
     * Co więcej, da się użyć wszystkich page objectów - a to dlatego, że do nich przekazujemy na początku referencję
     * do drivera poprzez metodę helperową "getBrowser()" a obsługę wielowątkowości w oparciu o ThreadLocal mamy już
     * rozwiązaną w klasie bazowej. Również samo DriverFactory nie wymaga żadnej zmiany - bo ono zwraca po prostu nowy
     * obiekt drivera i to klasa bazowa TngBaseTest przypisuje go do odpowiedniej zmiennej driver zdeklarowanej jako
     * ThreadLocal.*/

    @Test
    void firstNoteAllowsComments() {
        WpNotePage notePage = openLatestNote();
        Assert.assertTrue(notePage.commentsAreAllowed());
    }

    @Test
    void canPostCommentsToNote() {
        /*Dzieki użyciu klasy Comment nie ma konieczności wypisywania za każdym razem w kolejnych metodach poniżej
        * argumentów z treścią komentarza, nazwą użytkownika i jego emailem.*/
        Comment comment = Comment.getRandomComment();
        WpNotePage notePage = openLatestNote();
        WpNotePage updatedNotePage = notePage.postComment(comment);
        Assert.assertTrue(updatedNotePage.hasComment(comment));
    }

    private WpNotePage openLatestNote() {
        WpMainPage mainPage = WpMainPage.open(getBrowser());
        return mainPage.openLatestNote();
    }
}
