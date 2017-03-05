package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 28.10.2016.
 */

public class TuketimNedeni extends CommonModul{

    private int id;
    private String tanim;
    private int aktifMi;

    public TuketimNedeni(int id, String tanim, int aktifMi){
        this.id = id;
        this.tanim = tanim;
        this.aktifMi = aktifMi;
    }

    public int getId() {
        return id;
    }


    public String getTanim() {
        return tanim;
    }

    public int getAktifMi() {
        return aktifMi;
    }

    public void setAktifMi(int aktifMi) {
        this.aktifMi = aktifMi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTanim(String tanim) {
        this.tanim = tanim;
    }

    @Override
    public String getDesc() {
        String aktif = (getAktifMi() == 1) ? "Aktif" : "Aktif Değil";
        return "Durum: " + aktif;
    }

    @Override
    public String getTitle() {
        return "Tanım: " + getTanim();
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_val", getId());
        return bundle;
    }
}
