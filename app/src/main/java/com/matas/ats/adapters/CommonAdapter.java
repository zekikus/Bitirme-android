package com.matas.ats.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.matas.ats.R;
import com.matas.ats.models.CommonModul;

import java.util.List;

/**
 * Created by Zeki on 31.10.2016.
 */

public class CommonAdapter<T extends CommonModul> extends BaseAdapter {

    protected List<T> resultList;
    protected LayoutInflater mInflater;
    private String[] colors ;
    private T test;

    public CommonAdapter(Activity activity,List<T> resultList){
        this.resultList = resultList;
        this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View commonView = mInflater.inflate(R.layout.ortak_layout, null);
        colors = commonView.getResources().getStringArray(R.array.listview_colors);

        TextView resultTitle = (TextView) commonView.findViewById(R.id.resultTitle);
        TextView resultDesc = (TextView) commonView.findViewById(R.id.resultDescription);
        TextView resultColor = (TextView) commonView.findViewById(R.id.resultColor);

        test = resultList.get(i);

        resultTitle.setText(test.getTitle());
        resultDesc.setText(test.getDesc());

        resultColor.setBackgroundColor(Color.parseColor(colors[i % 5]));

        return commonView;

    }
}
