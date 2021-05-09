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
        ClothesFragment clothesFragment=new ClothesFragment();
        clothesFragment.type=types.get(position);
        return clothesFragment;
//        return ClothesFragment.newInstance(types.get(position));
    }



    @Override
    public int getItemCount(){
        if(types!=null){
            return types.size();
        }
        return 0;
    }


}
