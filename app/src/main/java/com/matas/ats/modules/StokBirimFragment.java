package com.matas.ats.modules;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.matas.ats.R;
import com.matas.ats.adapters.CommonAdapter;
import com.matas.ats.adapters.CommonListener;
import com.matas.ats.adapters.CommonMethods;
import com.matas.ats.adapters.CommonSearchScreen;
import com.matas.ats.models.BirimTanim;
import com.matas.ats.models.Ilce;
import com.matas.ats.models.Sehir;
import com.matas.ats.models.StokBirim;
import com.matas.ats.network.ATSRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Zeki on 31.10.2016.
 */

public class StokBirimFragment extends Fragment  {

    private JSONArray jsonArray;
    private JSONObject sonuc;
    private CommonSearchScreen searchScreen;
    private List<StokBirim> dataAdapter;
    private ArrayAdapter<Sehir> sehirler;
    private ArrayAdapter<Ilce> ilceler;
    private ArrayAdapter<BirimTanim> birimler;
    private View rootView;
    private ListView resultView;
    private Spinner il,ilce,birim;
    private Button listButton;
    private ProgressBar bt_pb;
    private LinearLayout ll1,ll2,ll3;
    private LinearLayout.LayoutParams lp;
    private boolean isSpinnerInitial = true,isSpinnerIlceInitial = true;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_ortak, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initialMethod();
        try {
            getStokBirim("getStokBirimById/1",rootView);} catch (JSONException e) {e.printStackTrace();}


        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    buildSearchPanel(inflater);
                } catch (JSONException e) {e.printStackTrace();}
            }
        });

        return rootView;
    }

    //Başlangıçta kullanılacak sınıfları oluştur.
    public void initialMethod(){
        bt_pb = (ProgressBar) rootView.findViewById(R.id.ortak_progress);
        listButton = (Button) rootView.findViewById(R.id.ortak_listButton);
        dataAdapter = new ArrayList<StokBirim>();
        sonuc = null;
    }

    public void buildListPanel(View view){

        resultView = (ListView) view.findViewById(R.id.ortak_listResult);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),dataAdapter);
        resultView.setAdapter(ortakAdapter);
        resultView.setOnItemClickListener(new CommonListener<>(dataAdapter, this, new SBListFragment()));
    }

    public void buildSearchPanel(final LayoutInflater inflater) throws JSONException {
        if(searchScreen == null){
            searchScreen = new CommonSearchScreen() {
                @Override
                public void buildFilterPanel() throws JSONException {
                    View bottomSheetView = buildSearchScreen(inflater,rootView);

                    //ll = LinearLayout
                    ll1 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine1);
                    ll2 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine2);
                    ll3 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine3);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(10,0,0,10);

                    il = new Spinner(bottomSheetView.getContext());
                    il.setBackgroundResource(R.drawable.text_view_border);
                    sehirler =new ArrayAdapter<Sehir>(bottomSheetView.getContext(),android.R.layout.simple_spinner_item){
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);

                            ((TextView) v).setTextSize(16);
                            ((TextView) v).setTextColor(Color.BLACK);

                            return v;
                        }
                    };
                    sehirler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    getIl("getAllSehir");
                    il.setSelection(0);

                    ilce = new Spinner(bottomSheetView.getContext());
                    ilce.setBackgroundResource(R.drawable.text_view_border);
                    ilceler = new ArrayAdapter<Ilce>(bottomSheetView.getContext(),android.R.layout.simple_spinner_item){
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);

                            ((TextView) v).setTextSize(16);
                            ((TextView) v).setTextColor(Color.BLACK);

                            return v;
                        }
                    };
                    ilceler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    birim = new Spinner(bottomSheetView.getContext());
                    birim.setBackgroundResource(R.drawable.text_view_border);
                    birimler = new ArrayAdapter<BirimTanim>(bottomSheetView.getContext(),android.R.layout.simple_spinner_item){
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View v = super.getView(position, convertView, parent);
                            ((TextView) v).setTextSize(16);
                            ((TextView) v).setTextColor(Color.BLACK);
                            return v;
                        }
                    };
                    birimler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    ilce.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                           if(isSpinnerIlceInitial)
                                isSpinnerIlceInitial = false;
                            else  {
                                String sorgu = "getBirimByIlceName/il/"+il.getSelectedItem().toString()+"/ilce/"+ ilce.getSelectedItem().toString();
                                try {
                                    ll3.removeAllViews();
                                    getBirim(sorgu);
                                } catch (JSONException e) {e.printStackTrace();}
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {}
                    });


                    il.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            if(isSpinnerInitial)
                                isSpinnerInitial = false;
                            else  {
                                Sehir sehir = (Sehir)il.getSelectedItem();
                                String sorgu = "getIlceByID/" + sehir.getId();
                                try {
                                    ll2.removeAllViews();
                                    getIlce(sorgu);
                                    ll2.addView(ilce,lp);
                                } catch (JSONException e) {e.printStackTrace();}
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {}
                    });

                    Button filterBtn = (Button) bottomSheetView.findViewById(R.id.filterBtn);
                    filterBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if(birim.getSelectedItem() != null) {
                                    BirimTanim gBirim = (BirimTanim) birim.getSelectedItem();
                                    String sorgu = "getStokBirimById/"+ gBirim.getId();
                                    getStokBirim(sorgu,rootView);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    ll1.addView(il,lp);
                    getBottomSheetDialog().show();
                }
            };


        searchScreen.buildFilterPanel();
        }else
            searchScreen.getBottomSheetDialog().show();
    }

    public void getStokBirim(String sorgu, final View view) throws  JSONException{
        bt_pb.setVisibility(View.VISIBLE);

        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    StokBirim stok_birim;
                    jsonArray = response.getJSONArray("results");
                    dataAdapter.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            stok_birim = new StokBirim(sonuc.getInt("id"),sonuc.getString("ad"),sonuc.getString("tanim"),
                                                        sonuc.getString("aciklama"),sonuc.getString("hacim"),sonuc.getInt("sensor_id"));
                            dataAdapter.add(stok_birim);
                        }
                        buildListPanel(view);
                    }else {
                        CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                    }

                } catch (JSONException e) {
                    CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                }
                bt_pb.setVisibility(View.GONE);

                if(searchScreen != null) searchScreen.getBottomSheetDialog().hide();
            }
        });
    }

    public void getBirim(String sorgu) throws JSONException {

        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    jsonArray = response.getJSONArray("results");
                    birimler.clear();
                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            birimler.add(new BirimTanim(sonuc.getInt("id"),sonuc.getString("ad"),sonuc.getString("il"),sonuc.getString("ilce"),sonuc.getString("adres")));
                        }
                        birim.setAdapter(birimler);
                    }else {
                        CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                    }

                } catch (JSONException e) {
                    CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                }

                if(birimler.getCount() != 0)
                    ll3.addView(birim,lp);
            }
        });
    }

    public void getIl(String sorgu) throws JSONException {
            ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        jsonArray = response.getJSONArray("results");

                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            sehirler.add(new Sehir(sonuc.getInt("id"),sonuc.getString("ad")));
                        }
                        il.setAdapter(sehirler);
                    } catch (JSONException e) {
                        CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                    }
                }
            });
    }

    public void getIlce(String sorgu) throws JSONException {
            ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        jsonArray = response.getJSONArray("results");
                        ilceler.clear();
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            ilceler.add(new Ilce(sonuc.getInt("id"),sonuc.getString("ad"),sonuc.getInt("il_id")));
                        }
                        ilce.setAdapter(ilceler);

                    } catch (JSONException e) {CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");}
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    if (errorResponse != null) CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                }
            });
    }

}
