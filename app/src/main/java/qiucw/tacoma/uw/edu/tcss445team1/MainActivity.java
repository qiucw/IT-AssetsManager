/**
 * Chenwei Qiu
 * 11/8/2016
 * MainActivity.java
 */
package qiucw.tacoma.uw.edu.tcss445team1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import qiucw.tacoma.uw.edu.tcss445team1.authenticate.SignInActivity;


/**
 *  This class represents the activity for the main page
 */
public class MainActivity extends AppCompatActivity {

    private String current_user;
    private SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        current_user = extras.getString("username");
        Button startButton = (Button) findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, GameActivity.class);
                i.putExtra("username", current_user);
                finish();
                startActivity(i);
            }
        });
        Button scoreButton = (Button) findViewById(R.id.score_button);
        scoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ScoreActivity.class);
                i.putExtra("username", current_user);
                startActivity(i);
            }
        });
    }

    //create the option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_signin, menu);
        return true;
    }

    /**
     * create the action for the option menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {

            prefs = getSharedPreferences(getString(R.string.ShareP)
                    , Context.MODE_PRIVATE);
            prefs.edit().putBoolean("loggedin", false)
                    .putString("username", null)
                    .commit();
            Intent i = new Intent(this, SignInActivity.class);
            startActivity(i);
            finish();
            return true;
        }
        return false;
    }
}
