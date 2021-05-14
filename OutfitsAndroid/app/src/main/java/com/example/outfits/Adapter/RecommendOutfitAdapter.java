package com.example.outfits.Adapter;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.RecommendClothes;
import com.example.outfits.MyApplication;
import com.example.outfits.R;

import java.util.List;

public class RecommendOutfitAdapter extends RecyclerView.Adapter<RecommendOutfitAdapter.ViewHolder> {
    Fragment fragment;
    private final List<RecommendClothes> clothesList;

    public RecommendOutfitAdapter(Fragment fragment,List<RecommendClothes> clothesList) {
        this.fragment = fragment;
        this.clothesList=clothesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_recommend_clothes, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
        Glide.with(MyApplication.getContext())
                .load(clothesList.get(position).getClothingPic())
                .into(holder.clothesImage);
        holder.type.setText(clothesList.get(position).getClothingType());
        holder.subtype.setText(clothesList.get(position).getClothingSubtype());
    }

    @Override
    public int getItemCount() {
        if(clothesList!= null){
            return clothesList.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView clothesImage;
        private final TextView type;
        private final TextView subtype;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            clothesImage = itemView.findViewById(R.id.clothes_picture);
            type = itemView.findViewById(R.id.clothes_type);
            subtype= itemView.findViewById(R.id.clothes_subtype);
        }
    }
}
