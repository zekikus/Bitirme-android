package com.matas.ats.models;

import android.os.Bundle;

import com.matas.ats.adapters.CommonMethods;

/**
 * Created by Zeki on 28.10.2016.
 */

public class Sicaklik extends CommonModul{

    private int id;
    private int sensor_id;
    private String sicaklik_deger;
    private String kayit_zamani;
    private String olcum_zamani;

    public Sicaklik(int id, int sensor_id, String sicaklik_deger,String kayit_zamani, String olcum_zamani){
        this.id = id;
        this.sensor_id = sensor_id;
        this.sicaklik_deger = sicaklik_deger;
        this.kayit_zamani = kayit_zamani;
        this.olcum_zamani = olcum_zamani;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getSicaklik_deger() {
        return sicaklik_deger;
    }

    public void setSicaklik_deger(String sicaklik_deger) {
        this.sicaklik_deger = sicaklik_deger;
    }

    public String getKayit_zamani() {
        return kayit_zamani;
    }

    public void setKayit_zamani(String kayit_zamani) {
        this.kayit_zamani = kayit_zamani;
    }

    public String getOlcum_zamani() {
        return olcum_zamani;
    }

    public void setOlcum_zamani(String olcum_zamani) {
        this.olcum_zamani = olcum_zamani;
    }

    @Override
    public String toString() {
        return getSicaklik_deger();
    }

    @Override
    public String getDesc() {
        return "Sensör ID: " + getSensor_id() + "\nKayıt Zamanı: " + getKayit_zamani() + "\nÖlçüm Zamanı: " + getOlcum_zamani();
    }

    @Override
    public String getTitle() {
        return "Sıcaklık: " + getSicaklik_deger();
    }

    @Override
    public Bundle getBundle() {
        return null;
    }
}
