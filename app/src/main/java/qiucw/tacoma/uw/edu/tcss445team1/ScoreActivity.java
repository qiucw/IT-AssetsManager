/**
 * Chenwei Qiu
 * 11/8/2016
 * ScoreActivity.java
 */
package qiucw.tacoma.uw.edu.tcss445team1;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 *  This class represents the activity for the score page
 */
public class ScoreActivity extends AppCompatActivity {

    private ArrayList<String> scorelist = new ArrayList<>();
    private final static String GET_SCORE_URL
            = "http://cssgate.insttech.washington.edu/~_450team1/allscore.php?";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        GetScoreTask task = new GetScoreTask();
        task.execute(new String[]{GET_SCORE_URL.toString()});

        try {
            JSONArray jArray = new JSONArray(task.get());
            for (int i = 0; i < jArray.length(); i++) {
                JSONObject json_data = jArray.getJSONObject(i);
                StringBuilder sb = new StringBuilder();
                sb.append(json_data.getString("score"));
                sb.append(" Points    Played by            ");
                sb.append(json_data.getString("username"));
                scorelist.add(sb.toString());
            }
        }
        catch (Exception e)
        {   Log.e("", "" + e);  }

        //set adapter for list view
        ListView list=(ListView)findViewById(R.id.listview);
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, scorelist);
        list.setAdapter(arrayAdapter);
        list.setDivider(null);
    }

    //execute the url
    private class GetScoreTask extends AsyncTask<String, Void, String> {

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
                    response = "Unable to add user, Reason: " + e.getMessage();
                } finally {
                    if (urlConnection != null)
                        urlConnection.disconnect();
                }
            }
            return response;
        }
    }
}
