package com.example.campus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class KaydolActivity extends AppCompatActivity
{
    EditText edt_email, edt_kullaniciadi, edt_ad, edt_soyad, edt_sifresi;
    Button btn_kaydol;
    TextView txt_girisSayfasinaGit;
    FirebaseAuth Yetki;
    DatabaseReference yol;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaydol);

        edt_email = findViewById(R.id.edt_Email_kaydol);
        edt_kullaniciadi = findViewById(R.id.edt_kullaniciAdi_kaydol);
        edt_ad = findViewById(R.id.edt_Ad_kaydol);
        edt_soyad = findViewById(R.id.edt_soyAd_kaydol);
        edt_sifresi = findViewById(R.id.edt_Sifre_kaydol);
        btn_kaydol = findViewById(R.id.btn_kaydol_activity_kaydol);
        txt_girisSayfasinaGit = findViewById(R.id.txt_Giris_git_kaydol);
        Yetki = FirebaseAuth.getInstance();

        txt_girisSayfasinaGit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(KaydolActivity.this, MainActivity.class));
            }
        });

        //Yeni Kullanıcı Kaydetme Kodları
        btn_kaydol.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                pd = new ProgressDialog(KaydolActivity.this);
                pd.setMessage("Lütfen Bekleyin");
                pd.show();

                String str_kullaniciAdi = edt_kullaniciadi.getText().toString();
                String str_email = edt_email.getText().toString();
                String str_ad = edt_ad.getText().toString();
                String str_soyad = edt_soyad.getText().toString();
                String str_sifre = edt_sifresi.getText().toString();

                if (TextUtils.isEmpty(str_kullaniciAdi) || TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_ad) || TextUtils.isEmpty(str_soyad) || TextUtils.isEmpty(str_sifre))
                {
                    Toast.makeText(KaydolActivity.this, "Lütfen Bütün Alanları Doldurun.", Toast.LENGTH_SHORT).show();
                }
                else if (str_sifre.length() < 6)
                {
                    Toast.makeText(KaydolActivity.this, "Şifreniz minumum 6 karaktere sahip olmalı...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Kaydet(str_kullaniciAdi, str_email, str_ad, str_soyad, str_sifre);
                }
            }
        });
    }

    private void Kaydet(String kullaniciadi, String email, String ad, String soyad, String sifre)
    {
        Yetki.createUserWithEmailAndPassword(email, sifre).addOnCompleteListener(KaydolActivity.this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    FirebaseUser firebaseKullanici = Yetki.getCurrentUser();
                    String kullaniciid = firebaseKullanici.getUid();
                    yol = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(kullaniciid);
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", kullaniciid);
                    hashMap.put("kullaniciAdi", kullaniciadi.toLowerCase());
                    hashMap.put("Ad", ad);
                    hashMap.put("Soyad", soyad);
                    hashMap.put("Sifre", sifre);
                    hashMap.put("Email", email);

                    yol.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if(task.isSuccessful())
                            {
                                pd.dismiss();
                                Intent intent = new Intent(KaydolActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });
                }

                else
                {
                    pd.dismiss();
                    Toast.makeText(KaydolActivity.this, "Bu e-posta veya şifre ile kayıt başarısız", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}