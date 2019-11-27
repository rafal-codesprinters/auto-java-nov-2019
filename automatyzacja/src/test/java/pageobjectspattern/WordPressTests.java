package pageobjectspattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pageobjects.WpMainPage;
import pageobjects.WpNotePage;

public class WordPressTests extends BaseTests {

    @Test
    void firstNoteAllowsComments() {
        WpMainPage mainPage = WpMainPage.open(getBrowser());
        WpNotePage notePage = mainPage.openLatestNote();
        Assertions.assertTrue(notePage.commentsAreAllowed());
    }

}
