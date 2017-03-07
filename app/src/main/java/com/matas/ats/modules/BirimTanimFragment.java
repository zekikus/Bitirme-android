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
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.matas.ats.R;
import com.matas.ats.adapters.CommonAdapter;
import com.matas.ats.adapters.CommonListener;
import com.matas.ats.adapters.CommonMethods;
import com.matas.ats.adapters.CommonSearchScreen;
import com.matas.ats.models.BirimTanim;
import com.matas.ats.models.Ilce;
import com.matas.ats.models.Sehir;
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

public class BirimTanimFragment extends Fragment{
    private JSONArray jsonArray;
    private JSONObject sonuc;
    private CommonSearchScreen searchScreen;
    private List<BirimTanim> dataAdapter;
    private ArrayAdapter<Sehir> sehirler;
    private ArrayAdapter<Ilce> ilceler;
    private View rootView;
    private ListView resultView;
    private Spinner il,ilce;
    private Button listButton;
    private ProgressBar bt_pb;
    private LinearLayout ll1,ll2;
    private LinearLayout.LayoutParams lp;
    private boolean isSpinnerInitial = true;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_ortak, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initialMethod();
        try {
            getBirim("getBirimByIlceName/il//ilce/",rootView);} catch (JSONException e) {e.printStackTrace();}


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
        dataAdapter = new ArrayList<BirimTanim>();
        sonuc = null;
    }

    public void buildListPanel(View view){

        resultView = (ListView) view.findViewById(R.id.ortak_listResult);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),dataAdapter);
        resultView.setAdapter(ortakAdapter);
        resultView.setOnItemClickListener(new CommonListener<BirimTanim>(dataAdapter,this,new BirimListFragment()));
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

                    il.setFocusable(false);
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
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    Button filterBtn = (Button) bottomSheetView.findViewById(R.id.filterBtn);
                    filterBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                if(ilce.getSelectedItem() != null) {
                                    String sorgu = "getBirimByIlceName/il/" + il.getSelectedItem().toString() + "/ilce/" + ilce.getSelectedItem().toString();
                                    getBirim(sorgu, rootView);
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


    public void getBirim(String sorgu, final View view) throws JSONException {
        bt_pb.setVisibility(View.VISIBLE);

        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    BirimTanim birim;
                    jsonArray = response.getJSONArray("results");
                    dataAdapter.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            birim = new BirimTanim(sonuc.getInt("id"),sonuc.getString("ad"),sonuc.getString("il"),sonuc.getString("ilce"),sonuc.getString("adres"));
                            dataAdapter.add(birim);
                        }
                        buildListPanel(view);
                    }else {
                        CommonMethods.makeaShortToast(rootView,R.string.no_result);
                    }

                } catch (JSONException e) {
                    CommonMethods.makeaShortToast(rootView,R.string.no_result);
                }
                bt_pb.setVisibility(View.GONE);

                if(searchScreen != null) searchScreen.getBottomSheetDialog().hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                CommonMethods.makeaShortToast(rootView,R.string.connection_error);
                bt_pb.setVisibility(View.GONE);
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
                        CommonMethods.makeaShortToast(rootView,R.string.no_result);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    CommonMethods.makeaShortToast(rootView,R.string.connection_error);
                    bt_pb.setVisibility(View.GONE);
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

                    } catch (JSONException e) { CommonMethods.makeaShortToast(rootView,R.string.no_result);}
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    CommonMethods.makeaShortToast(rootView,R.string.connection_error);
                    bt_pb.setVisibility(View.GONE);
                }
            });
    }

}
