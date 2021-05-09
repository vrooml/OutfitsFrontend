package com.example.outfits.Adapter;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.MyApplication;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.UI.ClothesFragment;
import com.example.outfits.RetrofitStuff.GetClothingRequest;
import com.example.outfits.UI.OutfitFragment;
import com.example.outfits.Utils.LoadingDialog;
import com.example.outfits.Utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;

public class ClothesFragmentAdapter extends FragmentStateAdapter{
    List<Type> types;
    Fragment fragment;

    public ClothesFragmentAdapter(Fragment fragment,List<Type> types){
        super(fragment);
        this.types=types;
        this.fragment=fragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        List<SubTypeClothingBean> subTypeClothingBeans=new ArrayList<>();
        LoadingDialog dialog=new LoadingDialog.Builder(fragment.getContext())
                .setMessage("加载中...")
                .setCancelable(false)
                .create();
        dialog.show();
        GetClothingRequest getClothingRequest=new GetClothingRequest(types.get(position).getTypeId());
        RetrofitUtil.postGetClothing("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNjIwNTUwNzQ1LCJzdWIiOiIxODk2MDE0NzI3MiIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDgwOTk0NX0.HjCnvUYa6m7MjRUMpMd_hfiTNwE71oMdAaNnzcr_-Wo"
        ,getClothingRequest,subTypeClothingBeans,dialog);
        return ClothesFragment.newInstance(subTypeClothingBeans);
    }

    @Override
    public int getItemCount(){
        if(types!=null){
            return types.size();
        }
        return 0;
    }

    public static void postGetClothing(String token,GetClothingRequest getClothingRequest,List<SubTypeClothingBean> subTypeClothingBeans,LoadingDialog dialog){
        final PostInterfaces request=RetrofitUtil.retrofit.create(PostInterfaces.class);
        Call<ResponseModel<SubTypeClothingBean[]>> call=request.postGetClothing(token,getClothingRequest);
        call.enqueue(new Callback<ResponseModel<SubTypeClothingBean[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<SubTypeClothingBean[]>> call,Response<ResponseModel<SubTypeClothingBean[]>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        for(SubTypeClothingBean i:response.body().getData()){
                            subTypeClothingBeans.add(i);
                        }
                        dialog.dismiss();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<SubTypeClothingBean[]>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }


}
