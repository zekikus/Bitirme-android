package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 31.10.2016.
 */

public class UrunTipi extends CommonModul {

    private int id;
    private String urun_ad;
    private String urun_tipi;
    private String urun_aciklama;

    public UrunTipi(int id, String urun_ad, String urun_tipi, String urun_aciklama){
        this.id = id;
        this.urun_ad = urun_ad;
        this.urun_tipi = urun_tipi;
        this.urun_aciklama = urun_aciklama;

    }

    public int getId() {
        return id;
    }

    public String getUrun_ad() {
        return urun_ad;
    }

    public void setUrun_ad(String urun_ad) {
        this.urun_ad = urun_ad;
    }

    public String getUrun_tipi() {
        return urun_tipi;
    }

    public String getUrun_aciklama() {
        return urun_aciklama;
    }

    public void setUrun_tipi(String urun_tipi) {
        this.urun_tipi = urun_tipi;
    }

    public void setUrun_aciklama(String urun_aciklama) {
        this.urun_aciklama = urun_aciklama;
    }

    @Override
    public String getDesc() {
       return "Ürün Tipi: "+ getUrun_tipi() + "\nÜrün Açıklama: " + getUrun_aciklama();
    }

    @Override
    public String getTitle() {
        return getUrun_ad();
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_val", getId());
        return bundle;
    }
}
