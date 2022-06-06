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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    EditText edt_email_Giris, edt_sifre_Giris;
    Button btn_giris_Yap;
    TextView txt_kayitSayfasinaGit;
    FirebaseAuth girisYetkisi;
    FirebaseUser mevcutKullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_email_Giris = findViewById(R.id.edt_email_giris);
        edt_sifre_Giris = findViewById(R.id.edt_Sifre_giris);
        btn_giris_Yap = findViewById(R.id.btn_giris);
        txt_kayitSayfasinaGit = findViewById(R.id.txt_Kaydol_git_giris);
        girisYetkisi = FirebaseAuth.getInstance();
        mevcutKullanici = girisYetkisi.getCurrentUser();

        txt_kayitSayfasinaGit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this, KaydolActivity.class));
            }
        });

        //Giriş Yapma Kodları
        btn_giris_Yap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ProgressDialog pdGiris = new ProgressDialog(MainActivity.this);
                pdGiris.setMessage("Giriş yapılıyor...");
                pdGiris.show();

                String str_emailGiris = edt_email_Giris.getText().toString();
                String str_sifre = edt_sifre_Giris.getText().toString();

                if (TextUtils.isEmpty(str_emailGiris) || TextUtils.isEmpty(str_sifre))
                {
                    Toast.makeText(MainActivity.this, "Bütün alanları doldurun...", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    girisYetkisi.signInWithEmailAndPassword(str_emailGiris, str_sifre).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                DatabaseReference yolGiris = FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(girisYetkisi.getCurrentUser().getUid());
                                yolGiris.addValueEventListener(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot)
                                    {
                                        Intent intent = new Intent(MainActivity.this, AnasayfaActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError)
                                    {
                                        pdGiris.dismiss();
                                    }
                                });
                            }

                            else
                            {
                                pdGiris.dismiss();
                                Toast.makeText(MainActivity.this, "Giriş başarısız oldu...", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        if(mevcutKullanici != null)
        {
            Intent intent = new Intent(MainActivity.this, AnasayfaActivity.class);
            startActivity(intent);
            finish();
        }

        super.onStart();
    }
}