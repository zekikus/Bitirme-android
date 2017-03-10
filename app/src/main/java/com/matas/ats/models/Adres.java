package com.matas.ats.models;

import android.os.Bundle;

/**
 * Created by Zeki on 28.10.2016.
 */

public class Adres extends CommonModul{

    private int id;
    private int kullanici_id;
    private String il;
    private String ilce;
    private String acik_adres;

    public Adres(int id, int kullanici_id, String il, String ilce, String acik_adres){
        this.id = id;
        this.kullanici_id = kullanici_id;
        this.il = il;
        this.ilce = ilce;
        this.acik_adres = acik_adres;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKullanici_id() {
        return kullanici_id;
    }

    public void setKullanici_id(int kullanici_id) {
        this.kullanici_id = kullanici_id;
    }

    public String getIl() {
        return il;
    }

    public void setIl(String il) {
        this.il = il;
    }

    public String getIlce() {
        return ilce;
    }

    public void setIlce(String ilce) {
        this.ilce = ilce;
    }

    public String getAcik_adres() {
        return acik_adres;
    }

    public void setAcik_adres(String acik_adres) {
        this.acik_adres = acik_adres;
    }

    @Override
    public String toString() {
        return getIl() + " " + getIlce();
    }

    @Override
    public String getDesc() {
        return "Açık Adres: " + getAcik_adres();
    }

    @Override
    public String getTitle() {
        return "Adres: " + getIl() + " " + getIlce();
    }

    @Override
    public Bundle getBundle() {
        return null;
    }
}
