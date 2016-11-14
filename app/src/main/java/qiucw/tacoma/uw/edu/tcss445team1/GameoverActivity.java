/**
 * Chenwei Qiu
 * 11/8/2016
 * GameoverActivity.java
 */
package qiucw.tacoma.uw.edu.tcss445team1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
 */
public class GameoverActivity extends AppCompatActivity {

    private final static String UPDATE_SCORE_URL
            = "http://cssgate.insttech.washington.edu/~_450team1/updatescore.php?";
    private String current_user;
    private int score;

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
        tv.setText("Your Score is: "+ score +"\nWould you like to save your score? " );

        Button save = (Button) findViewById(R.id.yes_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = buildUserURL();
                UpdateScoreTask task = new UpdateScoreTask();
                task.execute(new String[]{url.toString()});
                Toast.makeText(v.getContext(), "Your score has been saved", Toast.LENGTH_LONG)
                        .show();
            }
        });

        Button back = (Button) findViewById(R.id.no_button);
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

    //build the url to use php file
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


    //execute the url
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
