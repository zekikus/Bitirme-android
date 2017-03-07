package com.matas.ats.modules;

import android.graphics.Color;
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
import com.matas.ats.models.TuketimNedeni;
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

public class TuketimNedeniFragment extends Fragment{
    private JSONArray jsonArray;
    private JSONObject sonuc;
    private CommonSearchScreen searchScreen;
    private List<TuketimNedeni> dataAdapter;
    private View rootView;
    private ListView resultView;
    private Button listButton;
    private ProgressBar tuketimNedeni_pb;
    private LinearLayout ll1;
    private LinearLayout.LayoutParams lp;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_ortak, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initialMethod();
        getTuketimNedeni("",rootView);


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
        tuketimNedeni_pb = (ProgressBar) rootView.findViewById(R.id.ortak_progress);
        listButton = (Button) rootView.findViewById(R.id.ortak_listButton);
        dataAdapter = new ArrayList<TuketimNedeni>();
        sonuc = null;
    }

    public void buildListPanel(View view){

        resultView = (ListView) view.findViewById(R.id.ortak_listResult);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),dataAdapter);
        resultView.setAdapter(ortakAdapter);
        resultView.setOnItemClickListener(new CommonListener<TuketimNedeni>(dataAdapter,this,new TuketimNedeniListFragment()));
    }

    public void buildSearchPanel(final LayoutInflater inflater) throws JSONException {
        if(searchScreen == null){
            searchScreen = new CommonSearchScreen() {
                @Override
                public void buildFilterPanel() {
                    View bottomSheetView = buildSearchScreen(inflater,rootView);

                    final EditText tuketim_nedeni = new EditText(bottomSheetView.getContext());
                    tuketim_nedeni.setBackgroundResource(R.drawable.edit_text_border);
                    tuketim_nedeni.setTextColor(Color.BLACK);
                    tuketim_nedeni.setHintTextColor(Color.GRAY);
                    tuketim_nedeni.setPadding(10,5,5,5);
                    tuketim_nedeni.setHint("  Tüketim Nedeni Giriniz...");

                    Button filterBtn = (Button) bottomSheetView.findViewById(R.id.filterBtn);
                    filterBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String sorgu = "" + tuketim_nedeni.getText();
                            tempFilter(sorgu,rootView);
                        }
                    });
                    //ll = LinearLayout
                    ll1 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine1);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5,0,0,10);
                    ll1.addView(tuketim_nedeni,lp);

                    getBottomSheetDialog().show();
                }
            };
            searchScreen.buildFilterPanel();
        }else
            searchScreen.getBottomSheetDialog().show();
    }

    //En başta bütün sonuçları getirdiğimiz için tekrar database'e sorgu atmaya gerek yok
    public void tempFilter(String sorgu,View view){

        tuketimNedeni_pb.setVisibility(View.VISIBLE);
        dataAdapter.clear();
        for (int i = 0; i < jsonArray.length(); i++){

            try {
                sonuc = jsonArray.getJSONObject(i);
                if(sonuc.getString("tanim").toLowerCase().contains(sorgu.toLowerCase())) {
                    dataAdapter.add(new TuketimNedeni(sonuc.getInt("id"),sonuc.getString("tanim"),sonuc.getInt("aktifMi")));
                }
            } catch (JSONException e) {
                CommonMethods.makeaShortToast(rootView,R.string.no_result);
            }
        }
        buildListPanel(view);
        if(searchScreen != null)
            searchScreen.getBottomSheetDialog().hide();
        tuketimNedeni_pb.setVisibility(View.GONE);
    }

    public void getTuketimNedeni(String sorgu, final View view) {
        tuketimNedeni_pb.setVisibility(View.VISIBLE);
        ATSRestClient.post(getContext(), "getTuketimNedeniByName/" + sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("results");
                    dataAdapter.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            dataAdapter.add(new TuketimNedeni(sonuc.getInt("id"),sonuc.getString("tanim"),sonuc.getInt("aktifMi")));
                        }
                        buildListPanel(view);
                    }else {
                        CommonMethods.makeaShortToast(rootView,R.string.no_result);
                    }

                } catch (JSONException e) {
                    CommonMethods.makeaShortToast(rootView,R.string.no_result);
                }
                tuketimNedeni_pb.setVisibility(View.GONE);

                if(searchScreen != null)
                    searchScreen.getBottomSheetDialog().hide();

                Log.e("response = ", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                CommonMethods.makeaShortToast(rootView,R.string.connection_error);
                tuketimNedeni_pb.setVisibility(View.GONE);
            }

        });
    }



}
