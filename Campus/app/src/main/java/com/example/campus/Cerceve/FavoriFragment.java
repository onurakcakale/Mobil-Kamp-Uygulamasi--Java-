package com.example.campus.Cerceve;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.campus.Adapter.FavoriAdapter;
import com.example.campus.Adapter.KampAdapter;
import com.example.campus.Adapter.SehirAdapter;
import com.example.campus.Model.Favoriler;
import com.example.campus.Model.Kamplar;
import com.example.campus.Model.Sehirler;
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

public class FavoriFragment extends Fragment
{
    private RecyclerView recyclerView;
    private FavoriAdapter favoriAdapter;
    private List<Kamplar> mKamplar;

    String kampid;

    public FavoriFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_favori, container, false);

        recyclerView = view.findViewById(R.id.recyler_view_favori);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mKamplar = new ArrayList<>();
        favoriAdapter = new FavoriAdapter(getContext(), mKamplar);
        recyclerView.setAdapter(favoriAdapter);

        favorileriOku();

        return view;
    }

    private void favorileriOku()
    {
        FirebaseUser mevcutKullanici = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference favoriYolu = FirebaseDatabase.getInstance().getReference("Favoriler").child(mevcutKullanici.getUid());
        favoriYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                mKamplar.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Kamplar kamplar = snapshot.getValue(Kamplar.class);
                    mKamplar.add(kamplar);
                }

                favoriAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}