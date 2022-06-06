package com.example.campus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

//import com.example.campus.Adapter.KullaniciAdapter;
import com.example.campus.Cerceve.FavoriFragment;
import com.example.campus.Cerceve.SehirAramaFragment;
import com.example.campus.Cerceve.AnasayfaFragment;
import com.example.campus.Cerceve.ProfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AnasayfaActivity extends AppCompatActivity
{
    BottomNavigationView bottomNavigationView;
    Fragment seciliCerceve = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici, new AnasayfaFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener()
    {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            switch (item.getItemId())
            {
                case R.id.nav_home:
                    seciliCerceve = new AnasayfaFragment();
                    break;

                case R.id.nav_arama:
                    seciliCerceve = new SehirAramaFragment();
                    break;

                case R.id.nav_favori:
                    SharedPreferences.Editor editor = getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                    editor.putString("profilid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    editor.apply();
                    seciliCerceve = new FavoriFragment();
                    break;

                case R.id.nav_profil:
                    SharedPreferences.Editor editor2 = getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                    editor2.putString("profilid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    editor2.apply();
                    seciliCerceve = new ProfilFragment();
                    break;
            }

            if (seciliCerceve != null)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici, seciliCerceve).commit();
            }
            return true;
        }
    };
}