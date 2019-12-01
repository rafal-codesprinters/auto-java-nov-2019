package testframework.testdata;

import java.util.UUID;

public class Comment {

    /*Klasa wykorzystywana w testach komentarzy na blogu.*/

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    private String text;
    private String name;
    private String mail;

    public static Comment getRandomComment() {
        /*Ta metoda generuje losowe dane, których można użyć do testów komentarzy.*/
        Comment comment = new Comment();
        comment.setText(UUID.randomUUID().toString());
        comment.setName(UUID.randomUUID().toString());
        comment.setMail(comment.getName() + "@test.domain");
        return comment;
    }

}
