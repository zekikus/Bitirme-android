package com.matas.ats.modules;

import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.matas.ats.R;
import com.matas.ats.adapters.CommonAdapter;
import com.matas.ats.adapters.CommonMethods;
import com.matas.ats.models.Sicaklik;
import com.matas.ats.models.Urun;
import com.matas.ats.network.ATSRestClient;
import com.matas.ats.storage.SaveSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Zeki on 1.06.2017.
 */

public class AnasayfaFragment extends Fragment {

    private JSONArray jsonArray;
    private JSONObject sonuc;
    private List<Sicaklik> dataAdapter;
    private ListView resultView;
    private List<Urun> sktDataAdapter;
    private ListView sktResultView;
    private SaveSharedPreferences prefs;
    private View rootView;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_home, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        prefs = new SaveSharedPreferences(this.getContext());
        initialMethod();

        try {
            getSicaklik("getSicaklikInfoByStc/" + prefs.getUserBirimID(),rootView);
            getSKTInfo("getSKTInfo/" + prefs.getUserBirimID(),rootView);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    public void initialMethod(){
        dataAdapter = new ArrayList<Sicaklik>();
        sktDataAdapter = new ArrayList<Urun>();
        sonuc = null;
    }

    public void buildListPanel(View view){
        resultView = (ListView) view.findViewById(R.id.home_alarm_list);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),dataAdapter);
        resultView.setAdapter(ortakAdapter);
    }

    public void buildSKTListPanel(View view){
        sktResultView = (ListView) view.findViewById(R.id.home_skt_list);
        CommonAdapter sktAdapter = new CommonAdapter(getActivity(),sktDataAdapter);
        sktResultView.setAdapter(sktAdapter);
    }

    public void getSicaklik(String sorgu, final View view) throws JSONException {
        // alarm_pb.setVisibility(View.VISIBLE);
        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Sicaklik sicaklik;
                    jsonArray = response.getJSONArray("results");
                    dataAdapter.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            sicaklik = new Sicaklik(sonuc.getInt("id"),sonuc.getInt("sensor_id"),sonuc.getString("sicaklik_deger"),sonuc.getString("kayit_zamani"));
                            dataAdapter.add(sicaklik);
                        }
                        buildListPanel(view);
                    }else {
                        CommonMethods.makeaShortToast(rootView,R.string.no_result);
                    }
                } catch (JSONException e) {
                    CommonMethods.makeaShortToast(rootView,R.string.no_result);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                CommonMethods.makeaShortToast(rootView,R.string.connection_error);
                //alarm_pb.setVisibility(View.GONE);
            }
        });
    }

    public void getSKTInfo(String sorgu, final View view) throws JSONException {
        // alarm_pb.setVisibility(View.VISIBLE);
        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Urun urun;
                    jsonArray = response.getJSONArray("results");
                    sktDataAdapter.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            urun = new Urun(sonuc.getString("stokbirim_ad"),sonuc.getString("TagID"),sonuc.getString("ad"),sonuc.getString("kullanim_suresi"));
                            sktDataAdapter.add(urun);
                        }
                        buildSKTListPanel(view);
                    }else {
                        CommonMethods.makeaShortToast(rootView,R.string.no_result);
                    }
                } catch (JSONException e) {
                    CommonMethods.makeaShortToast(rootView,R.string.no_result);
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                CommonMethods.makeaShortToast(rootView,R.string.connection_error);
            }
        });
    }
}
