package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 31.10.2016.
 */

public class Urun extends CommonModul {

    private int id;
    private String ad;
    private String uretici_ad;
    private String tag_id;
    private String tanimAd;
    private String aciklama;
    private int doz;
    private int seans_tipi;
    private int seans_sayisi;
    private String kullanim_suresi;

    public Urun(int id,String ad, String tag_id, String tanimAd, String kullanim_suresi, int doz){
        this.id = id;
        this.ad = ad;
        this.tag_id = tag_id;
        this.tanimAd = tanimAd;
        this.kullanim_suresi = kullanim_suresi;
        this.doz = doz;
    }

    public Urun(String tag_id,String ad,int doz,String kullanim_suresi){
        this.tag_id = tag_id;
        this.ad = ad;
        this.doz = doz;
        this.kullanim_suresi = kullanim_suresi;
    }

    public Urun(String tag_id,String ad,int doz){
        this.tag_id = tag_id;
        this.ad = ad;
        this.doz = doz;
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

    public int getDoz() {
        return doz;
    }

    public int getSeans_sayisi() {
        return seans_sayisi;
    }

    public int getSeans_tipi() {
        return seans_tipi;
    }

    public String getAciklama() {
        return aciklama;
    }

    public String getKullanim_suresi() {
        return kullanim_suresi;
    }

    public String getTag_id() {
        return tag_id;
    }

    public String getTanimAd() {
        return tanimAd;
    }

    public String getUretici_ad() {
        return uretici_ad;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public void setDoz(int doz) {
        this.doz = doz;
    }

    public void setKullanim_suresi(String kullanim_suresi) {
        this.kullanim_suresi = kullanim_suresi;
    }

    public void setSeans_sayisi(int seans_sayisi) {
        this.seans_sayisi = seans_sayisi;
    }

    public void setSeans_tipi(int seans_tipi) {
        this.seans_tipi = seans_tipi;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public void setTanimAd(String tanimAd) {
        this.tanimAd = tanimAd;
    }

    public void setUretici_ad(String uretici_ad) {
        this.uretici_ad = uretici_ad;
    }

    @Override
    public String getDesc() {
        String desc = "Ürün No : " + getTag_id() + "\n" + "S.K.T : " +
                getKullanim_suresi() + "\nDoz Sayısı : " + getDoz();

        if (getTanimAd() != null)
            desc += "\nÜrün Tanımı : " + getTanimAd();

        return desc;
    }

    @Override
    public String getTitle() {
        return "Ürün Ad: "+getAd();
    }

    @Override
    public Bundle getBundle() {
        Bundle bundle = new Bundle();
        bundle.putInt("id_val", getId());
        bundle.putString("ad_val", getAd());
        return bundle;
    }
}
