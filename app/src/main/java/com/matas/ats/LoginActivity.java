package com.matas.ats;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.matas.ats.network.ATSRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Button sign_in;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialize();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sendEmailPasswordToServer();
                Intent endStart = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(endStart);
                finish();
            }
        });
    }

    public void initialize(){
        sign_in = (Button)findViewById(R.id.sign_in);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void sendEmailPasswordToServer() {

        RequestParams params = new RequestParams();
        /*params.put("username", mUserNameView.getText().toString()+"");
        params.put("password", mPasswordView.getText().toString()+"");
*/
        ATSRestClient.post(getApplicationContext(), "getAllSehir", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONArray dizi = response.getJSONArray("results");
                    JSONObject obje = dizi.getJSONObject(0);
                    Toast.makeText(getApplicationContext(), "" + obje.get("ad"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.e("response = ",response.toString());
/*
                mProgressView.setVisibility(View.GONE);
                sharedPreferences.setUserPassword(mPasswordView.getText().toString());
                sharedPreferences.setUserName(mUserNameView.getText().toString());
                sharedPreferences.setLogin(true);
*/
                try {

                    boolean correctPassword = response.getBoolean("result");
                    //getEmployeeInfo();
                    Log.e("correctPassword = ",correctPassword+"");
                    if (correctPassword) {

  //                      getEmployeeInfo();
                        //JSONObject userJSON = response.getJSONObject("status_message");
                        //Log.e("userJSON = ",userJSON.toString());
                        /*
                        mProgressView.setVisibility(View.GONE);
                        //sharedPreferences.setLoggedInUserInformationJSONString(userJSON.toString());
                        sharedPreferences.setUserPassword(mPasswordView.getText().toString());
                        sharedPreferences.setUserName(mUserNameView.getText().toString());
                        sharedPreferences.setLastReportDay("0-0");
                        sharedPreferences.setLogin(true);

                        //mUserNameView.setText("");
                        //mPasswordView.setText("");

                        Toast.makeText(getApplicationContext(), getString(R.string.strLoginComplete), Toast.LENGTH_SHORT).show();

                        Intent openMainMenuActivity = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(openMainMenuActivity);
                        finish();*/

                    } else {

                        Toast.makeText(getApplicationContext(), "Hatalı parola girdiniz..", Toast.LENGTH_SHORT).show();
                        //showDialog(getResources().getString(R.string.strWarning), response.getString("status_message"), getResources().getString(R.string.strOkay));

                    }

                } catch (Exception e) {

                    e.printStackTrace();
                    /*getEmployeeInfo();

                    sharedPreferences.setUserPassword(mPasswordView.getText().toString());
                    sharedPreferences.setUserName(mUserNameView.getText().toString());
                    sharedPreferences.setLogin(true);
                    sharedPreferences.setLastReportDay("0-0");
                    Toast.makeText(getApplicationContext(), getString(R.string.strLoginComplete), Toast.LENGTH_SHORT).show();

                    Intent openMainMenuActivity = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(openMainMenuActivity);
                    finish();
*/
                    //mProgressView.setVisibility(View.GONE);
                    //showDialog(getResources().getString(R.string.strWarning), getResources().getString(R.string.strServerError), getResources().getString(R.string.strOkay));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
/*
                String message = getResources().getString(R.string.strServerError);

                //try to parse server message
                try {
                    message = errorResponse.getString("status_message");
                } catch (Exception e) {
                }*/

                //mProgressView.setVisibility(View.GONE);

                if (errorResponse != null) {
                    Log.e(TAG, "Error getting user information :" + errorResponse.toString());
                }
            }
        });
    }

}
