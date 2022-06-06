package com.example.campus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campus.Model.Kullanici;
import com.example.campus.Model.Yorum;
import com.example.campus.R;
import com.example.campus.YorumlarActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class YorumAdapter extends RecyclerView.Adapter<YorumAdapter.ViewHolder>
{
    private Context mContext;
    private List<Yorum> mYorumListesi;

    private FirebaseUser mevcutKullanici;

    public YorumAdapter(Context mContext, List<Yorum> mYorumListesi)
    {
        this.mContext = mContext;
        this.mYorumListesi = mYorumListesi;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.yorum_ogesi,parent,false);
        return new YorumAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        mevcutKullanici = FirebaseAuth.getInstance().getCurrentUser();

        final Yorum yorum = mYorumListesi.get(position);
        holder.txt_yorum.setText(yorum.getYorum());
        kullaniciBilgisiAl(holder.txt_kullaniciadi, yorum.getGonderen());
    }

    @Override
    public int getItemCount()
    {
        return mYorumListesi.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView txt_kullaniciadi, txt_yorum;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            txt_kullaniciadi = itemView.findViewById(R.id.txt_kullanici_adi_yorumOgesi);
            txt_yorum = itemView.findViewById(R.id.txt_yorumOgesi);
        }
    }

    private void kullaniciBilgisiAl(TextView kullaniciAdi, String gonderenId)
    {
        DatabaseReference gonderenIdYolu= FirebaseDatabase.getInstance().getReference().child("Kullanıcılar").child(gonderenId);
        gonderenIdYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                Kullanici kullanici = snapshot.getValue(Kullanici.class);
                kullaniciAdi.setText(kullanici.getKullaniciAdi());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }
}