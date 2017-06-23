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

    public Sicaklik(int id, int sensor_id, String sicaklik_deger,String kayit_zamani){
        this.id = id;
        this.sensor_id = sensor_id;
        this.sicaklik_deger = sicaklik_deger;
        this.kayit_zamani = kayit_zamani;
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

    @Override
    public String toString() {
        return getSicaklik_deger();
    }

    @Override
    public String getDesc() {
        return "Sensör ID: " + getSensor_id() + "\nKayıt Zamanı: " + getKayit_zamani();
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
