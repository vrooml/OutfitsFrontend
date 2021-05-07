package com.example.outfits.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.R;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

public class ClothesRecyclerAdapter extends RecyclerView.Adapter<ClothesRecyclerAdapter.ViewHolder>{
    List<SubTypeClothingBean> subTypeClothingBeans;
    Fragment fragment;


    public ClothesRecyclerAdapter(List<SubTypeClothingBean> subTypeClothingBeans,Fragment fragment){
        this.subTypeClothingBeans=subTypeClothingBeans;
        this.fragment=fragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.view_subtype_clothes,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position){
        holder.subTypeName.setText(subTypeClothingBeans.get(position).getSubtypeName());
        Clothes[] clothing=subTypeClothingBeans.get(position).getClothing();
        if(clothing!=null){
            for(int i=0;i<clothing.length;i++){
                holder.pictures.add(Uri.parse(clothing[i].getClothingPic()));
            }
        }
        //设置图片添加adapter
        holder.addPictureAdapter=new AddPictureGridViewAdapter(holder.pictures,fragment.getContext());
        holder.addPictureGrid.setAdapter(holder.addPictureAdapter);


        //设置Gridview点击事件
        holder.addPictureGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long id){
                Matisse.from(fragment)
                        .choose(MimeType.of(MimeType.JPEG,MimeType.PNG))
                        .countable(true)//true:选中后显示数字;false:选中后显示对号
                        .maxSelectable(10)//可选的最大数
                        .capture(false)//选择照片时，是否显示拍照
                        .imageEngine(new GlideEngine())//图片加载引擎
                        .forResult(subTypeClothingBeans.get(position).getSubtypeId());
            }
        });
    }

    @Override
    public int getItemCount(){
        if(subTypeClothingBeans!=null){
            return subTypeClothingBeans.size();
        }else{
            return 0;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subTypeName;
        GridView addPictureGrid;
        AddPictureGridViewAdapter addPictureAdapter;
        List<Uri> pictures=new ArrayList<>();//图片路径列表

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            subTypeName=itemView.findViewById(R.id.subtype_name);
            addPictureGrid=itemView.findViewById(R.id.subtype_gridview);
        }


    }




}
