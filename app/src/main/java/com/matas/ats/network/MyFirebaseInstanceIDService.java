package com.matas.ats.network;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.matas.ats.storage.SaveSharedPreferences;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{
 
    private static final String TAG = "MyFirebaseIIDService";
    SaveSharedPreferences prefs;

    @Override
    public void onTokenRefresh() {

        prefs = new SaveSharedPreferences(getApplicationContext());

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Token: " + token);

        prefs.setUserToken(token);
 
        //sendRegistrationToServer(token);
    }
 
    private void sendRegistrationToServer(String token) {
        // token'ı servise gönderme işlemlerini bu methodda yapmalısınız

        ATSRestClient.post(getApplicationContext(), "", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(MyFirebaseInstanceIDService.this, "HATA", Toast.LENGTH_SHORT).show();
            }
        });
    }
 
}