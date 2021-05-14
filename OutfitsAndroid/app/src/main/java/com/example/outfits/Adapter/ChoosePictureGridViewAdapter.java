package com.example.outfits.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.outfits.Bean.Clothes;
import com.example.outfits.R;
import com.example.outfits.UI.AddOutfitActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ChoosePictureGridViewAdapter extends BaseAdapter{

    private List<Uri> images;
    private List<Clothes> clothes;
    private List<Integer> gridChosenImageIds;

    private Context context;

    private LayoutInflater inflater;

    private int maxImages=9;

    public ChoosePictureGridViewAdapter(List<Uri> datas,List<Clothes> clothes,Context context) {
        this.images = datas;
        this.clothes=clothes;
        this.context = context;
        inflater = LayoutInflater.from(context);
        gridChosenImageIds=new ArrayList<>();
        for(int i:AddOutfitActivity.chosenImageIds){
            if(images.contains(i)){
                gridChosenImageIds.add(i);
            }
        }
    }


    //得到数量
    @Override
    public int getCount(){
        int count=0;
        if(images!=null){
            count=images.size();
        }
        if (count>maxImages) {
            return images.size();
        } else {
            return count;
        }
    }

    //根据位置得到图片
    @Override
    public Object getItem(int i){
        return null;
    }

    //根据位置得到id
    @Override
    public long getItemId(int i){
        return 0;
    }

    public int getMaxImages(){
        return maxImages;
    }

    public void setMaxImages(int maxImages){
        this.maxImages=maxImages;
    }

    ///得到view中数据
    @Override
    public View getView(final int i,View convertView,ViewGroup viewGroup){
        ViewHolder viewHolder=null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.view_select_picture,viewGroup,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        if(images!=null&&i<images.size()){
            Glide.with(context)
                    .load(images.get(i).toString())
                    .into(viewHolder.ivimage);
            viewHolder.btcheck.setVisibility(View.VISIBLE);
        }
        for(int j=0;j<images.size();j++){
            if(AddOutfitActivity.chosenImageIds.contains(clothes.get(i).getClothingId())){
                viewHolder.btcheck.setBackgroundResource(R.drawable.checked);
            }else{
                viewHolder.btcheck.setBackgroundResource(R.drawable.unchecked);
            }
        }
        viewHolder.btcheck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                choiceState(i);
            }
        });
        return convertView;
    }



    public class ViewHolder{
        public final ImageView ivimage;
        public final ImageView btcheck;
        public boolean isChecked=false;
        public final View root;

        public ViewHolder(View root){
            ivimage=(ImageView)root.findViewById(R.id.iv_image);
            btcheck=(ImageView)root.findViewById(R.id.btn_check);
            this.root=root;
        }
    }


    public void notifyDataSetChanged(List<Uri> images){
        this.images=images;
        this.notifyDataSetChanged();
    }

    public void choiceState(int position) {
        if (gridChosenImageIds.contains(clothes.get(position).getClothingId())){
            gridChosenImageIds.remove((Integer)clothes.get(position).getClothingId());
            AddOutfitActivity.chosenImageIds.remove((Integer)clothes.get(position).getClothingId());
        } else {
            gridChosenImageIds.add(clothes.get(position).getClothingId());
            AddOutfitActivity.chosenImageIds.add(clothes.get(position).getClothingId());
        }
        this.notifyDataSetChanged();
    }
}
