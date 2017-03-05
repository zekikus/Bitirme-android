package com.matas.ats.models;

/**
 * Created by Zeki on 28.10.2016.
 */

public class Sehir {

    private int id;
    private String ad;


    public Sehir(int id, String ad){
        this.id = id;
        this.ad = ad;
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

    @Override
    public String toString() {
        return getAd();
    }
}
