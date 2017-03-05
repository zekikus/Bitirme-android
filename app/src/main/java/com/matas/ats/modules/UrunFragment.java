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
import com.matas.ats.models.Urun;
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

public class UrunFragment extends Fragment{
    private JSONArray jsonArray;
    private JSONObject sonuc;
    private CommonSearchScreen searchScreen;
    private List<Urun> dataAdapter;
    private View rootView;
    private ListView resultView;
    private Button listButton;
    private ProgressBar urun_pb;
    private LinearLayout ll1;
    private LinearLayout.LayoutParams lp;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_ortak, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initialMethod();
        getUrun("",rootView);


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
        urun_pb = (ProgressBar) rootView.findViewById(R.id.ortak_progress);
        listButton = (Button) rootView.findViewById(R.id.ortak_listButton);
        dataAdapter = new ArrayList<Urun>();
        sonuc = null;
    }

    public void buildListPanel(View view){

        resultView = (ListView) view.findViewById(R.id.ortak_listResult);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),dataAdapter);
        resultView.setAdapter(ortakAdapter);
        resultView.setOnItemClickListener(new CommonListener<Urun>(dataAdapter,this,new UrunListFragment()));
    }

    public void buildSearchPanel(final LayoutInflater inflater) throws JSONException {
        if(searchScreen == null){
            searchScreen = new CommonSearchScreen() {
                @Override
                public void buildFilterPanel() {
                    View bottomSheetView = buildSearchScreen(inflater,rootView);

                    final EditText urun_no = new EditText(bottomSheetView.getContext());
                    urun_no.setBackgroundResource(R.drawable.edit_text_border);
                    urun_no.setTextColor(Color.BLACK);
                    urun_no.setHintTextColor(Color.GRAY);
                    urun_no.setPadding(10,5,5,5);
                    urun_no.setHint("  Ürün No Giriniz");

                    final EditText urun_ad = new EditText(bottomSheetView.getContext());
                    urun_ad.setBackgroundResource(R.drawable.edit_text_border);
                    urun_ad.setTextColor(Color.BLACK);
                    urun_ad.setHintTextColor(Color.GRAY);
                    urun_ad.setPadding(10,5,5,5);
                    urun_ad.setHint("  Ürün Adı Giriniz");

                    Button filterBtn = (Button) bottomSheetView.findViewById(R.id.filterBtn);
                    filterBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String sorgu[] = {urun_no.getText()+"",urun_ad.getText()+""};
                            tempFilter(sorgu,rootView);
                        }
                    });
                    //ll = LinearLayout
                    ll1 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine1);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5,0,0,10);
                    ll1.addView(urun_no,lp);

                    ll1 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine2);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5,0,0,10);
                    ll1.addView(urun_ad,lp);

                    getBottomSheetDialog().show();
                }
            };
            searchScreen.buildFilterPanel();
        }else
            searchScreen.getBottomSheetDialog().show();
    }

    //En başta bütün sonuçları getirdiğimiz için tekrar database'e sorgu atmaya gerek yok
    public void tempFilter(String[] sorgu,View view){
        urun_pb.setVisibility(View.VISIBLE);
        dataAdapter.clear();
        for (int i = 0; i < jsonArray.length(); i++){

            try {
                sonuc = jsonArray.getJSONObject(i);
                String[] response = {sonuc.getString("tag_id"),sonuc.getString("ad")};
                if(CommonMethods.equalControl(response,sorgu)) {
                    Urun urun = new Urun(sonuc.getInt("id"), sonuc.getString("ad"), sonuc.getString("tag_id"), sonuc.getString("TanimAd"), sonuc.getString("kullanim_suresi"), sonuc.getInt("doz"));
                    dataAdapter.add(urun);
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(),"Uygun Sonuç Bulunamadı",Toast.LENGTH_SHORT).show();
            }
        }
        buildListPanel(view);
        if(searchScreen != null)
            searchScreen.getBottomSheetDialog().hide();
        urun_pb.setVisibility(View.GONE);
    }

    public void getUrun(String sorgu, final View view) {
        urun_pb.setVisibility(View.VISIBLE);
        ATSRestClient.post(getContext(), "getUrunByTagOrName/tag/name/" + sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Urun urun;
                    jsonArray = response.getJSONArray("results");
                    dataAdapter.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            urun = new Urun(sonuc.getInt("id"), sonuc.getString("ad"), sonuc.getString("tag_id"), sonuc.getString("TanimAd"), sonuc.getString("kullanim_suresi"), sonuc.getInt("doz"));
                            dataAdapter.add(urun);
                        }
                        buildListPanel(view);
                        Log.e("Test",response + "");
                    }else {
                        Toast.makeText(getContext(), "Uygun Sonuç Bulunamadı", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Uygun Sonuç Bulunamadı", Toast.LENGTH_SHORT).show();
                }
                urun_pb.setVisibility(View.GONE);

                if(searchScreen != null)
                    searchScreen.getBottomSheetDialog().hide();

                Log.e("response = ", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                if (errorResponse != null) {
                    Toast.makeText(getContext(),"Uygun Sonuç Bulunamadı",Toast.LENGTH_SHORT).show();
                }
            }

        });
    }



}
