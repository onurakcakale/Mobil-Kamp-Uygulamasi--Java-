package com.example.campus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campus.GonderiActivity;
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

import java.util.List;

public class KampAdapter extends RecyclerView.Adapter<KampAdapter.ViewHolder>
{
    private Context mContext;
    private List<Kamplar> mKamplar;
    private List<Sehirler> mSehirler;

    private FirebaseUser firebaseSehirler;

    public KampAdapter(Context mContext, List<Kamplar> mKamplar, List<Sehirler> mSehirler)
    {
        this.mContext = mContext;
        this.mKamplar = mKamplar;
        this.mSehirler = mSehirler;
    }

    @NonNull
    @Override
    public KampAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.kamp_ogesi, viewGroup,false);
        return new KampAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KampAdapter.ViewHolder viewHolder, int i)
    {
        firebaseSehirler = FirebaseAuth.getInstance().getCurrentUser();
        final Kamplar kamplar = mKamplar.get(i);

        viewHolder.kampAd.setText(kamplar.getKampAd());
        viewHolder.sehirAd.setText(kamplar.getSehirAd());
        Glide.with(mContext).load(kamplar.getKampUrl()).into(viewHolder.kampResim);

        viewHolder.kamp_ogeleri.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, GonderiActivity.class);
                intent.putExtra("kampid", kamplar.getKampid());
                intent.putExtra("sehirid", kamplar.getSehirid());
                mContext.startActivity(intent);
            }
        });

        begeniSayisi(viewHolder.txt_begeni, kamplar.getKampid());
        yorumlariAl(kamplar.getKampid(), viewHolder.txt_yorum);
    }

    @Override
    public int getItemCount()
    {
        return mKamplar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView kampAd, sehirAd, txt_begeni, txt_yorum;
        LinearLayout kamp_ogeleri;
        public ImageView kampResim;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            kamp_ogeleri = itemView.findViewById(R.id.kamp_ogeleri);
            kampAd = itemView.findViewById(R.id.kamp_adi_ogesi);
            kampResim = itemView.findViewById(R.id.kamp_resmi_ogesi);
            sehirAd = itemView.findViewById(R.id.sehir_adi_kamp_ogesi);
            txt_begeni = itemView.findViewById(R.id.txt_begeni);
            txt_yorum = itemView.findViewById(R.id.txt_yorum);
        }
    }

    private void begeniSayisi(TextView begeniler, String kampid)
    {
        DatabaseReference begeniSayisiYolu = FirebaseDatabase.getInstance().getReference().child("Begeniler").child(kampid);
        begeniSayisiYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                begeniler.setText(dataSnapshot.getChildrenCount() + " Beğeni");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }

    private void yorumlariAl(String kampid, TextView yorumlar)
    {
        DatabaseReference yorumlariAlmaYolu = FirebaseDatabase.getInstance().getReference("Yorumlar").child(kampid);
        yorumlariAlmaYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                yorumlar.setText(dataSnapshot.getChildrenCount() + " Yorum");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
    }
}