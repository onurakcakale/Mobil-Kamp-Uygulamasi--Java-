package com.example.campus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campus.KampAramaActivity;
import com.example.campus.Model.Kamplar;
import com.example.campus.Model.Sehirler;
import com.example.campus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class SehirAdapter extends RecyclerView.Adapter<SehirAdapter.ViewHolder>
{
    private Context mContext;
    private List<Sehirler> mSehirler;
    private List<Kamplar> mKamplar;
    private FirebaseUser firebaseSehirler;

    public SehirAdapter(Context mContext, List<Sehirler> mSehirler)
    {
        this.mContext = mContext;
        this.mSehirler = mSehirler;
    }

    @NonNull
    @Override
    public SehirAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.sehir_ogesi, viewGroup,false);
        return new SehirAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SehirAdapter.ViewHolder viewHolder, int i)
    {
        firebaseSehirler = FirebaseAuth.getInstance().getCurrentUser();
        final Sehirler sehirler = mSehirler.get(i);
        viewHolder.sehirAd.setText(sehirler.getSehirAd());

        viewHolder.sehirAd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, KampAramaActivity.class);
                intent.putExtra("sehirid", sehirler.getSehirid());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return mSehirler.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView sehirAd;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            sehirAd = itemView.findViewById(R.id.sehir_adi);
        }
    }
}