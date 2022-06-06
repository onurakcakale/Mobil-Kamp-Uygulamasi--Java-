package com.example.campus.Model;

public class Favoriler
{
    private String kampid;
    private String sehirid;
    private String kampUrl;
    private String kampAd;
    private String sehirAd;

    public Favoriler(String kampUrl, String kampAd, String sehirAd, String kampid, String sehirid)
    {
        this.kampUrl = kampUrl;
        this.kampAd = kampAd;
        this.sehirAd = sehirAd;
        this.kampid = kampid;
        this.sehirid = sehirid;
    }

    public Favoriler()
    {

    }

    public String getKampid() {
        return kampid;
    }

    public void setKampid(String kampid) {
        this.kampid = kampid;
    }

    public String getSehirid() {
        return sehirid;
    }

    public void setSehirid(String sehirid) {
        this.sehirid = sehirid;
    }

    public String getKampUrl() {
        return kampUrl;
    }

    public void setKampUrl(String kampUrl) {
        this.kampUrl = kampUrl;
    }

    public String getKampAd() {
        return kampAd;
    }

    public void setKampAd(String kampAd) {
        this.kampAd = kampAd;
    }

    public String getSehirAd() {
        return sehirAd;
    }

    public void setSehirAd(String sehirAd) {
        this.sehirAd = sehirAd;
    }
}
