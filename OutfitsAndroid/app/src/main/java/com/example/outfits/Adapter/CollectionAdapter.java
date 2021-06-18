package com.example.outfits.Adapter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.outfits.Bean.Collection;
import com.example.outfits.R;
import com.example.outfits.UI.BlogDetailActivity;

import java.util.List;

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.InnerHolder>{
    Fragment fragment;
    private List<Collection> datas;

    public CollectionAdapter(Fragment fragment, List<Collection> datas) {
        this.fragment = fragment;
        this.datas = datas;
    }

    //创建条目界面
    @NonNull
    @Override
    public CollectionAdapter.InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_view,parent,false);
        return new InnerHolder(view);
    }

    //用于绑定holder，一般用来设置数据
    @Override
    public void onBindViewHolder(@NonNull CollectionAdapter.InnerHolder holder, int position) {
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
        private ConstraintLayout rootLayout;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            //找到条目的控件
            icon = (ImageView)itemView.findViewById(R.id.iv_icon);
            title = (TextView)itemView.findViewById(R.id.tv_title);
            rootLayout=itemView.findViewById(R.id.root_layout);
        }

        public void setData(Collection CollectionBean){
            title.setText(CollectionBean.getBlogTitle().toString());
            Glide.with(itemView).asBitmap()
                    .load(CollectionBean.getBlogPic().toString())
                    .centerCrop().into(icon);
            rootLayout.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent=new Intent(fragment.getActivity(),BlogDetailActivity.class);
                    intent.putExtra("blogId",CollectionBean.getBlogId());
                    fragment.getActivity().startActivity(intent);
                }
            });

        }

    }
}
