package com.example.outfits.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.DeleteClothingRequest;
import com.example.outfits.RetrofitStuff.DeleteOccasionRequest;
import com.example.outfits.UI.ClosetFragment;
import com.example.outfits.UI.ClothesDetailActivity;
import com.example.outfits.UI.MyOutfitFragment;
import com.example.outfits.Utils.LoadingDialog;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;
import com.kongzue.dialog.v2.SelectDialog;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

import static com.example.outfits.Adapter.AddPictureGridViewAdapter.ADD_MODE;

public class ClothesRecyclerAdapter extends RecyclerView.Adapter<ClothesRecyclerAdapter.ViewHolder>{
    List<SubTypeClothingBean> subTypeClothingBeans;
    Fragment fragment;
    Type type;


    public ClothesRecyclerAdapter(List<SubTypeClothingBean> subTypeClothingBeans,Type type,Fragment fragment){
        this.subTypeClothingBeans=subTypeClothingBeans;
        this.type=type;
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
        AddPictureGridViewAdapter addPictureAdapter;
        holder.pictures.clear();
        if(subTypeClothingBeans!=null){
            holder.subTypeName.setText(subTypeClothingBeans.get(position).getSubtypeName());
            Clothes[] clothing=subTypeClothingBeans.get(position).getClothing();
            if(clothing!=null){
                for(int i=0;i<clothing.length;i++){
                    holder.pictures.add(Uri.parse(clothing[i].getClothingPic()));
                }
            }
        }
        //设置图片添加adapter
        addPictureAdapter=new AddPictureGridViewAdapter(holder.pictures,subTypeClothingBeans.get(position),fragment.getContext(),ADD_MODE,(ClosetFragment)fragment.getParentFragment());
        //设置Gridview点击事件
        holder.addPictureGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int position2,long id){
                if(position2!=holder.pictures.size()){
                    Intent intent=new Intent(fragment.getActivity(),ClothesDetailActivity.class);
                    SubTypeClothingBean subTypeClothingBean=subTypeClothingBeans.get(position);
                    intent.putExtra("clothes",subTypeClothingBean.getClothing()[position2]);
                    intent.putExtra("type",type);
                    fragment.startActivity(intent);
                }else{
                    Matisse.from(fragment)
                            .choose(MimeType.of(MimeType.JPEG,MimeType.PNG))
                            .countable(true)//true:选中后显示数字;false:选中后显示对号
                            .maxSelectable(10)//可选的最大数
                            .capture(false)//选择照片时，是否显示拍照
                            .imageEngine(new GlideEngine())//图片加载引擎
                            .forResult(subTypeClothingBeans.get(position).getSubtypeId());
                }
            }
        });
        holder.addPictureGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent,View view,int position2,long id){
                SelectDialog.show(fragment.getContext(), "要删除这件衣物吗？", "", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SubTypeClothingBean subTypeClothingBean=subTypeClothingBeans.get(position);
                        DeleteClothingRequest deleteClothingRequest=new DeleteClothingRequest(subTypeClothingBean.getClothing()[position2].getClothingId());
                        RetrofitUtil.postDeleteClothing(deleteClothingRequest,(ClosetFragment)fragment.getParentFragment());
                        dialog.dismiss();
                    }
                });
                return true;
            }
        });
        holder.addPictureGrid.setAdapter(addPictureAdapter);

    }

    @Override
    public int getItemCount(){
        if(subTypeClothingBeans!=null){
            return subTypeClothingBeans.size();
        }
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView subTypeName;
        GridView addPictureGrid;
        ConstraintLayout rootLayout;
        List<Uri> pictures=new ArrayList<>();//图片路径列表

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            subTypeName=itemView.findViewById(R.id.subtype_name);
            addPictureGrid=itemView.findViewById(R.id.subtype_gridview);
            rootLayout=itemView.findViewById(R.id.root_layout);
        }


    }


}
