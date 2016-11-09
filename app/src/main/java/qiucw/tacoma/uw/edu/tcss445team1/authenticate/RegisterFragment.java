/**
 * Chenwei Qiu
 * 11/8/2016
 * RegisterFragment.java
 */
package qiucw.tacoma.uw.edu.tcss445team1.authenticate;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import qiucw.tacoma.uw.edu.tcss445team1.R;

/**
 *  This class represents the fragment which is used for user to register
 */
public class RegisterFragment extends Fragment {

    private final static String COURSE_ADD_URL
            = "http://cssgate.insttech.washington.edu/~_450team1/adduser.php?";
    private EditText userIdText, pwdText, pwdComText;

    /**
     * This is the Required empty public constructor
     */
    public RegisterFragment() {

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
        View v = inflater.inflate(R.layout.fragment_register, container, false);
        userIdText = (EditText) v.findViewById(R.id.userid_edit);
        pwdText = (EditText) v.findViewById(R.id.pwd_edit);
        pwdComText = (EditText) v.findViewById(R.id.pwd_edit_com);

        Button bt = (Button) v.findViewById(R.id.cancel_button);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        //create listener for button
        Button signInButton = (Button) v.findViewById(R.id.reg_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = userIdText.getText().toString();
                String pwd = pwdText.getText().toString();
                String pwdCom = pwdComText.getText().toString();
                if (TextUtils.isEmpty(userId)) {
                    Toast.makeText(v.getContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                    userIdText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    Toast.makeText(v.getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                    pwdText.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(pwdCom)) {
                    Toast.makeText(v.getContext(), "Please confirm your password", Toast.LENGTH_SHORT).show();
                    pwdComText.requestFocus();
                    return;
                }
                if (!pwd.equals(pwdCom)) {
                    Toast.makeText(v.getContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    pwdComText.requestFocus();
                    return;
                }
                String url = buildUserURL(v);
                AddUserTask task = new AddUserTask();
                task.execute(new String[]{url.toString()});
            }
        });

        return v;
    }

    //build the url to use php file
    private String buildUserURL(View v) {

        StringBuilder sb = new StringBuilder(COURSE_ADD_URL);

        try {
            String username = userIdText.getText().toString();
            sb.append("username=");
            sb.append(URLEncoder.encode(username, "UTF-8"));

            String password = pwdText.getText().toString();
            sb.append("&password=");
            sb.append(URLEncoder.encode(password, "UTF-8"));

        }
        catch(Exception e) {
            Toast.makeText(v.getContext(), "Something wrong with the url" + e.getMessage(), Toast.LENGTH_LONG)
                    .show();
        }
        return sb.toString();
    }

    //execute the url
    private class AddUserTask extends AsyncTask<String, Void, String> {

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

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("success")) {
                Toast.makeText(getActivity().getApplicationContext(), "User successfully added!"
                        , Toast.LENGTH_LONG)
                        .show();
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "  Failed to add: " + result
                                +". \nThe username already exists"
                        , Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}