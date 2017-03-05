package com.matas.ats.network;


import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ATSRestClient {

    private static final String BASE_URL = "http://192.168.2.199/Bitirme/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();


    public static void get(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        //fetchUserCredentials(context);
        if (params == null) {
            params = new RequestParams();
        }
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(Context context, String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {

        //fetchUserCredentials(context);
        if (params == null) {
            params = new RequestParams();
        }

        //params.put("user",USER);
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}