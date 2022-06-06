package com.example.campus.Cerceve;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.campus.Adapter.KampAdapter;
import com.example.campus.Adapter.SehirAdapter;
import com.example.campus.Model.Kamplar;
import com.example.campus.Model.Sehirler;
import com.example.campus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SehirAramaFragment extends Fragment
{
    private RecyclerView recyclerView;
    private SehirAdapter sehirAdapter;
    private List<Sehirler> msehirler;

    EditText arama_bar;

    public SehirAramaFragment()
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
        View view = inflater.inflate(R.layout.fragment_sehir_arama, container, false);
        recyclerView = view.findViewById(R.id.recyler_view_Arama);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arama_bar = view.findViewById(R.id.edt_arama_bar);
        msehirler = new ArrayList<>();
        sehirAdapter = new SehirAdapter(getContext(), msehirler);
        recyclerView.setAdapter(sehirAdapter);
        sehirleriOku();

        arama_bar.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                sehirAra(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        return view;
    }

    private void sehirAra(String s)
    {
        Query sorgu = FirebaseDatabase.getInstance().getReference("sehirler").orderByChild("sehirAd").startAt(s).endAt(s+"\uf8ff");
        sorgu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                msehirler.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Sehirler sehirler = snapshot.getValue(Sehirler.class);
                    msehirler.add(sehirler);
                }

                sehirAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    private void sehirleriOku()
    {
        DatabaseReference sehirYolu = FirebaseDatabase.getInstance().getReference("sehirler");
        sehirYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if(arama_bar.getText().toString().equals(""))
                {
                    msehirler.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Sehirler sehirler = snapshot.getValue(Sehirler.class);
                        msehirler.add(sehirler);
                    }

                    sehirAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}