/**
 * Chenwei Qiu
 * 11/8/2016
 * MainActivity.java
 */
package qiucw.tacoma.uw.edu.tcss445team1;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import qiucw.tacoma.uw.edu.tcss445team1.authenticate.SignInActivity;

import static android.R.attr.duration;
import static qiucw.tacoma.uw.edu.tcss445team1.R.id.ball;
import static qiucw.tacoma.uw.edu.tcss445team1.R.id.stickman;


/**
 *  This class represents the activity for the main page
 *  @author Chenwei Qiu
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    private String current_user;
    private SharedPreferences prefs;
    private ImageView stickman;
    private AnimationDrawable stickmanAnimation;

    /**
     * this method create the every component in this activity in the beginning as well as listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        current_user = extras.getString("username");


        stickman = (ImageView) findViewById(R.id.stickman);
        stickman.setBackgroundResource(R.drawable.walk);
        stickmanAnimation = (AnimationDrawable) stickman.getBackground();
        stickmanAnimation.start();
        stickman.animate().translationX(1500).setDuration(10);
        stickman.animate().setInterpolator(new LinearInterpolator());
        stickman.animate().setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
             @Override
             public void onAnimationUpdate(ValueAnimator animation) {

                 if (stickman.getX() > 1500) {
                     stickman.setX(75);
                     stickman.animate().translationX(1500).setDuration(4000);
                 }
             }
         });

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

    /**
     * create the option menu
     * @param menu
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_signin, menu);
        return true;
    }

    /**
     * create the action for the option menu
     * @param item
     * @return true if any one is selected
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
