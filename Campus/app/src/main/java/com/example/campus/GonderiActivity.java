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
import android.widget.ImageView;

import com.example.campus.Adapter.GonderiAdapter;
import com.example.campus.Model.Kamplar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GonderiActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private GonderiAdapter gonderiAdapter;
    private List<Kamplar> mkamplar;

    FirebaseUser mevcutKullanici;
    String kampid, sehirid;
    ImageView btn_begeni;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gonderi);

        Toolbar toolbar = findViewById(R.id.toolbar_gonderiActivity);
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

        recyclerView = findViewById(R.id.recyler_view_activity_gonderi);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mkamplar = new ArrayList<>();
        gonderiAdapter = new GonderiAdapter(this, mkamplar);
        recyclerView.setAdapter(gonderiAdapter);

        kampBilgisi();
    }

    private void kampBilgisi()
    {
        DatabaseReference kampYolu = FirebaseDatabase.getInstance().getReference("kamplar").child(sehirid).child(kampid);
        kampYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mkamplar.clear();
                Kamplar kamplar = dataSnapshot.getValue(Kamplar.class);
                mkamplar.add(kamplar);

                gonderiAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}