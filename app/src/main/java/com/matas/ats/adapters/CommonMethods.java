package com.matas.ats.adapters;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.matas.ats.R;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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

    public static String calcDateDiff(String startDate){
        String dateFormat = "dd.MM.yyyy HH:mm:ss";
        String dateDiff= "";
        boolean timeFormat = true;

        SimpleDateFormat format = null;
        if (!startDate.contains(":")) {
            dateFormat = "dd.MM.yyyy";
            timeFormat = false;
        }

        format = new SimpleDateFormat(dateFormat);
        Date d1 = null;
        Date d2 = new Date();

        try {
            d1 = format.parse(startDate);

            long diff = d1.getTime() - d2.getTime();

            String diffSeconds = dateFormat(diff / 1000 % 60," saniye");
            String diffMinutes = dateFormat(diff / (60 * 1000) % 60," dakika");
            String diffHours = dateFormat(diff / (60 * 60 * 1000) % 24," saat");
            String diffDays = dateFormat(diff / (24 * 60 * 60 * 1000) % 31, " gün");
            String diffMonth = dateFormat(diff / (365 * 12 * 60 * 60 * 1000)," ay");



            dateDiff = diffMonth + diffDays;
            if(timeFormat)
                dateDiff += diffHours + " saat " + diffMinutes + " dakika " + diffSeconds + " saniye";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateDiff;
    }

    private static String dateFormat(long val,String ek){
        return (val == 0) ? "" : "" + val + ek;
    }
}
