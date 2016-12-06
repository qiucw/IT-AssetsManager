package qiucw.tacoma.uw.edu.tcss445team1.authenticate;

/**
 * Created by Chenwei Qiu on 12/5/2016.
 */

public class Account {

    private String username;
    private String password;

    public Account(String username, String password){
        if (isValid(username)){
            this.username = username;
        } else{
            throw new IllegalArgumentException("Invalid username");
        }
        if (isValid(password)){
            this.password = password;
        }else{
            throw new IllegalArgumentException("Invalid password");
        }
    }

    public static boolean isValid(String string) {
        return string != null && string.length() >= 6;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
