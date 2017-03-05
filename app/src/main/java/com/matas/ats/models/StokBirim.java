package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 31.10.2016.
 */

public class StokBirim extends CommonModul {

    private int id;
    private String ad;
    private int sensor_id;
    private int birim_id;
    private String aciklama;
    private String hacim;
    private String marka;
    private String model;
    private String uretim_tarihi;
    private String tanim;
    private int sicaklik_alt_limit;
    private int sicaklik_ust_limit;

    public StokBirim(int id, String ad,String tanim,String aciklama,String hacim,int sensor_id){
        this.id = id;
        this.ad = ad;
        this.tanim = tanim;
        this.aciklama = aciklama;
        this.hacim = hacim;
        this.sensor_id = sensor_id;
    }

    public StokBirim(int id, String ad,int sensor_id,int birim_id, String aciklama,String hacim,String marka
                        ,String model, String uretim_tarihi, String tanim, int sicaklik_alt_limit, int sicaklik_ust_limit ){
        this.id = id;
        this.ad = ad;
        this.sensor_id = sensor_id;
        this.birim_id = birim_id;
        this.aciklama = aciklama;
        this.hacim = hacim;
        this.marka = marka;
        this.model = model;
        this.uretim_tarihi = uretim_tarihi;
        this.tanim = tanim;
        this.sicaklik_alt_limit = sicaklik_alt_limit;
        this.sicaklik_ust_limit = sicaklik_ust_limit;
    }

    public int getId() {
        return id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBirim_id() {
        return birim_id;
    }

    public void setBirim_id(int birim_id) {
        this.birim_id = birim_id;
    }

    public int getSicaklik_ust_limit() {
        return sicaklik_ust_limit;
    }

    public void setSicaklik_ust_limit(int sicaklik_ust_limit) {
        this.sicaklik_ust_limit = sicaklik_ust_limit;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getHacim() {
        return hacim;
    }

    public void setHacim(String hacim) {
        this.hacim = hacim;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUretim_tarihi() {
        return uretim_tarihi;
    }

    public void setUretim_tarihi(String uretim_tarihi) {
        this.uretim_tarihi = uretim_tarihi;
    }

    public String getTanim() {
        return tanim;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    public int getSicaklik_alt_limit() {
        return sicaklik_alt_limit;
    }

    public void setSicaklik_alt_limit(int sicaklik_alt_limit) {
        this.sicaklik_alt_limit = sicaklik_alt_limit;
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
        return "Stok Birim Tipi : " + getTanim() + "\n" + "Açıklama : " +
                getAciklama() + "\nHacim : " + getHacim();
    }

    @Override
    public String getTitle() {
        return getAd();
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_val",getId());
        bundle.putInt("sensor_id_val",getSensor_id());
        return bundle;
    }
}
