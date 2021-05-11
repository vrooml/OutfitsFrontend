package com.example.outfits.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.outfits.R;
import com.example.outfits.UI.AddOutfitActivity;

import java.io.File;
import java.util.List;

public class ChoosePictureGridViewAdapter extends BaseAdapter{

    private List<Uri> images;

    private Context context;

    private LayoutInflater inflater;


    private int maxImages=9;

    public ChoosePictureGridViewAdapter(List<Uri> datas,Context context) {
        this.images = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    //得到数量
    @Override
    public int getCount(){
        int count=1;
        if(images!=null){
            count=images.size()+1;
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
            final File file=new File(images.get(i).toString());
            Glide.with(context)
                    .load(images.get(i))
                    .into(viewHolder.ivimage);
            viewHolder.btcheck.setVisibility(View.VISIBLE);
//            设置删除按钮监听
            ViewHolder finalViewHolder=viewHolder;
            viewHolder.btcheck.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(!finalViewHolder.isChecked){
                        AddOutfitActivity.chosenImages.add(images.get(i));
                        finalViewHolder.btcheck.setBackgroundResource(R.drawable.checked);
                    }else{
                        AddOutfitActivity.chosenImages.remove(images.get(i));
                        finalViewHolder.btcheck.setBackgroundResource(R.drawable.unchecked);
                    }
                }
            });
        }
        return convertView;
    }



    public class ViewHolder{
        public final ImageView ivimage;
        public final Button btcheck;
        public final View root;
        public final boolean isChecked=false;

        public ViewHolder(View root){
            ivimage=(ImageView)root.findViewById(R.id.iv_image);
            btcheck=(Button)root.findViewById(R.id.btn_check);
            this.root=root;
        }
    }


    public void notifyDataSetChanged(List<Uri> images){
        this.images=images;
        this.notifyDataSetChanged();
    }
}
