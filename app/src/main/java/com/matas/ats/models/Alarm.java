package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 31.10.2016.
 */

public class Alarm extends CommonModul {

    private int id;
    private int stok_birim_id;
    private int sensor_id;
    private String alarm_il;
    private String alarm_ilce;
    private String alarm_depo;
    private String tip;
    private String baslangic_zaman;
    private String bitis_zaman;
    private String durum;

    public Alarm(int id, int sensor_id, String tip, String baslangic_zaman , String bitis_zaman, String durum){
        this.id = id;
        this.sensor_id = sensor_id;
        this.tip = tip;
        this.baslangic_zaman = baslangic_zaman;
        this.bitis_zaman = bitis_zaman;
        this.durum = durum;
    }

    public Alarm(int id, String alarm_il, String alarm_ilce, String alarm_depo, int stok_birim_id, int sensor_id, String tip){
        this.id = id;
        this.alarm_il = alarm_il;
        this.alarm_ilce = alarm_ilce;
        this.alarm_depo = alarm_depo;
        this.stok_birim_id = stok_birim_id;
        this.sensor_id = sensor_id;
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStok_birim_id() {
        return stok_birim_id;
    }

    public void setStok_birim_id(int stok_birim_id) {
        this.stok_birim_id = stok_birim_id;
    }

    public int getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(int sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getAlarm_il() {
        return alarm_il;
    }

    public void setAlarm_il(String alarm_il) {
        this.alarm_il = alarm_il;
    }

    public String getAlarm_ilce() {
        return alarm_ilce;
    }

    public void setAlarm_ilce(String alarm_ilce) {
        this.alarm_ilce = alarm_ilce;
    }

    public String getAlarm_depo() {
        return alarm_depo;
    }

    public void setAlarm_depo(String alarm_depo) {
        this.alarm_depo = alarm_depo;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getBaslangic_zaman() {
        return baslangic_zaman;
    }

    public void setBaslangic_zaman(String baslangic_zaman) {
        this.baslangic_zaman = baslangic_zaman;
    }

    public String getBitis_zaman() {
        return bitis_zaman;
    }

    public void setBitis_zaman(String bitis_zaman) {
        this.bitis_zaman = bitis_zaman;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }

    @Override
    public String toString() {
        return getTip();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String getDesc() {
        return "Alarm No: " + getId() + "\nAlarm İl: "+ getAlarm_il() + "\nAlarm İlçe: "+ getAlarm_ilce() +
                "\nDepo: " + getAlarm_depo() + "\nSTC No: " +getSensor_id() + "\nStok Birim No: " + getStok_birim_id() + "\nAlarm Tipi : " + getTip();
    }

    @Override
    public String getTitle() {
        return getTip();
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_val",getId());
        return bundle;
    }
}
