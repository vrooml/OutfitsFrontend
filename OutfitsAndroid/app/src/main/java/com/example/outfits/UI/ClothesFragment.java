package com.example.outfits.UI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfits.Adapter.ClothesRecyclerAdapter;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.GetClothingRequest;
import com.example.outfits.Utils.LoadingDialog;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;
import com.zhihu.matisse.Matisse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

public class ClothesFragment extends Fragment{
    RecyclerView recyclerView;
    public ClothesRecyclerAdapter adapter;
    public List<SubTypeClothingBean> subTypeClothingBeans;
    public LoadingDialog dialog;
    public Type type;
    public ClosetFragment closetFragment;


    public ClothesFragment(){
        // Required empty public constructor
    }

    public static ClothesFragment newInstance(Type type,ClosetFragment closetFragment){
        ClothesFragment fragment=new ClothesFragment();
        fragment.type=type;
        fragment.closetFragment=closetFragment;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_clothes,container,false);
        subTypeClothingBeans=new ArrayList<>();
        recyclerView=view.findViewById(R.id.clothes_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new ClothesRecyclerAdapter(subTypeClothingBeans,type,this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        GetClothingRequest getClothingRequest=new GetClothingRequest(type.getTypeId());
        RetrofitUtil.postGetClothing(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")
                ,getClothingRequest,subTypeClothingBeans,this,null);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode==RESULT_OK){
            int subtypeId=requestCode;
            List<Uri> pictures=Matisse.obtainResult(data);
            List<Integer> subtypeIds=new ArrayList<>();
            for(int i=0;i<pictures.size();i++){
                subtypeIds.add(subtypeId);
            }
//            String s=String.valueOf(subtypeIds);
//            RequestBody subtypeIdRequest=RequestBody.create(MediaType.parse("multipart/form-data"),String.valueOf(subtypeIds));
            RetrofitUtil.postUploadClothing(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")
                    ,subtypeIds
                    ,getImgList(pictures),closetFragment);
        }
    }

    private List<MultipartBody.Part> getImgList(List<Uri> origin){
        List<MultipartBody.Part> result=new ArrayList<>();
        for(Uri i: origin){
            //将contentUri转化为真实路径Uri
            String res=null;
            String[] proj={MediaStore.Images.Media.DATA};
            Cursor cursor=getActivity().getContentResolver().query(i,proj,null,null,null);
            if(cursor.moveToFirst()){
                int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                res=cursor.getString(column_index);
            }
            cursor.close();
            //将路径uri转化为file
            File file=new File(res);
            //将路径file转化为RequestBody
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
            //将RequestBody转化为MultipartBody.Part
            MultipartBody.Part finalRequest=MultipartBody.Part.createFormData("clothingPic",file.getName(),requestBody);
            result.add(finalRequest);
        }
        return result;
    }
}