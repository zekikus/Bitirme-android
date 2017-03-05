package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 28.10.2016.
 */

public class Kullanici extends CommonModul {

    private int id;
    private int tcNo;
    private String ad;
    private String soyad;
    private String tip;
    private String kullaniciAdi;
    private String kullaniciSifre;
    private int birim_id;


    public Kullanici(String ad,String soyad, String tip){
        this.ad = ad;
        this.soyad = soyad;
        this.tip = tip;
    }

    public Kullanici(int id, String ad, String soyad, String tip) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTcNo() {
        return tcNo;
    }

    public void setTcNo(int tcNo) {
        this.tcNo = tcNo;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getKullaniciSifre() {
        return kullaniciSifre;
    }

    public void setKullaniciSifre(String kullaniciSifre) {
        this.kullaniciSifre = kullaniciSifre;
    }

    public int getBirim_id() {
        return birim_id;
    }

    public void setBirim_id(int birim_id) {
        this.birim_id = birim_id;
    }

    @Override
    public String getDesc() {
        return  "Tip:" + getTip();
    }

    @Override
    public String getTitle() {
        return getAd() + " " + getSoyad();
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_val", getId());
        return bundle;
    }
}
