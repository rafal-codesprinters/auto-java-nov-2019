package pageobjectspattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pageobjects.WpMainPage;
import pageobjects.WpNotePage;

public class WordPressTests extends BaseTests {

    @Test
    void firstNoteAllowsComments() {
        // Arrange
        WpMainPage mainPage = WpMainPage.open(getBrowser());

        // Act
        WpNotePage notePage = mainPage.openLatestNote();

        // Assert
        Assertions.assertTrue(notePage.commentsAreAllowed());
    }

}
