package com.matas.ats.storage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SaveSharedPreferences {

    private static final String USER_PREFS = "USER_PREFS";
    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private String isUserLoggedIn = "IsUserLoggedIn";
    private String userToken = "userToken";

    private String userName = "userName";
    private String fullName = "fullName";
    private String userPassword = "userPassword";
    private String userEmail = "userEmail";
    private String userBirimID = "userBirimID";
    private String userID = "1";

    private String GCMRegKey = "GCMRegKey";


    public SaveSharedPreferences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public String getUserToken() {
        return appSharedPrefs.getString(userToken, "");
    }
    public void setUserToken(String value) {
        prefsEditor.putString(userToken, value).commit();
    }

    public boolean getIsUserLoggedIn() {
        return appSharedPrefs.getBoolean(isUserLoggedIn, false);
    }
    public void setIsUserLoggedIn(boolean value) {
        prefsEditor.putBoolean(isUserLoggedIn, value).commit();
    }

    public String getUserName() {
        return appSharedPrefs.getString(userName, "");
    }
    public void setUserName(String value) {
        prefsEditor.putString(userName, value).commit();
    }

    public String getFullName() {
        return appSharedPrefs.getString(fullName, "");
    }
    public void setFullName(String value) {
        prefsEditor.putString(fullName, value).commit();
    }

    public boolean getUserPassword() {
        return appSharedPrefs.getBoolean(userPassword, false);
    }
    public void setUserPassword(boolean value) {
        prefsEditor.putBoolean(userPassword, value).commit();
    }

    public boolean getUserEmail() {
        return appSharedPrefs.getBoolean(userEmail, false);
    }
    public void setUserEmail(boolean value) {
        prefsEditor.putBoolean(userEmail, value).commit();
    }

    public String getUserBirimID() {
        return appSharedPrefs.getString(userBirimID, "1");
    }
    public void setUserBirimID(String value) {
        prefsEditor.putString(userBirimID, value).commit();
    }

    public String getUserID() {
        return appSharedPrefs.getString(userID, "1");
    }
    public void setUserID(String value) {
        prefsEditor.putString(userID, value).commit();
    }

}