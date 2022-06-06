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

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriAdapter extends RecyclerView.Adapter<FavoriAdapter.ViewHolder>
{
    private Context mContext;
    private List<Kamplar> mKamplar;

    private FirebaseUser firebaseSehirler;
    private FirebaseUser mevcutFirebaseUser;

    public FavoriAdapter(Context mContext, List<Kamplar> mKamplar)
    {
        this.mContext = mContext;
        this.mKamplar = mKamplar;
    }

    @NonNull
    @Override
    public FavoriAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.favori_ogesi, viewGroup,false);
        return new FavoriAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriAdapter.ViewHolder viewHolder, int i)
    {
        firebaseSehirler = FirebaseAuth.getInstance().getCurrentUser();
        mevcutFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Kamplar kamplar = mKamplar.get(i);

        viewHolder.kampAd.setText(kamplar.getKampAd());
        viewHolder.sehirAd.setText(kamplar.getSehirAd());
        Glide.with(mContext).load(kamplar.getKampUrl()).into(viewHolder.kampResim);

        viewHolder.favori_ogeleri.setOnClickListener(new View.OnClickListener()
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

        viewHolder.begeni_resmi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                FirebaseDatabase.getInstance().getReference().child("Begeniler").child(kamplar.getKampid()).child(mevcutFirebaseUser.getUid()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Favoriler").child(mevcutFirebaseUser.getUid()).child(kamplar.getKampid()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mKamplar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView kampAd, sehirAd, txt_begeni, txt_yorum;
        public LinearLayout favori_ogeleri;
        public ImageView begeni_resmi;
        public CircleImageView kampResim;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            favori_ogeleri = itemView.findViewById(R.id.favori_ogeleri);
            kampAd = itemView.findViewById(R.id.favori_kamp_ad);
            kampResim = itemView.findViewById(R.id.favori_kamp_resim);
            sehirAd = itemView.findViewById(R.id.favori_sehir_ad);
            begeni_resmi = itemView.findViewById(R.id.begeni_favori);
        }
    }
}