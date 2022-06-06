package com.example.campus.Model;

public class Sehirler2
{
    private String sehirid;
    private String sehirUrl;

    public Sehirler2()
    {

    }

    public Sehirler2(String sehirid, String sehirUrl)
    {
        this.sehirid = sehirid;
        this.sehirUrl = sehirUrl;
    }

    public String getSehirid() {
        return sehirid;
    }

    public void setSehirid(String sehirid) {
        this.sehirid = sehirid;
    }

    public String getSehirUrl() {
        return sehirUrl;
    }

    public void setSehirAd(String sehirUrl) {
        this.sehirUrl = sehirUrl;
    }
}