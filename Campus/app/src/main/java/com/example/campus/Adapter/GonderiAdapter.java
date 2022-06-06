package com.example.campus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.campus.Model.Kamplar;
import com.example.campus.R;
import com.example.campus.YorumlarActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class GonderiAdapter extends RecyclerView.Adapter<GonderiAdapter.ViewHolder>
{
    public Context mContext;
    public List<Kamplar> mKamplar;
    DatabaseReference yol;

    final List<SlideModel> remoteimages = new ArrayList<>();

    ImageSlider imageSlider;

    private FirebaseUser mevcutFirebaseUser;

    public GonderiAdapter(Context mContext, List<Kamplar> mKamplar)
    {
        this.mContext = mContext;
        this.mKamplar = mKamplar;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gonderi_ogesi, viewGroup, false);
        return new GonderiAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        mevcutFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final Kamplar kamplar = mKamplar.get(i);

        viewHolder.kampAd.setText(kamplar.getKampAd());
        viewHolder.kampDetay.setText(kamplar.getKampDetay());
        viewHolder.sehirAd.setText(kamplar.getSehirAd());

        slideAl(kamplar.getSehirid(), kamplar.getKampid());
        yorumlariAl(kamplar.getKampid(), viewHolder.txt_yorum);
        begenileriAl(kamplar.getKampid(), viewHolder.begeni_resmi);
        begeniSayisi(viewHolder.txt_begeni, kamplar.getKampid());

        viewHolder.begeni_resmi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (viewHolder.begeni_resmi.getTag().equals("Begen"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Begeniler").child(kamplar.getKampid()).child(mevcutFirebaseUser.getUid()).setValue(true);
                    String str_kampAd = kamplar.getKampAd().toString();
                    String str_kampUrl = kamplar.getKampUrl().toString();
                    String str_sehirAd = kamplar.getSehirAd().toString();
                    String str_kampid = kamplar.getKampid().toString();
                    String str_sehirid = kamplar.getSehirid().toString();
                    Kaydet(str_kampAd, str_sehirAd, str_kampid, str_sehirid, str_kampUrl);
                }

                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Begeniler").child(kamplar.getKampid()).child(mevcutFirebaseUser.getUid()).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Favoriler").child(mevcutFirebaseUser.getUid()).child(kamplar.getKampid()).removeValue();

                }
            }
        });

        viewHolder.yorum_resmi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, YorumlarActivity.class);
                intent.putExtra("kampid", kamplar.getKampid());
                mContext.startActivity(intent);
            }
        });

        viewHolder.txt_yorum.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(mContext, YorumlarActivity.class);
                intent.putExtra("kampid", kamplar.getKampid());
                mContext.startActivity(intent);
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
        public ImageView kampResim, begeni_resmi, yorum_resmi;
        public TextView txt_begeni, txt_yorum, kampAd, kampDetay, sehirAd;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageSlider = itemView.findViewById(R.id.image_slider);
            begeni_resmi = itemView.findViewById(R.id.begeni_resmi_gonderi);
            yorum_resmi = itemView.findViewById(R.id.yorum_resmi_gonderi);
            txt_begeni = itemView.findViewById(R.id.txt_begeni_gonderi);
            txt_yorum = itemView.findViewById(R.id.txt_yorum_gonderi);
            kampAd = itemView.findViewById(R.id.kamp_adi_gonderi);
            kampDetay = itemView.findViewById(R.id.kamp_detayi_gonderi);
            sehirAd = itemView.findViewById(R.id.sehir_adi_gonderi);
        }
    }

    private void Kaydet(String kampAd, String sehirAd, String kampid, String sehirid, String kampUrl)
    {
        String kullaniciid = mevcutFirebaseUser.getUid();
        yol = FirebaseDatabase.getInstance().getReference().child("Favoriler").child(kullaniciid).child(kampid);
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("kampid", kampid);
        hashMap.put("kampUrl", kampUrl);
        hashMap.put("sehirid", sehirid);
        hashMap.put("kampAd", kampAd);
        hashMap.put("sehirAd", sehirAd);

        yol.setValue(hashMap).addOnCompleteListener(new com.google.android.gms.tasks.OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {

                }
            }
        });
    }

    private void slideAl(String sehirid, String kampid)
    {
        DatabaseReference slideAlmaYolu = FirebaseDatabase.getInstance().getReference("kamplar").child(sehirid).child(kampid);
        slideAlmaYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                remoteimages.add(new SlideModel(dataSnapshot.child("kampUrl").getValue().toString(), ScaleTypes.FIT));
                remoteimages.add(new SlideModel(dataSnapshot.child("kampUrl2").getValue().toString(), ScaleTypes.FIT));
                remoteimages.add(new SlideModel(dataSnapshot.child("kampUrl3").getValue().toString(), ScaleTypes.FIT));

                imageSlider.setImageList(remoteimages, ScaleTypes.FIT);
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

    private void begenileriAl(String kampid, ImageView imageView)
    {
        FirebaseUser mevcutKullanici = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference begeniYolu = FirebaseDatabase.getInstance().getReference().child("Begeniler").child(kampid);
        begeniYolu.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.child(mevcutKullanici.getUid()).exists())
                {
                    imageView.setImageResource(R.drawable.ic_like2);
                    imageView.setTag("Begenildi");
                }

                else
                {
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("Begen");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {

            }
        });
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
}