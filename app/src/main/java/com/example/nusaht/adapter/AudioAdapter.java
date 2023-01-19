package com.example.nusaht.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nusaht.R;
import com.example.nusaht.models.AudioModel;

import java.util.List;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.RVHolderRetrofit>{

    Context context;
    List<AudioModel> audioModels;

    public AudioAdapter (Context context, List<AudioModel> audioResult) {
     this.context = context;
     this.audioModels = audioResult;
    }

    @NonNull
    @Override
    public RVHolderRetrofit onCreateViewHolder(ViewGroup parent,int ViewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.audio_res,parent,false);
        return new RVHolderRetrofit(view);
    }

    @Override
    public void onBindViewHolder(RVHolderRetrofit holder,int position)
    {
        holder.audio_name.setText(audioModels.get(position).getAudios__name());
        holder.annotation.setText(audioModels.get(position).getAudios__annotation());
        holder.language__name.setText(audioModels.get(position).getAudios__language__name());
        Glide.with(context).
                load(audioModels.get(position)).placeholder(R.drawable.sd).
                error(R.drawable.soldier).
                into(holder.ivImage);
    }

    @Override
    public int getItemCount() {return audioModels.size();}
    public class RVHolderRetrofit extends RecyclerView.ViewHolder {
        TextView audio_name;
        TextView annotation;
        TextView language__name;
        ImageView ivImage;

        public RVHolderRetrofit(View itemView) {
            super(itemView);
            audio_name = itemView.findViewById(R.id.audio_name);
            annotation = itemView.findViewById(R.id.annotation);
            language__name = itemView.findViewById(R.id.language__name);
            ivImage = itemView.findViewById(R.id.audio_image);
        }
    }
}
