package com.example.outfits.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.outfits.Bean.Outfit;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.DeleteClothingRequest;
import com.example.outfits.RetrofitStuff.DeleteOccasionRequest;
import com.example.outfits.UI.ClosetFragment;
import com.example.outfits.UI.MyOutfitFragment;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;
import com.kongzue.dialog.v2.SelectDialog;

import java.io.File;
import java.util.List;

public class AddPictureGridViewAdapter extends BaseAdapter{

    private List<Uri> images;
    private Context context;
    private int mode=2;

    private SubTypeClothingBean subTypeClothingBean;
    private ClosetFragment closetFragment;
    private Outfit outfit;



    private LayoutInflater inflater;
    public static final int ADD_MODE=1;
    public static final int SHOW_MODE=2;

    private int maxImages=30;

    public AddPictureGridViewAdapter(List<Uri> datas,SubTypeClothingBean subTypeClothingBean,Context context,int mode,ClosetFragment closetFragment) {
        this.images = datas;
        this.context = context;
        this.subTypeClothingBean=subTypeClothingBean;
        this.mode=mode;
        this.closetFragment=closetFragment;
        inflater = LayoutInflater.from(context);
    }

    public AddPictureGridViewAdapter(List<Uri> datas,Outfit outfit,Context context,int mode) {
        this.images = datas;
        this.context = context;
        this.outfit=outfit;
        this.mode=mode;
        inflater = LayoutInflater.from(context);
    }


    //得到数量
    @Override
    public int getCount(){
        int count=0;
        if(images!=null){
            count=images.size();

        }
        if(mode==ADD_MODE){
            count+=1;
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
            convertView=inflater.inflate(R.layout.view_show_picture,viewGroup,false);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        //代表+号之前的需要正常显示图片
        if(images!=null&&i<images.size()){
            String url=images.get(i).toString();
            Glide.with(context)
//                    .load(images.get(i).toString())
                    .load(images.get(i).toString())
                    .thumbnail(0.3f)
                    .centerCrop()
                    .into(viewHolder.ivimage);
        }
        if(mode==ADD_MODE&&(images==null||i==images.size())){
            /**代表+号的需要+号图片显示图片**/
            Glide.with(context)
                    .load(R.drawable.add_pic_btn)
                    .into(viewHolder.ivimage);
            viewHolder.ivimage.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

//        if(mode==ADD_MODE&&i!=images.size()){
//            viewHolder.ivimage.setOnLongClickListener(new View.OnLongClickListener(){
//                @Override
//                public boolean onLongClick(View v){
//                    SelectDialog.show(context, "要删除这件衣物吗？", "", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            DeleteClothingRequest deleteClothingRequest=new DeleteClothingRequest(subTypeClothingBean.getClothing()[i].getClothingId());
//                            RetrofitUtil.postDeleteClothing(deleteClothingRequest,closetFragment);
//                            dialog.dismiss();
//                        }
//                    });
//                    return false;
//                }
//            });
//        }

        return convertView;
    }



    public class ViewHolder{
        public final ImageView ivimage;
        public final View root;

        public ViewHolder(View root){
            ivimage=(ImageView)root.findViewById(R.id.iv_image);
            this.root=root;
        }
    }


    public void notifyDataSetChanged(List<Uri> images){
        this.images=images;
        this.notifyDataSetChanged();
    }
}
