package com.example.campus.Model;

public class Kamplar
{
    private String kampid;
    private String kampAd;
    private String kampDetay;
    private String kampUrl;
    private String sehirAd;
    private String sehirid;

    public Kamplar()
    {

    }

    public Kamplar(String kampid, String kampAd, String kampDetay, String kampUrl, String sehirAd, String sehirid)
    {
        this.kampid = kampid;
        this.kampAd = kampAd;
        this.kampDetay = kampDetay;
        this.kampUrl = kampUrl;
        this.sehirAd = sehirAd;
        this.sehirid = sehirid;
    }

    public String getKampid() {
        return kampid;
    }

    public void setKampid(String kampid) {
        this.kampid = kampid;
    }

    public String getKampAd() {
        return kampAd;
    }

    public void setKampAd(String kampAd) {
        this.kampAd = kampAd;
    }

    public String getKampDetay() {
        return kampDetay;
    }

    public void setKampDetay(String kampDetay) {
        this.kampDetay = kampDetay;
    }

    public String getKampUrl() {
        return kampUrl;
    }

    public void setKampUrl(String kampUrl) {
        this.kampUrl = kampUrl;
    }

    public String getSehirAd() {
        return sehirAd;
    }

    public void setSehirAd(String sehirAd) {
        this.sehirAd = sehirAd;
    }

    public String getSehirid() { return sehirid; }

    public void setSehirid(String sehirid) { this.sehirid = sehirid; }
}