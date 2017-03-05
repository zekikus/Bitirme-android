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
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.matas.ats.R;
import com.matas.ats.adapters.CommonAdapter;
import com.matas.ats.adapters.CommonMethods;
import com.matas.ats.models.Alarm;
import com.matas.ats.models.Kullanici;
import com.matas.ats.models.Urun;
import com.matas.ats.network.ATSRestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class AlarmListFragment extends Fragment{


    private View rootView;
    private JSONArray jsonArray;
    private JSONObject sonuc;
    private LinearLayout ll;
    private LinearLayout.LayoutParams lp,lp2;
    private List<LinearLayout> ll_list;
    private List<TextView> tv_list;
    private List<EditText> et_list;
    private List<Alarm> alarm_list;
    private int id_val;
    private String[] col_names = {"tip","baslangic_zaman","bitis_zaman","sensor_id","durum"};
    private String[] header_names = {"Alarm Tipi: ","Alarm Başlangıç Zamanı: ","Alarm Bitiş Zamanı: ","STC No: ","Alarm Durumu: "};
    private Button tab1,tab2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_info_ortak, container, false);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        id_val= getArguments().getInt("id_val");
        try { initInfoScreen(); } catch (JSONException e) {e.printStackTrace();}

        return rootView;
    }

    public void initInfoScreen() throws JSONException{

        tab1 = (Button) rootView.findViewById(R.id.tab1);
        tab2 = (Button) rootView.findViewById(R.id.tab2);

        tab1.setVisibility(View.INVISIBLE);
        tab2.setVisibility(View.INVISIBLE);

        //Template'i temizle
        ll = (LinearLayout) rootView.findViewById(R.id.ortak_info_temp);
        ll.removeAllViews();
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(100,0,10,0);

        lp2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lp2.setMargins(0,0,50,0);

        alarm_list = new ArrayList<Alarm>();
        ll_list = new ArrayList<LinearLayout>();
        tv_list = new ArrayList<TextView>();
        et_list = new ArrayList<EditText>();

        String sorgu = "getAlarmInfoById/" + id_val;
        getAlarmInfo(sorgu);

    }

    public void getAlarmInfo(final String sorgu) throws JSONException {

        ATSRestClient.post(getContext(), sorgu, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    jsonArray = response.getJSONArray("results");
                    alarm_list.clear();

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
                        CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                    }

                } catch (JSONException e) {
                    CommonMethods.makeaShortToast(rootView,"Uygun Sonuç Bulunamadı");
                }
            }
        });
    }

}