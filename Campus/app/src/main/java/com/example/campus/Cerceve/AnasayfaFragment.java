package com.example.campus.Cerceve;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campus.Adapter.AnasayfaAdapter;
import com.example.campus.Model.Sehirler2;
import com.example.campus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnasayfaFragment extends Fragment
{
    private RecyclerView recyclerView;
    private AnasayfaAdapter anasayfaAdapter;
    private List<Sehirler2> mSehirler2;

    FirebaseUser mevcutKullanici;
    String sehirid;

    public AnasayfaFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_anasayfa, container, false);

        mevcutKullanici = FirebaseAuth.getInstance().getCurrentUser();
        SharedPreferences prefs = getContext().getSharedPreferences("PREPS", Context.MODE_PRIVATE);
        sehirid = prefs.getString("sehirid","none");

        recyclerView = view.findViewById(R.id.recyler_view_home_fragment);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        mSehirler2 = new ArrayList<>();
        anasayfaAdapter = new AnasayfaAdapter(getContext(), mSehirler2);
        recyclerView.setAdapter(anasayfaAdapter);

        sehirBilgisi();

        return view;
    }

    private void sehirBilgisi()
    {
        DatabaseReference sehirYolu = FirebaseDatabase.getInstance().getReference("sehirler2");
        sehirYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mSehirler2.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Sehirler2 sehirler2 = snapshot.getValue(Sehirler2.class);
                    mSehirler2.add(sehirler2);
                }

                anasayfaAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}