/**
 * Chenwei Qiu
 * 11/8/2016
 * GameoverActivity.java
 */
package qiucw.tacoma.uw.edu.tcss445team1;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 *  This class represents the activity for the page when game is over
 *  @author Chenwei Qiu
 * @version 1.0
 */
public class GameoverActivity extends AppCompatActivity {

    private final static String UPDATE_SCORE_URL
            = "http://cssgate.insttech.washington.edu/~_450team1/updatescore.php?";
    private String current_user;
    private int score;
    private Button save;

    /**
     * this method create the every component in this activity in the beginning as well as listeners
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        Bundle extras = getIntent().getExtras();
        current_user = extras.getString("username");
        score = extras.getInt("score");

        TextView tv = (TextView) findViewById(R.id.tv);
        tv.setText("Your Score is: "+ score +"\nWould you like to play again? OR Save the Score" );

        Button play = (Button) findViewById(R.id.play_button);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameoverActivity.this, GameActivity.class);
                i.putExtra("username", current_user);
                startActivity(i);
                finish();
            }
        });

        save = (Button) findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildUserURL();
                UpdateScoreTask task = new UpdateScoreTask();
                task.execute(new String[]{url.toString()});
                Toast.makeText(v.getContext(), "Your score has been saved", Toast.LENGTH_LONG)
                        .show();
                save.setClickable(false);
            }
        });

        Button share = (Button) findViewById(R.id.share_button);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        Button back = (Button) findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameoverActivity.this, MainActivity.class);
                i.putExtra("username", current_user);
                finish();
                startActivity(i);
            }
        });


    }

    /**
     * build the url that you want to execute
     * @return the url you build
     */
    private String buildUserURL() {
        StringBuilder sb = new StringBuilder(UPDATE_SCORE_URL);
        try {
            sb.append("username=");
            sb.append(URLEncoder.encode(current_user, "UTF-8"));

            sb.append("&score=");
            sb.append(URLEncoder.encode(String.valueOf(score), "UTF-8"));
        }
        catch(Exception e) {
            Toast.makeText(GameoverActivity.this, "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return sb.toString();
    }

    /**
     * go the email where you can send the email
     */
    protected void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "New Score");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "I have just got " + score + " points on Stickman! Try it!");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(GameoverActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * the class that could execute the url
     */
    private class UpdateScoreTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = "";
            HttpURLConnection urlConnection = null;
            for (String url : urls) {
                try {
                    URL urlObject = new URL(url);
                    urlConnection = (HttpURLConnection) urlObject.openConnection();
                    InputStream content = urlConnection.getInputStream();
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                    String s = "";
                    while ((s = buffer.readLine()) != null) {
                        response += s;
                    }
                } catch (Exception e) {
                    response = "Unable to add user, Reason: "+ e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
    }
}
