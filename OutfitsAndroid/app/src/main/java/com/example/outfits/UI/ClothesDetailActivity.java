package com.example.outfits.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.outfits.BaseActivity;
import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.Type;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.ModifyClothesRequest;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class ClothesDetailActivity extends BaseActivity{
    ImageView clothesImage;
    List<Type> types;
    Type currentType;
    ImageView editClothesButton;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_detail);
        Clothes clothes=(Clothes)getIntent().getSerializableExtra("clothes");
        currentType=(Type)getIntent().getSerializableExtra("type");
        clothesImage=findViewById(R.id.clothes_image);
        editClothesButton=findViewById(R.id.edit_clothes_button);
        spinner=findViewById(R.id.spinner);

        types=new ArrayList<>();
        initSpinner();
//        types.add(new Type(1,"暂无法获取",null));
        spinner.setEnabled(false);
        if(currentType!=null){
            RetrofitUtil.getType(types,this);
        }
        Glide.with(getApplicationContext())
//                    .load(images.get(i).toString())
                .load(clothes.getClothingPic())
                .centerCrop()
                .into(clothesImage);

        editClothesButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(!spinner.isEnabled()){
                    spinner.setEnabled(true);
                }else{
                    spinner.setEnabled(false);
                    for(int i=0;i<types.size();i++){
                        if(types.get(i).getTypeName().equals(spinner.getSelectedItem().toString())){
                            currentType.getTypeId();
                        }
                    }
                    ModifyClothesRequest modifyClothesRequest=new ModifyClothesRequest(clothes.getClothingId(),currentType.getTypeId());
                    RetrofitUtil.postModifyClothes(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),modifyClothesRequest,ClothesDetailActivity.this);
                }
            }
        });

    }

    public void initSpinner(){
        // 建立数据源
        int currentItem=0;
        List<String> spinnerItems=new ArrayList<>();
        for(int i=0;i<types.size();i++){
            spinnerItems.add(types.get(i).getTypeName());
            if(types.get(i).getTypeName().equals(currentType.getTypeName())){
                currentItem=i;
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(currentItem,true);
    }
}