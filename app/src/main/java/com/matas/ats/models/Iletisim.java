package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 28.10.2016.
 */

public class Iletisim extends CommonModul{

    private int id;
    private String tip;
    private String deger;

    public Iletisim(int id, String tip, String deger){
        this.id = id;
        this.tip = tip;
        this.deger = deger;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getDeger() {
        return deger;
    }

    public void setDeger(String deger) {
        this.deger = deger;
    }

    @Override
    public String toString() {
        return getTip();
    }

    @Override
    public String getDesc() {
        return "İletişim Tipi: " + getTip();
    }

    @Override
    public String getTitle() {
        return "Değer: " + getDeger();
    }

    @Override
    public Bundle getBundle() {
        return null;
    }
}
