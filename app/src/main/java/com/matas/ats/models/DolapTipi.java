package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 28.10.2016.
 */

public class DolapTipi extends CommonModul{

    private int id;
    private String ad;
    private int aktifMi;

    public DolapTipi(int id, String ad,int aktifMi){
        this.id = id;
        this.ad = ad;
        this.aktifMi = aktifMi;
    }

    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public int getAktifMi() {
        return aktifMi;
    }

    public void setAktifMi(int aktifMi) {
        this.aktifMi = aktifMi;
    }

    @Override
    public String toString() {
        return getAd();
    }

    @Override
    public String getDesc() {

        return (getAktifMi() == 1) ? "Aktif" : "Aktif Değil";
    }

    @Override
    public String getTitle() {
        return getAd();
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_val",getId());
        return bundle;
    }
}
