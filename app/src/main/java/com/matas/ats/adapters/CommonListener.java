package com.matas.ats.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;

import com.matas.ats.R;
import com.matas.ats.models.CommonModul;
import com.matas.ats.modules.SBListFragment;

import java.util.List;

/**
 * Created by Zeki on 21.11.2016.
 */


//Bütün Sınıflar için ortak Listview tıklamalarını dinler.
public class CommonListener<T extends CommonModul> implements AdapterView.OnItemClickListener {

    private List<T> list;
    private Fragment source_fragment,target_fragment;
    private T obje;

    public CommonListener(List<T> list, Fragment source_fragment,Fragment target_fragment){
        this.list = list;
        this.source_fragment = source_fragment;
        this.target_fragment = target_fragment;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        obje = list.get(i);
        Fragment mFragment = target_fragment;
        FragmentManager mFragmentManager = source_fragment.getFragmentManager();
        Bundle bundle = obje.getBundle();
        mFragment.setArguments(bundle);
        mFragmentManager.beginTransaction().replace(R.id.frame_container, mFragment).commit();
    }
}
