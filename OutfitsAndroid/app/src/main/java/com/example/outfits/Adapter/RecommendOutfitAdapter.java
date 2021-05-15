package com.example.outfits.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.outfits.Bean.RecommendClothes;
import com.example.outfits.MyApplication;
import com.example.outfits.R;

import java.util.List;

public class RecommendOutfitAdapter extends RecyclerView.Adapter<RecommendOutfitAdapter.ViewHolder> {
    Fragment fragment;
    List<RecommendClothes> clothesList;

    public RecommendOutfitAdapter(Fragment fragment,List<RecommendClothes> clothesList) {
        this.fragment = fragment;
        this.clothesList=clothesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_recommend_clothes,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        Glide.with(MyApplication.getContext())
                .load("http://"+clothesList.get(position).getClothingPic())
                .into(holder.clothesImage);
        holder.type.setText(clothesList.get(position).getTypeName());
        holder.subtype.setText(clothesList.get(position).getSubtypeName());
    }

    @Override
    public int getItemCount() {
        if(clothesList!= null){
            return clothesList.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView clothesImage;
        TextView type;
        TextView subtype;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clothesImage = itemView.findViewById(R.id.clothes_picture);
            type = itemView.findViewById(R.id.clothes_type);
            subtype= itemView.findViewById(R.id.clothes_subtype);
        }
    }
}
