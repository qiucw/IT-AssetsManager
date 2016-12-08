package qiucw.tacoma.uw.edu.tcss445team1.authenticate;

/**
 * This is a account class including the username and password
 */

public class Account {

    private String username;
    private String password;

    /**
     * This is the constructor.
     * @param username the username for the account
     * @param password the password for the account
     */
    public Account(String username, String password){
        if (isValid(username)){
            this.username = username;
        } else{
            throw new IllegalArgumentException("Invalid username: at least 6 characters");
        }
        if (isValid(password)){
            this.password = password;
        }else{
            throw new IllegalArgumentException("Invalid password:  at least 6 characters");
        }
    }

    /**
     * this method check if the sting is valid
     * @param string the string that you want to check
     * @return true if the string is not null and at least 6 characters
     */
    public static boolean isValid(String string) {
        return string != null && string.length() >= 6;
    }

    /**
     * getter for the username
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * getter for the password
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}
