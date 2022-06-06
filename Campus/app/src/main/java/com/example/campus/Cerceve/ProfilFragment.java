package com.example.campus.Cerceve;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campus.MainActivity;
import com.example.campus.Model.Kullanici;
import com.example.campus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilFragment extends Fragment
{
    TextView txt_kullanici_adi, txt_adi, txt_soyadi, txt_email;
    String profilId;
    Button cikis_yap;
    FirebaseAuth.AuthStateListener mevcutKullanici;

    public ProfilFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        SharedPreferences prefs = getContext().getSharedPreferences("PREPS", Context.MODE_PRIVATE);
        profilId = prefs.getString("profilid", "none");

        txt_kullanici_adi = view.findViewById(R.id.txt_kullanici_adi_profil);
        txt_adi = view.findViewById(R.id.txt_adi_profil);
        txt_soyadi = view.findViewById(R.id.txt_soyadi_profil);
        txt_email = view.findViewById(R.id.txt_email_profil);

        cikis_yap = view.findViewById(R.id.cikis_yap_profil);

        cikis_yap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseAuth.getInstance().signOut();
            }
        });

        setupFirebaseListener();
        kullaniciBilgisi();

        return view;
    }

    private void setupFirebaseListener()
    {
        mevcutKullanici = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
            {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null)
                {

                }

                else
                {
                    Toast.makeText(getActivity(), "Çıkış Yapıldı...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        };
    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mevcutKullanici);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        if (mevcutKullanici != null)
        {
            FirebaseAuth.getInstance().removeAuthStateListener(mevcutKullanici);
        }
    }

    private void kullaniciBilgisi()
    {
        DatabaseReference kullaniciBilgisiYolu = FirebaseDatabase.getInstance().getReference("Kullanıcılar").child(profilId);
        kullaniciBilgisiYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Kullanici kullanici = dataSnapshot.getValue(Kullanici.class);
                txt_kullanici_adi.setText(kullanici.getKullaniciAdi());
                txt_adi.setText(kullanici.getAd());
                txt_soyadi.setText(kullanici.getSoyad());
                txt_email.setText(kullanici.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}