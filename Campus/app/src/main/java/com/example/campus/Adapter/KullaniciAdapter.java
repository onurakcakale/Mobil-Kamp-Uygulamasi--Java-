package com.example.campus.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.campus.Cerceve.ProfilFragment;
import com.example.campus.Model.Kullanici;
import com.example.campus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class KullaniciAdapter extends RecyclerView.Adapter<KullaniciAdapter.ViewHolder>{

    private Context mContext;
    private List<Kullanici> mKullanicilar;

    private FirebaseUser firebaseKullanici;

    public KullaniciAdapter(Context mContext, List<Kullanici> mKullanicilar)
    {
        this.mContext = mContext;
        this.mKullanicilar = mKullanicilar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType)
    {
        View view= LayoutInflater.from(mContext).inflate(R.layout.fragment_profil, viewGroup,false);

        return new KullaniciAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        firebaseKullanici = FirebaseAuth.getInstance().getCurrentUser();

        final Kullanici kullanici = mKullanicilar.get(i);

        viewHolder.kullaniciAdi.setText(kullanici.getKullaniciAdi());
        viewHolder.ad.setText(kullanici.getAd());


        viewHolder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREPS", Context.MODE_PRIVATE).edit();
                editor.putString("profilid", kullanici.getId());
                editor.apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.cerceve_kapsayici, new ProfilFragment()).commit();
            }
        });
    }


    @Override
    public int getItemCount()
    {
        return mKullanicilar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView kullaniciAdi;
        public TextView ad, soyad;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            kullaniciAdi = itemView.findViewById(R.id.txt_kullanici_adi_profil);
            ad = itemView.findViewById(R.id.txt_adi_profil);
            soyad = itemView.findViewById(R.id.txt_soyadi_profil);
        }
    }
}