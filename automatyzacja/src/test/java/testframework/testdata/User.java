package testframework.testdata;

import java.util.UUID;

public class User {

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
        User admin = new User();
        admin.setUserName("jan-automatyczny@gmail.com");
        admin.setPassword("C0d3$pr1nt3r$");
        return admin;
    }

    public static User randomUser(){
        User user = new User();
        user.setUserName(UUID.randomUUID().toString() + "@test.domain");
        user.setPassword(UUID.randomUUID().toString());
        return user;
    }

}
