package com.matas.ats.modules;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.matas.ats.R;
import com.matas.ats.adapters.CommonAdapter;
import com.matas.ats.adapters.CommonMethods;
import com.matas.ats.models.BirimTanim;
import com.matas.ats.models.Kullanici;
import com.matas.ats.models.Sicaklik;
import com.matas.ats.models.StokBirim;
import com.matas.ats.models.Urun;
import com.matas.ats.network.ATSRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class SBListFragment extends Fragment{

    private View rootView;
    private JSONArray jsonArray;
    private JSONObject sonuc;
    private LinearLayout ll;
    private LinearLayout.LayoutParams lp,lp2;
    private List<LinearLayout> ll_list;
    private List<TextView> tv_list;
    private List<EditText> et_list;
    private List<StokBirim> stok_birim_list;
    private List<Urun> urun_list;
    private List<Sicaklik> sicaklik_list;
    private ListView resultView;
    private int id_val,sensor_id_val;
    private String[] col_names = {"ad","aciklama","hacim","marka","model","uretim_tarihi","tanim","sicaklik_alt_limit","sicaklik_ust_limit"};
    private String[] header_names = {"Stok Birim Adı: ","Açıklama: ","Hacim: ","Marka: ","Model: ","Üretim Tarihi: ","Tanım: ","Sıcaklık Alt Limit: ","Sıcaklık Üst Limit: "};
    private Button tab1,tab2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_info_ortak, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        id_val= getArguments().getInt("id_val");
        sensor_id_val = getArguments().getInt("sensor_id_val");
        try { initInfoScreen(); } catch (JSONException e) {e.printStackTrace();}

        return rootView;
    }

    public void initInfoScreen() throws JSONException{

        urun_list = new ArrayList<>();
        sicaklik_list = new ArrayList<Sicaklik>();

        tab1 = (Button) rootView.findViewById(R.id.tab1);
        tab2 = (Button) rootView.findViewById(R.id.tab2);

        tab1.setText("Stok Listesi");
        tab2.setText("Sıcaklık Ölçümleri");

        //Template'i temizle
        ll = (LinearLayout) rootView.findViewById(R.id.ortak_info_temp);
        ll.removeAllViews();
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(100,0,10,0);

        lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp2.setMargins(0,0,50,0);

        stok_birim_list = new ArrayList<StokBirim>();
        ll_list = new ArrayList<LinearLayout>();
        tv_list = new ArrayList<TextView>();
        et_list = new ArrayList<EditText>();

        String sorgu = "getStokBirimInfoById/" + id_val;
        getStokBirimInfo(sorgu);

        tab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll.removeAllViews();
                String sorgu = "getStokById/" + id_val;
                try {
                    getStokBirimStokInfo(sorgu,rootView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll.removeAllViews();
                String sorgu = "getSicaklikById/" + sensor_id_val;
                try {
                    getStokBirimSicaklik(sorgu,rootView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void buildListPanel(View view){
        resultView = (ListView) view.findViewById(R.id.ortak_info_listResult);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),urun_list);
        resultView.setAdapter(ortakAdapter);
    }

    public void buildSicaklikListPanel(View view){
        resultView = (ListView) view.findViewById(R.id.ortak_info_listResult);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),sicaklik_list);
        resultView.setAdapter(ortakAdapter);
    }

    public void getStokBirimSicaklik(String sorgu, final View view) throws  JSONException{

        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("results");
                    sicaklik_list.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            sicaklik_list.add(new Sicaklik(sonuc.getInt("id"),sonuc.getInt("sensor_id"),sonuc.getString("sicaklik_deger"),sonuc.getString("kayit_zamani"),sonuc.getString("olcum_zamani")));
                        }
                        buildSicaklikListPanel(view);
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

    public void getStokBirimStokInfo(String sorgu, final View view) throws  JSONException{

        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("results");
                    urun_list.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            urun_list.add(new Urun(sonuc.getString("tag_id"),sonuc.getString("ad"),sonuc.getInt("doz"),sonuc.getString("kullanim_suresi")));
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
            }
        });
    }

    public void getStokBirimInfo(final String sorgu) throws JSONException {

        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("results");
                    stok_birim_list.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i =0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            for (int j = 0; j < sonuc.length() - 1; j++){
                                ll_list.add(new LinearLayout(rootView.getContext()));
                                tv_list.add(new TextView(rootView.getContext()));
                                et_list.add(new EditText(rootView.getContext()));
                                tv_list.get(j).setLayoutParams(lp2);
                                tv_list.get(j).setText("" + header_names[j]);
                                et_list.get(j).setText("" + sonuc.get(col_names[j]));
                                ll_list.get(j).addView(tv_list.get(j),lp);
                                ll_list.get(j).addView(et_list.get(j),lp);
                                ll.addView(ll_list.get(j));
                            }
                        }
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