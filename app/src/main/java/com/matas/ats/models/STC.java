package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Tarik on 28.10.2016.
 */

public class STC extends CommonModul{

    private int id;
    private String cihaz_durum;
    private int alarm_uret;
    private String stockbirim;
    private String olcum_zamani;

    public STC(int id, String stockbirim, String cihaz_durum, int alarm_uret){
        this.id = id;
        this.stockbirim = stockbirim;
        this.cihaz_durum = cihaz_durum;
        this.alarm_uret = alarm_uret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStockbirim_id() {
        return stockbirim;
    }

    public void setStockbirim_id(String stockbirim_id) {
        this.stockbirim = stockbirim;
    }

    public String getCihaz_durum() {
        return cihaz_durum;
    }

    public void setCihaz_durum(String cihaz_durum) {
        this.cihaz_durum = cihaz_durum;
    }

    public int getAlarm_uret() {return alarm_uret;
    }

    public void setAlarm_uret(int alarm_uret) {
        this.alarm_uret = alarm_uret;
    }

    @Override
    public int hashCode() {
        return getId();
    }
    @Override
    public String getDesc() {
        String alarmUret = (getAlarm_uret() == 1) ? "Aktif" : "Aktif Değil";
        return "Cihaz ID: "+ getId() + "\nStok birim: "+getStockbirim_id() + "\nAlarm Üret: " + alarmUret + "\nCihaz Durum: " + getCihaz_durum();
    }


    @Override
    public String getTitle() {
        return "Cihaz Stok Birim: " + getStockbirim_id();
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_val",getId());
        return bundle;

    }
}
