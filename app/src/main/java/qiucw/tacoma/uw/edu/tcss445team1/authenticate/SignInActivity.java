/**
 * Chenwei Qiu
 * 11/8/2016
 * SignInActivity.java
 */
package qiucw.tacoma.uw.edu.tcss445team1.authenticate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import qiucw.tacoma.uw.edu.tcss445team1.R;

/**
 *  This class represents the activity for login fragment and register fragment
 */
public class SignInActivity extends AppCompatActivity{

    /**
     * this method create the every component in the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new LoginFragment() )
                .commit();
    }
}
