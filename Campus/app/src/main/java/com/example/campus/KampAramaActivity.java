package com.example.campus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.campus.Adapter.KampAdapter;
import com.example.campus.Model.Kamplar;
import com.example.campus.Model.Sehirler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KampAramaActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private KampAdapter kampAdapter;
    private List<Kamplar> mkamplar;
    private List<Sehirler> msehirler;

    FirebaseUser mevcutKullanici;
    String sehirid, kampid;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamp_arama);

        Toolbar toolbar = findViewById(R.id.toolbar_KampAramaActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        mevcutKullanici = FirebaseAuth.getInstance().getCurrentUser();
        Intent intent = getIntent();
        kampid = intent.getStringExtra("kampid");
        sehirid = intent.getStringExtra("sehirid");

        recyclerView = findViewById(R.id.recyler_view_kampArama);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mkamplar = new ArrayList<>();
        msehirler = new ArrayList<>();
        kampAdapter = new KampAdapter(this, mkamplar, msehirler);
        recyclerView.setAdapter(kampAdapter);

        kampOku();
    }

    private void kampOku()
    {
        DatabaseReference kampYolu = FirebaseDatabase.getInstance().getReference("kamplar").child(sehirid);
        kampYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (this == null)
                {
                    return;
                }

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Kamplar kamplar = snapshot.getValue(Kamplar.class);
                    mkamplar.add(kamplar);
                }

                kampAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}