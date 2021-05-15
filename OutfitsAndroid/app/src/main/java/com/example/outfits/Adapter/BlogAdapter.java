package com.example.outfits.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
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
import com.example.outfits.R;

import java.util.List;

public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.InnerHolder> {
    Fragment fragment;
    private List<Blog> datas;

    public BlogAdapter(Fragment fragment, List<Blog> datas) {
        this.fragment = fragment;
        this.datas = datas;
    }

    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_view,parent,false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, int position) {
        holder.setData(datas.get(position));
    }

    @Override
    public int getItemCount() {
        if(datas != null){
            return datas.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder{
        private ImageView icon;
        private TextView title;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            //找到条目的控件
            icon = (ImageView)itemView.findViewById(R.id.iv_icon);
            title = (TextView)itemView.findViewById(R.id.tv_title);
        }

        public void setData(Blog blogBean){
            title.setText(blogBean.getBlogTitle().toString());
            Glide.with(itemView)
                    .load(blogBean.getBlogPic().toString())
                    .centerCrop().into(icon);
            title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
