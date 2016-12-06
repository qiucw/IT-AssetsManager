package qiucw.tacoma.uw.edu.tcss445team1;

import org.junit.Test;

import qiucw.tacoma.uw.edu.tcss445team1.authenticate.Account;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Chenwei Qiu on 12/6/2016.
 */
public class AccountTest {
    @Test
    public void testUsername() {
        try {
            new Account("qwert", "123456");
            fail("Invalid username");
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }

    @Test
    public void testPassword() {
        try {
            new Account("qwerty", "12345");
            fail("Invalid password");
        } catch (IllegalArgumentException e) {
            e.getMessage();
        }
    }

    @Test
    public void testGetters() {
        Account account = new Account("qwerty", "123456");
        assertEquals(account.getUsername(), "qwerty");
        assertEquals(account.getPassword(), "123456");
    }


}
