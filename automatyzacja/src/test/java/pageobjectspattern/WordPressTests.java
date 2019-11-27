package pageobjectspattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pageobjects.WpMainPage;
import pageobjects.WpNotePage;
import testdomain.Comment;

public class WordPressTests extends BaseTests {

    @Test
    void firstNoteAllowsComments() {
        WpNotePage notePage = openLatestNote();
        Assertions.assertTrue(notePage.commentsAreAllowed());
    }

    @Test
    void canPostCommentsToNote() {
        Comment comment = new Comment();
        WpNotePage notePage = openLatestNote();
        WpNotePage updatedNotePage = notePage.postComment(comment);
        Assertions.assertTrue(updatedNotePage.hasComment(comment));
    }

    private WpNotePage openLatestNote() {
        WpMainPage mainPage = WpMainPage.open(getBrowser());
        return mainPage.openLatestNote();
    }

}
