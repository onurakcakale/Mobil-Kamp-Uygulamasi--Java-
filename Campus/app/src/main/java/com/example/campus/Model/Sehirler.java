package com.example.campus.Model;

public class Sehirler
{
    private String sehirid;
    private String sehirAd;

    public Sehirler()
    {

    }

    public Sehirler(String sehirid, String sehirAd)
    {
        this.sehirid = sehirid;
        this.sehirAd = sehirAd;
    }

    public String getSehirid() {
        return sehirid;
    }

    public void setSehirid(String sehirid) {
        this.sehirid = sehirid;
    }

    public String getSehirAd() {
        return sehirAd;
    }

    public void setSehirAd(String sehirAd) {
        this.sehirAd = sehirAd;
    }
}