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
import com.matas.ats.models.DolapTipi;
import com.matas.ats.models.STC;
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

public class STCFragment extends Fragment{
    private JSONArray jsonArray;
    private JSONObject sonuc;
    private CommonSearchScreen searchScreen;
    private List<STC> dataAdapter;
    private View rootView;
    private ListView resultView;
    private Button listButton;
    private ProgressBar stc_pb;
    private LinearLayout ll1;
    private LinearLayout.LayoutParams lp;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_ortak, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initialMethod();
        getSTC("1",rootView);


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
        stc_pb = (ProgressBar) rootView.findViewById(R.id.ortak_progress);
        listButton = (Button) rootView.findViewById(R.id.ortak_listButton);
        dataAdapter = new ArrayList<STC>();
        sonuc = null;
    }

    public void buildListPanel(View view){

        resultView = (ListView) view.findViewById(R.id.ortak_listResult);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),dataAdapter);
        resultView.setAdapter(ortakAdapter);
        resultView.setOnItemClickListener(new CommonListener<STC>(dataAdapter,this,new STCListFragment()));
    }

    public void buildSearchPanel(final LayoutInflater inflater) throws JSONException {
        if(searchScreen == null){
            searchScreen = new CommonSearchScreen() {
                @Override
                public void buildFilterPanel() {
                    View bottomSheetView = buildSearchScreen(inflater,rootView);

                    final EditText sicaklik_takip_cihazi = new EditText(bottomSheetView.getContext());
                    sicaklik_takip_cihazi.setBackgroundResource(R.drawable.edit_text_border);
                    sicaklik_takip_cihazi.setTextColor(Color.BLACK);
                    sicaklik_takip_cihazi.setHintTextColor(Color.GRAY);
                    sicaklik_takip_cihazi.setPadding(10,5,5,5);
                    sicaklik_takip_cihazi.setHint("  STC No Giriniz...");

                    Button filterBtn = (Button) bottomSheetView.findViewById(R.id.filterBtn);
                    filterBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String sorgu = "" + sicaklik_takip_cihazi.getText();
                            getSTC(sorgu,rootView);
                        }
                    });
                    //ll = LinearLayout
                    ll1 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine1);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5,0,0,10);
                    ll1.addView(sicaklik_takip_cihazi,lp);

                    getBottomSheetDialog().show();
                }
            };
            searchScreen.buildFilterPanel();
        }else
            searchScreen.getBottomSheetDialog().show();
    }

    public void getSTC(String sorgu, final View view) {
        stc_pb.setVisibility(View.VISIBLE);
        ATSRestClient.post(getContext(), "getStcById/" + sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("results");
                    dataAdapter.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            dataAdapter.add(new STC(sonuc.getInt("id"), sonuc.getString("ad"), sonuc.getString("cihaz_durum"),sonuc.getInt("alarm_uret")));
                        }
                        buildListPanel(view);
                    }else {
                        CommonMethods.makeaShortToast(rootView,R.string.no_result);
                    }

                } catch (JSONException e) {
                    CommonMethods.makeaShortToast(rootView,R.string.no_result);
                }
                stc_pb.setVisibility(View.GONE);

                if(searchScreen != null)
                    searchScreen.getBottomSheetDialog().hide();

                Log.e("response = ", response.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                CommonMethods.makeaShortToast(rootView,R.string.connection_error);
            }

        });
    }



}
