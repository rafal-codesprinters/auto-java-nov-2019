package wordpresstests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import testframework.BaseTests;
import testframework.pageobjects.WpMainPage;
import testframework.pageobjects.WpNotePage;
import testframework.testdata.Comment;

public class CommentsTests extends BaseTests {

    /*Testy napisane z wykorzystaniem Page Object Pattern.
    * Więcej o tym wzorcu tutaj: https://github.com/SeleniumHQ/selenium/wiki/PageObjects*/

    @Test
    void firstNoteAllowsComments() {
        WpNotePage notePage = openLatestNote();
        Assertions.assertTrue(notePage.commentsAreAllowed());
    }

    @Test
    void canPostCommentsToNote() {
        /*Dzieki użyciu klasy Comment nie ma konieczności wypisywania za każdym razem w kolejnych metodach poniżej
        * argumentów z treścią komentarza, nazwą użytkownika i jego emailem.*/
        Comment comment = Comment.getRandomComment();
        WpNotePage notePage = openLatestNote();
        WpNotePage updatedNotePage = notePage.postComment(comment);
        Assertions.assertTrue(updatedNotePage.hasComment(comment));
    }

    private WpNotePage openLatestNote() {
        WpMainPage mainPage = WpMainPage.open(getBrowser());
        return mainPage.openLatestNote();
    }
}
