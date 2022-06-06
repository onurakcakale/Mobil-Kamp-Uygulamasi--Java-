package com.example.campus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.campus.KampAramaActivity;
import com.example.campus.Model.Sehirler2;
import com.example.campus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AnasayfaAdapter extends RecyclerView.Adapter<AnasayfaAdapter.ViewHolder>
{
    public Context mContext;
    public List<Sehirler2> mSehirler2;

    private FirebaseUser mevcutFirebaseUser;

    public AnasayfaAdapter(Context mContext, List<Sehirler2> mSehirler2)
    {
        this.mContext = mContext;
        this.mSehirler2 = mSehirler2;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.home_ogesi, viewGroup, false);
        return new AnasayfaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        mevcutFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Sehirler2 sehirler2 = mSehirler2.get(i);

        Glide.with(mContext).load(sehirler2.getSehirUrl()).into(viewHolder.sehirUrl);

        viewHolder.sehirUrl.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, KampAramaActivity.class);
                intent.putExtra("sehirid", sehirler2.getSehirid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mSehirler2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView sehirUrl;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            sehirUrl = itemView.findViewById(R.id.sehir_image);
        }
    }
}