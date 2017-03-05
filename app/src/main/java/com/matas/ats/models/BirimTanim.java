package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 31.10.2016.
 */

public class BirimTanim extends CommonModul {

    private int id;
    private String ad;
    private String il;
    private String ilce;
    private String adres;

    public BirimTanim(int id, String ad, String il,String ilce,String adres){
        this.id = id;
        this.ad = ad;
        this.il = il;
        this.ilce = ilce;
        this.adres = adres;
    }

    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public String getAdres() {
        return adres;
    }

    public String getIl() {
        return il;
    }

    public String getIlce() {
        return ilce;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setIl(String il) {
        this.il = il;
    }

    public void setIlce(String ilce) {
        this.ilce = ilce;
    }

    @Override
    public String toString() {
        return getAd();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String getDesc() {
        return "Birim İl : " + getIl() + "\nBirim İlçe : " + getIlce() + "\n" + "Adres : " +
                getAdres();
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
