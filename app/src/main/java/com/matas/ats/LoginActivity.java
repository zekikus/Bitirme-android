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
import com.matas.ats.adapters.CommonMethods;
import com.matas.ats.models.Kullanici;
import com.matas.ats.network.ATSRestClient;
import com.matas.ats.storage.SaveSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private Button sign_in;
    private EditText username;
    private EditText password;
    private JSONArray jsonArray;
    private JSONObject sonuc;

    SaveSharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = new SaveSharedPreferences(getApplicationContext());

        initialize();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText() + "";
                String passwd = password.getText() + "";
                checkAuth("checkAuth/username/" + userName + "/password/" + passwd);
            }
        });
    }

    public void initialize(){
        sign_in = (Button)findViewById(R.id.sign_in);
        username = (EditText)findViewById(R.id.email);
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

    private void checkAuth(String sorgu) {


        ATSRestClient.post(getApplicationContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("results");

                    if (!jsonArray.getJSONObject(0).has("result")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            sonuc = jsonArray.getJSONObject(i);
                            prefs.setUserName(sonuc.getString("kullaniciAdi"));
                            prefs.setUserBirimID("" + sonuc.getInt("birimID"));
                            String fullName = sonuc.getString("ad") + " " + sonuc.getString("soyad");
                            prefs.setFullName(fullName);
                            Log.e("Test",""+sonuc.getInt("id"));
                            prefs.setUserID("" + sonuc.getInt("id"));

                        }
                        sendToken(Integer.parseInt(prefs.getUserID()));
                        Toast.makeText(getApplicationContext(),prefs.getFullName(),Toast.LENGTH_LONG).show();
                        Intent endStart = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(endStart);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),R.string.no_result,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(),R.string.no_result,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) {
                    Log.e(TAG, "Error getting user information :" + errorResponse.toString());
                }
            }
        });
    }

    private void sendToken(int userID) {
        Log.e("UserID",""+userID);

        ATSRestClient.post(getApplicationContext(), "setToken/id/"+userID+"/token/"+prefs.getUserToken(), null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONArray dizi = response.getJSONArray("results");
                    JSONObject obje = dizi.getJSONObject(0);
                    Toast.makeText(getApplicationContext(), "" + obje.get("ad"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                if (errorResponse != null) {
                    Log.e(TAG, "Error getting user information :" + errorResponse.toString());
                }
            }
        });
    }


    private void sendNotification() {

        RequestParams params = new RequestParams();

        ATSRestClient.post(getApplicationContext(), "send.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {

                    JSONArray dizi = response.getJSONArray("results");
                    JSONObject obje = dizi.getJSONObject(0);
                    Toast.makeText(getApplicationContext(), "" + obje.get("ad"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                if (errorResponse != null) {
                    Log.e(TAG, "Error getting user information :" + errorResponse.toString());
                }
            }
        });
    }

}
