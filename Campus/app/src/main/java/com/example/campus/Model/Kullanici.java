package com.example.campus.Model;

public class Kullanici
{
    private String id;
    private String kullaniciAdi;
    private String Ad;
    private String Soyad;
    private String Sifre;
    private String Email;


    public Kullanici()
    {

    }

    public Kullanici(String id, String kullaniciAdi, String ad, String soyad, String sifre, String email)
    {
        this.id = id;
        this.kullaniciAdi = kullaniciAdi;
        Ad = ad;
        Soyad = soyad;
        Sifre = sifre;
        Email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getAd() {
        return Ad;
    }

    public void setAd(String ad) {
        Ad = ad;
    }

    public String getSoyad() {
        return Soyad;
    }

    public void setSoyad(String soyad) {
        Soyad = soyad;
    }

    public String getSifre() {
        return Sifre;
    }

    public void setSifre(String sifre) {
        Sifre = sifre;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }
}
