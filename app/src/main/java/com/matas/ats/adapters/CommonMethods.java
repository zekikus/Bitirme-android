package com.matas.ats.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.matas.ats.R;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Zeki on 8.02.2017.
 */

public class CommonMethods {

    //Direkt kullanılan Ortak Metodlar Buraya
    public static void makeaShortToast(View view, String message){
        Toast.makeText(view.getContext(),message, Toast.LENGTH_SHORT).show();
    }

    public static void makeaShortToast(View view, int key){
        Toast.makeText(view.getContext(),getString(key,view.getContext()), Toast.LENGTH_SHORT).show();
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

    public static String getString(int key,Context context){
        return context.getResources().getString(key);
    }
}
