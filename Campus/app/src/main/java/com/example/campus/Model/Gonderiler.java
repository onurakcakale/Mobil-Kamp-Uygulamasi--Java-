package com.example.campus.Model;

public class Gonderiler
{
    private String kampid;
    private String kampAd;
    private String kampDetay;
    private String kampUrl;
    private String sehirAd;

    public Gonderiler()
    {

    }

    public Gonderiler(String kampid, String kampAd, String kampDetay, String kampUrl, String sehirAd)
    {
        this.kampid = kampid;
        this.kampAd = kampAd;
        this.kampDetay = kampDetay;
        this.kampUrl = kampUrl;
        this.sehirAd = sehirAd;
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
}