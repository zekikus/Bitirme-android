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

    private String GCMRegKey = "GCMRegKey";


    public SaveSharedPreferences(Context context) {
        this.appSharedPrefs = context.getSharedPreferences(USER_PREFS, Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public boolean getIsUserLoggedIn() {
        return appSharedPrefs.getBoolean(isUserLoggedIn, false);
    }
    public void setIsUserLoggedIn(boolean value) {
        prefsEditor.putBoolean(isUserLoggedIn, value).commit();
    }

    public boolean getUserName() {
        return appSharedPrefs.getBoolean(userName, false);
    }
    public void setUserName(boolean value) {
        prefsEditor.putBoolean(userName, value).commit();
    }

    public boolean getFullName() {
        return appSharedPrefs.getBoolean(fullName, false);
    }
    public void setFullName(boolean value) {
        prefsEditor.putBoolean(fullName, value).commit();
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



}