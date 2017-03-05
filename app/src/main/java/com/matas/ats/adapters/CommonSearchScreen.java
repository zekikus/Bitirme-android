package com.matas.ats.adapters;

import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.matas.ats.R;

import org.json.JSONException;

/**
 * Created by Zeki on 5.02.2017.
 */

//Bütün Sınıflar için Ortak Olan Filtreleme İşlemleri için Tasarım Gerçekleştirilir.
public abstract class CommonSearchScreen {

    private BottomSheetDialog bottomSheetDialog;

    public View buildSearchScreen(LayoutInflater inflater,View rootView){
        bottomSheetDialog = new BottomSheetDialog(rootView.getContext());

        View bottomSheetView = inflater.inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        return bottomSheetView;
    }

    public BottomSheetDialog getBottomSheetDialog() {
        return bottomSheetDialog;
    }

    public abstract void buildFilterPanel() throws JSONException;

}
