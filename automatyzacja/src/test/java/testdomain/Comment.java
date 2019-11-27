package testdomain;

import java.util.UUID;

public class Comment {


    private String text;
    private String name;
    private String mail;

    public Comment() {
        setText(UUID.randomUUID().toString());
        setName(UUID.randomUUID().toString());
        setMail(getName() + "@test.domain");
    }

    public String getText() {
        return text;
    }

    private void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    private void setMail(String mail) {
        this.mail = mail;
    }
}
