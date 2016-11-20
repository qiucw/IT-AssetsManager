/**
 * Chenwei Qiu
 * 11/8/2016
 * LoginFragment.java
 */
package qiucw.tacoma.uw.edu.tcss445team1.authenticate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutionException;

import qiucw.tacoma.uw.edu.tcss445team1.MainActivity;
import qiucw.tacoma.uw.edu.tcss445team1.R;

/**
 *  This class represents the fragment which is used for user to log in
 */
public class LoginFragment extends Fragment {

    private final static String CHECK_USER_URL
            = "http://cssgate.insttech.washington.edu/~_450team1/checkuser.php?";
    private EditText userIdText, pwdText;
    private String current_user;

    /**
     * This is the Required empty public constructor
     */
    public LoginFragment() {

    }

    /**
     * this method create the every component in this fragment in the beginning as well as listeners
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        userIdText = (EditText) v.findViewById(R.id.userid_edit);
        pwdText = (EditText) v.findViewById(R.id.pwd_edit);



        //create listener for register button
        Button bt = (Button) v.findViewById(R.id.reg_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkNetwork()) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new RegisterFragment())
                            .addToBackStack(null)
                            .commit();
                } else {
                    Toast.makeText(getContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //create listener for sign in button
        Button signInButton = (Button) v.findViewById(R.id.login_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNetwork()) {

                    String userId = userIdText.getText().toString();
                    String pwd = pwdText.getText().toString();

                    //check if the username and password is empty
                    if (TextUtils.isEmpty(userId)) {
                        Toast.makeText(v.getContext(), "Enter username", Toast.LENGTH_SHORT).show();
                        userIdText.requestFocus();
                        return;
                    }
                    if (TextUtils.isEmpty(pwd)) {
                        Toast.makeText(v.getContext(), "Enter password", Toast.LENGTH_SHORT).show();
                        pwdText.requestFocus();
                        return;
                    }

                    CheckUserTask task = new CheckUserTask();
                    task.execute(new String[]{buildUserURL(v).toString()});

                    //get the result of task
                    String result = "";
                    try {
                        result= task.get();
                    } catch (InterruptedException e) {e.printStackTrace();
                    } catch (ExecutionException e) {e.printStackTrace();}

                    if (result.equals("success")){
                        Toast.makeText(v.getContext(), "Login successfully"
                                , Toast.LENGTH_SHORT)
                                .show();
                        current_user = userIdText.getText().toString();
                        login();
                    } else {
                        Toast.makeText(v.getContext(), "Either username or password is wrong"
                                , Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    Toast.makeText(getContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }

    public boolean checkNetwork(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else{
            return false;
        }
    }

    //go to the main activity if log in successfully
    public void login() {
        Intent i = new Intent(getActivity(), MainActivity.class);
        i.putExtra("username", current_user);
        getActivity().finish();
        startActivity(i);
    }

    //build the url to use php file
    private String buildUserURL(View v) {
        StringBuilder sb = new StringBuilder(CHECK_USER_URL);
        try {
            String username = userIdText.getText().toString();
            sb.append("username=");
            sb.append(URLEncoder.encode(username, "UTF-8"));

            String password = pwdText.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));
        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return sb.toString();
    }

    //execute the url
    private class CheckUserTask extends AsyncTask<String, Void, String> {

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
