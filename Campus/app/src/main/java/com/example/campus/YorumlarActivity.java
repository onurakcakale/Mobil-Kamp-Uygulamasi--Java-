package com.example.campus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.campus.Adapter.YorumAdapter;
import com.example.campus.Model.Yorum;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YorumlarActivity extends AppCompatActivity
{
    private RecyclerView recyclerView;
    private YorumAdapter yorumAdapter;
    private List<Yorum> yorumListesi;

    EditText edt_yorum_ekle;
    TextView txt_gonder, txt_kullaniciadi;
    String kampId, gonderenId;
    FirebaseUser mevcutKullanici;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yorumlar);

        Toolbar toolbar = findViewById(R.id.toolbar_yorumlarActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Yorumlar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyler_view_YorumlarActivity);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        yorumListesi = new ArrayList<>();
        yorumAdapter = new YorumAdapter(this, yorumListesi);
        recyclerView.setAdapter(yorumAdapter);

        edt_yorum_ekle = findViewById(R.id.edt_yorumEkle_yorumlarActivity);
        txt_gonder = findViewById(R.id.txt_gonder_yorumlarActivity);
        mevcutKullanici = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent = getIntent();
        kampId = intent.getStringExtra("kampid");
        gonderenId = intent.getStringExtra("gonderenid");

        txt_gonder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (edt_yorum_ekle.getText().toString().equals(""))
                {
                    Toast.makeText(YorumlarActivity.this, "Yorum kısmı boş bırakılamaz...", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    yorumEkle();
                }
            }
        });

        yorumlariOku();
    }

    private void yorumEkle()
    {
        DatabaseReference yorumlarYolu = FirebaseDatabase.getInstance().getReference("Yorumlar").child(kampId);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("yorum", edt_yorum_ekle.getText().toString());
        hashMap.put("gonderen", mevcutKullanici.getUid());

        yorumlarYolu.push().setValue(hashMap);
        edt_yorum_ekle.setText("");
    }

    private void yorumlariOku()
    {
        DatabaseReference yorumlariOkumaYolu = FirebaseDatabase.getInstance().getReference("Yorumlar").child(kampId);
        yorumlariOkumaYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                yorumListesi.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Yorum yorum = snapshot.getValue(Yorum.class);
                    yorumListesi.add(yorum);
                }

                yorumAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}