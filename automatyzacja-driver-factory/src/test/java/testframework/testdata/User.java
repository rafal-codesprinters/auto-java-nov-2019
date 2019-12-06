package testframework.testdata;

import java.util.UUID;

public class User {

    /*Klasa wykorzystywana w testach do logowania użytkowników.*/

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String userName;
    private String password;

    public static User getAdminUser() {
        /*Dzięki tej metodzie można dane do logowania admina trzymać w jednym miejscu. Natomiast normalnie nie powinno
        * się w ogóle takich danych w repozytorium trzymać - tylko zapisywać je w zmiennych środowiskowych i stamtąd
        * pobierać.*/
        User admin = new User();
        admin.setUserName("jan-automatyczny@gmail.com");
        admin.setPassword("C0d3$pr1nt3r$");
        return admin;
    }

    public static User randomUser(){
        /*Ta metoda generuje użytkownika z losowym emailem i hasłem - do testów nieudanego logowania.*/
        User user = new User();
        user.setUserName(UUID.randomUUID().toString() + "@test.domain");
        user.setPassword(UUID.randomUUID().toString());
        return user;
    }

}
