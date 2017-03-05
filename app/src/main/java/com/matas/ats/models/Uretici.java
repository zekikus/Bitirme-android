package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 31.10.2016.
 */

public class Uretici extends CommonModul {

    private int id;
    private String ad;
    private String ulke;

    public Uretici(int id,String ad,String ulke){
        this.id = id;
        this.ad = ad;
        this.ulke = ulke;
    }

    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public String getUlke() {
        return ulke;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public void setUlke(String ulke) {
        this.ulke = ulke;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String getDesc() {
        return "Ülke: " + getUlke();
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
