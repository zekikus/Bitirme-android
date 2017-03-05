package com.matas.ats.modules;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.matas.ats.models.Alarm;
import com.matas.ats.models.Ilce;
import com.matas.ats.models.Sehir;
import com.matas.ats.models.Uretici;
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

public class AlarmFragment extends Fragment{
    private JSONArray jsonArray;
    private JSONObject sonuc;
    private CommonSearchScreen searchScreen;
    private List<Alarm> dataAdapter;
    private View rootView;
    private ListView resultView;
    private Spinner il,ilce;
    private Button listButton;
    private ProgressBar alarm_pb;
    private LinearLayout ll1,ll2;
    private LinearLayout.LayoutParams lp;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_ortak, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        initialMethod();
        try {
            getAlarm("getAlarmByIdOrStcNo/id//stcno/",rootView);} catch (JSONException e) {e.printStackTrace();}


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
        alarm_pb = (ProgressBar) rootView.findViewById(R.id.ortak_progress);
        listButton = (Button) rootView.findViewById(R.id.ortak_listButton);
        dataAdapter = new ArrayList<Alarm>();
        sonuc = null;
    }

    public void buildListPanel(View view){

        resultView = (ListView) view.findViewById(R.id.ortak_listResult);
        CommonAdapter ortakAdapter = new CommonAdapter(getActivity(),dataAdapter);
        resultView.setAdapter(ortakAdapter);
        resultView.setOnItemClickListener(new CommonListener<Alarm>(dataAdapter,this,new AlarmListFragment()));
    }

    public void buildSearchPanel(final LayoutInflater inflater) throws JSONException {
        if(searchScreen == null){
            searchScreen = new CommonSearchScreen() {
                @Override
                public void buildFilterPanel() throws JSONException {
                    View bottomSheetView = buildSearchScreen(inflater,rootView);

                    final EditText alarm_no = new EditText(bottomSheetView.getContext());
                    alarm_no.setBackgroundResource(R.drawable.edit_text_border);
                    alarm_no.setTextColor(Color.BLACK);
                    alarm_no.setHintTextColor(Color.GRAY);
                    alarm_no.setPadding(10,5,5,5);
                    alarm_no.setHint(" Alarm No Giriniz");

                    final EditText stc_no = new EditText(bottomSheetView.getContext());
                    stc_no.setBackgroundResource(R.drawable.edit_text_border);
                    stc_no.setTextColor(Color.BLACK);
                    stc_no.setHintTextColor(Color.GRAY);
                    stc_no.setPadding(10,5,5,5);
                    stc_no.setHint(" Sıcaklık Takip Cihazı No Giriniz");

                    Button filterBtn = (Button) bottomSheetView.findViewById(R.id.filterBtn);
                    filterBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String sorgu = "getAlarmByIdOrStcNo/id/"+alarm_no.getText()+"/stcno/"+ stc_no.getText();
                            if (alarm_no.getText().equals("") && stc_no.getText().equals("")) {
                                String[] myParamater = {alarm_no.getText() + "", stc_no.getText() + ""};
                                tempFilter(myParamater, rootView);
                            }
                            else
                                try {
                                    getAlarm(sorgu,rootView);
                                } catch (JSONException e) {}
                        }
                    });
                    //ll = LinearLayout
                    ll1 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine1);
                    ll2 = (LinearLayout) bottomSheetView.findViewById(R.id.filterLine2);
                    lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(5,0,0,10);
                    ll1.addView(alarm_no,lp);
                    ll2.addView(stc_no,lp);
                    getBottomSheetDialog().show();
                }
            };


        searchScreen.buildFilterPanel();
        }else
            searchScreen.getBottomSheetDialog().show();
    }

    public void tempFilter(String[] sorgu,View view){

        alarm_pb.setVisibility(View.VISIBLE);
        dataAdapter.clear();
        for (int i = 0; i < jsonArray.length(); i++){

            try {
                sonuc = jsonArray.getJSONObject(i);
                String[] response = {sonuc.getInt("aID")+"",sonuc.getInt("sbStcID") + ""};
                if(CommonMethods.equalControl(response,sorgu)) {
                    dataAdapter.add(new Alarm(sonuc.getInt("aID"),sonuc.getString("bIl"),sonuc.getString("bIlce"),sonuc.getString("bAd"),sonuc.getInt("sbID"),sonuc.getInt("sbStcID"),sonuc.getString("aTip")));
                }
            } catch (JSONException e) {
                Toast.makeText(getContext(),"Uygun Sonuç Bulunamadı",Toast.LENGTH_SHORT).show();
            }
        }
        buildListPanel(view);
        if(searchScreen != null)
            searchScreen.getBottomSheetDialog().hide();
        alarm_pb.setVisibility(View.GONE);
    }

    public void getAlarm(String sorgu, final View view) throws JSONException {
        alarm_pb.setVisibility(View.VISIBLE);

        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    Alarm alarm;
                    jsonArray = response.getJSONArray("results");
                    dataAdapter.clear();

                    if(!jsonArray.getJSONObject(0).has("result")){
                        for (int i = 0; i < jsonArray.length(); i++){
                            sonuc = jsonArray.getJSONObject(i);
                            alarm = new Alarm(sonuc.getInt("aID"),sonuc.getString("bIl"),sonuc.getString("bIlce"),sonuc.getString("bAd"),sonuc.getInt("sbID"),sonuc.getInt("sbStcID"),sonuc.getString("aTip"));
                            dataAdapter.add(alarm);
                        }
                        Log.e("Sonuc",response + "");
                        buildListPanel(view);
                    }else {
                        CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                    }

                } catch (JSONException e) {
                    CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                }
                alarm_pb.setVisibility(View.GONE);

                if(searchScreen != null) searchScreen.getBottomSheetDialog().hide();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null) Toast.makeText(getContext(),"Uygun Sonuç Bulunamadı",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
