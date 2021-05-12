package com.example.outfits.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.OtherUserActivity;
import com.example.outfits.R;

import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.InnerHolder>{
    private Context context;
    private List<UserInfo> datas;
    public UserInfoAdapter(Context context, List<UserInfo> datas){
        this.datas = datas;
        this.context = context;
    }

    //创建条目界面
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //创建条目界面
        //1.得到View
        //2.构造InnerHolder
        View view = View.inflate(parent.getContext(), R.layout.user_list_view, null);
        return new InnerHolder(view);
    }

    //用于绑定holder，一般用来设置数据
    @Override
    public void onBindViewHolder(@NonNull UserInfoAdapter.InnerHolder holder, int position) {
        holder.setData(datas.get(position));
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), OtherUserActivity.class);
                //传递参数
                Bundle bundle = new Bundle();
                bundle.putInt("userId", 2);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
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

        public void setData(UserInfo UserInfoBean){
            title.setText(UserInfoBean.getUserNickname().toString());
            Glide.with(itemView).asBitmap()
                    .load(UserInfoBean.getUserPic().toString())
                    .centerCrop().into(new BitmapImageViewTarget(icon){
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                            .create(itemView.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    icon.setImageDrawable(circularBitmapDrawable);
                }
            });
        }
    }
}


