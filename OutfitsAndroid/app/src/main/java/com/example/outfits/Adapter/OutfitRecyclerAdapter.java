package com.example.outfits.Adapter;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Outfit;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.DeleteOutfitRequest;
import com.example.outfits.UI.AddOutfitActivity;
import com.example.outfits.UI.ClothesDetailActivity;
import com.example.outfits.UI.MyOutfitFragment;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;
import com.kongzue.dialog.v2.SelectDialog;

import java.util.ArrayList;
import java.util.List;

public class OutfitRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final int VIEW_TYPE_FOOTER=1;
    private static final int VIEW_TYPE_CELL=2;
    List<Outfit> outfits;
    Occasion occasion;
    Fragment fragment;


    public OutfitRecyclerAdapter(List<Outfit> outfits,Occasion occasion,Fragment fragment){
        this.outfits=outfits;
        this.occasion=occasion;
        this.fragment=fragment;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType){
        View view;
        if(viewType==VIEW_TYPE_CELL){
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.view_subtype_clothes,parent,false);
            return new OutfitViewHolder(view);
        }else{
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.view_outfit_footer,parent,false);
            return new OutfitFooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,int position){
        if(holder instanceof OutfitViewHolder){

            if(outfits!=null){
                ((OutfitViewHolder)holder).subTypeName.setText(outfits.get(position).getIntroduce());
                List<Clothes> clothing=outfits.get(position).getClothing();
                if(clothing!=null){
                    ((OutfitViewHolder)holder).pictures=new ArrayList<>();
                    for(int i=0;i<clothing.size();i++){
                        ((OutfitViewHolder)holder).pictures.add(Uri.parse(clothing.get(i).getClothingPic()));
                    }
                }
            }
            //设置图片添加adapter
            ((OutfitViewHolder)holder).addPictureAdapter=new AddPictureGridViewAdapter(((OutfitViewHolder)holder).pictures,outfits.get(position),fragment.getContext(),AddPictureGridViewAdapter.SHOW_MODE);
            ((OutfitViewHolder)holder).addPictureGrid.setAdapter(((OutfitViewHolder)holder).addPictureAdapter);


            //设置Gridview点击事件
            ((OutfitViewHolder)holder).addPictureGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView,View view,int position,long id){
                    Intent intent=new Intent(fragment.getActivity(),ClothesDetailActivity.class);
                    Outfit outfit=outfits.get(position);
                    intent.putExtra("clothes",outfit.getClothing().get(position));
                    fragment.startActivity(intent);
                }
            });
            ((OutfitViewHolder)holder).rootLayout.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v){
                    SelectDialog.show(fragment.getContext(), "要删除该搭配吗？", "", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DeleteOutfitRequest deleteOutfitRequest=new DeleteOutfitRequest(outfits.get(position).getMatchId());
                            RetrofitUtil.postDeleteOutfit(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                                    deleteOutfitRequest,(MyOutfitFragment)fragment.getParentFragment());
                            dialog.dismiss();
                        }
                    });
                    return false;
                }
            });
        }else if(holder instanceof OutfitFooterViewHolder){
            ((OutfitFooterViewHolder)holder).addOutfitButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    Intent intent=new Intent(fragment.getActivity(),AddOutfitActivity.class);
                    intent.putExtra("occasionId",occasion.getOccasionId());
                    fragment.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount(){
        if(outfits!=null){
            return outfits.size()+1;
        }
        return 1;
    }

    static class OutfitViewHolder extends RecyclerView.ViewHolder{
        TextView subTypeName;
        GridView addPictureGrid;
        ConstraintLayout rootLayout;
        AddPictureGridViewAdapter addPictureAdapter;
        List<Uri> pictures;//图片路径列表

        public OutfitViewHolder(@NonNull View itemView){
            super(itemView);
            subTypeName=itemView.findViewById(R.id.subtype_name);
            addPictureGrid=itemView.findViewById(R.id.subtype_gridview);
            rootLayout=itemView.findViewById(R.id.root_layout);
        }


    }

    static class OutfitFooterViewHolder extends RecyclerView.ViewHolder{
        ImageView addOutfitButton;

        public OutfitFooterViewHolder(@NonNull View itemView){
            super(itemView);
            addOutfitButton=itemView.findViewById(R.id.add_outfit);
        }


    }

    @Override
    public int getItemViewType(int position){
        if(position==outfits.size()){
            return VIEW_TYPE_FOOTER;
        }else{
            return VIEW_TYPE_CELL;
        }
    }
}
