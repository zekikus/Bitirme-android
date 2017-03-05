package com.matas.ats.models;

/**
 * Created by Zeki on 28.10.2016.
 */

public class Ilce {

    private int id;
    private String ad;
    private int il_id;


    public Ilce(int id, String ad,int il_id){
        this.id = id;
        this.ad = ad;
        this.il_id = il_id;
    }

    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public int getIl_id() {
        return il_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public void setIl_id(int il_id) {
        this.il_id = il_id;
    }



    @Override
    public String toString() {
        return getAd();
    }
}
