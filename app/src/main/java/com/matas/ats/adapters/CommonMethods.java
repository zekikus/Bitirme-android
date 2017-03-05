package com.matas.ats.adapters;

import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zeki on 8.02.2017.
 */

public class CommonMethods {

    //Direkt kullanılan Ortak Metodlar Buraya
    public static void makeaShortToast(View view, String message){
        Toast.makeText(view.getContext(),message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isEquals(String val,String val2){
        return val.toLowerCase().contains(val2.toLowerCase());
    }

    public static boolean equalControl(String[] response, String[] parameters) throws JSONException {
        for (int i=0; i < response.length;i++) {
            if (!isEquals(response[i],parameters[i]))
                return false;
        }
        return true;
    }
}
