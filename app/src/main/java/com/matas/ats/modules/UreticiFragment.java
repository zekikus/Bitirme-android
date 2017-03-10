package com.matas.ats.modules;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.matas.ats.R;
import com.matas.ats.adapters.CommonAdapter;
import com.matas.ats.adapters.CommonListener;
import com.matas.ats.adapters.CommonMethods;
import com.matas.ats.adapters.CommonSearchScreen;
import com.matas.ats.models.Uretici;
import com.matas.ats.network.ATSRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.conn.ConnectTimeoutException;

/**
 * Created by Zeki on 31.10.2016.
 */

public class UreticiFragment extends Fragment{
    private JSONArray jsonArray;
    private JSONObject sonuc;
    private CommonSearchScreen searchScreen;
    private String[] headers = {"Ad","Ülke"};
    private List<Uretici> dataAdapter;
    private View rootView;
    private ListView resultView;
    private Button listButton;
    private ProgressBar dolap_tipi_pb;
    private LinearLayout ll1;
    private LinearLayout.LayoutParams lp;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_ortak, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initialMethod();
        getUretici("",rootView);


        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    buildSearchPanel(inflater);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    //Başlangıçta kullanılacak sınıfları oluştur.
    public void initialMethod(){
        dolap_tipi_pb = (ProgressBar) rootView.findViewById(R.id.ortak_progress);
        listButton = (Button) rootView.findViewById(R.id.ortak_listButton);
        dataAdapter = new ArrayList<Uretici>();
        sonuc = null;
    }

    public void buildListPanel(View view){

        resultView = (ListView) view.findViewById(R.id.ortak_listResult);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),dataAdapter);
        resultView.setAdapter(ortakAdapter);
        resultView.setOnItemClickListener(new CommonListener<Uretici>(dataAdapter,this,new UreticiListFragment()));
    }

    public void buildSearchPanel(final LayoutInflater inflater) throws JSONException {
        if(searchScreen == null){
            searchScreen = new CommonSearchScreen() {
                @Override
                public void buildFilterPanel() {
                    View bottomSheetView = buildSearchScreen(inflater,rootView);

                    final EditText uretici_ad = new EditText(bottomSheetView.getContext());
                    uretici_ad.setBackgroundResource(R.drawable.edit_text_border);
                    uretici_ad.setTextColor(Color.BLACK);
                    uretici_ad.setHintTextColor(Color.GRAY);
                    uretici_ad.setPadding(10,5,5,5);
                    uretici_ad.setHint("  Üretici Adı Giriniz");

                    Button filterBtn = (Button) bottomSheetView.findViewById(R.id.filterBtn);
                    filterBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String sorgu = "" + uretici_ad.getText();
                            if(jsonArray != null)
                                tempFilter(sorgu,rootView);
                        }
                    });
                    //ll = LinearLayout
                    ll1 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine1);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5,0,0,10);
                    ll1.addView(uretici_ad,lp);

                    getBottomSheetDialog().show();
                }
            };
            searchScreen.buildFilterPanel();
        }else
            searchScreen.getBottomSheetDialog().show();
    }

    //En başta bütün sonuçları getirdiğimiz için tekrar database'e sorgu atmaya gerek yok
    public void tempFilter(String sorgu,View view){

        dolap_tipi_pb.setVisibility(View.VISIBLE);
        dataAdapter.clear();
        for (int i = 0; i < jsonArray.length(); i++){

            try {
                sonuc = jsonArray.getJSONObject(i);
                if(sonuc.getString("ad").toLowerCase().contains(sorgu.toLowerCase())) {
                    Uretici uretici = new Uretici(sonuc.getInt("id"), sonuc.getString("ad"), sonuc.getString("ulke"));
                    dataAdapter.add(uretici);
                }
            } catch (JSONException e) {
                CommonMethods.makeaShortToast(rootView,R.string.no_result);
            }
        }
        buildListPanel(view);
        if(searchScreen != null)
            searchScreen.getBottomSheetDialog().hide();
        dolap_tipi_pb.setVisibility(View.GONE);
    }

    public void getUretici(String sorgu, final View view) {
        dolap_tipi_pb.setVisibility(View.VISIBLE);

            ATSRestClient.post(getContext(), "getUreticiByName/" + sorgu, null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                    try {
                        Uretici uretici;
                        jsonArray = response.getJSONArray("results");
                        dataAdapter.clear();

                        if(!jsonArray.getJSONObject(0).has("result")){
                            for (int i = 0; i < jsonArray.length(); i++){
                                sonuc = jsonArray.getJSONObject(i);
                                uretici = new Uretici(sonuc.getInt("id"),sonuc.getString("ad"),sonuc.getString("ulke"));
                                dataAdapter.add(uretici);
                            }
                            buildListPanel(view);
                        }else {
                            CommonMethods.makeaShortToast(rootView,R.string.no_result);
                        }

                    } catch (JSONException e) {
                        CommonMethods.makeaShortToast(rootView,R.string.no_result);
                    }
                    dolap_tipi_pb.setVisibility(View.GONE);

                    if(searchScreen != null)
                        searchScreen.getBottomSheetDialog().hide();

                    Log.e("response = ", response.toString());
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable error, JSONObject errorResponse) {
                    CommonMethods.makeaShortToast(rootView,R.string.connection_error);
                    dolap_tipi_pb.setVisibility(View.GONE);
                }

            });

    }



}
