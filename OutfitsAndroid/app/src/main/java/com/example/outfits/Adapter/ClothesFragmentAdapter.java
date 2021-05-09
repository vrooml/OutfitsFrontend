package com.example.outfits.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.UI.ClothesFragment;
import com.example.outfits.RetrofitStuff.GetClothingRequest;
import com.example.outfits.UI.OutfitFragment;

import java.util.ArrayList;
import java.util.List;

public class ClothesFragmentAdapter extends FragmentStateAdapter{
    List<Type> types;

    public ClothesFragmentAdapter(Fragment fragment,List<Type> types){
        super(fragment);
        this.types=types;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        List<SubTypeClothingBean> subTypeClothingBeans=new ArrayList<>();
        subTypeClothingBeans.add(new SubTypeClothingBean(1,"短袖",null));
        subTypeClothingBeans.add(new SubTypeClothingBean(2,"长袖",null));
        subTypeClothingBeans.add(new SubTypeClothingBean(2,"长袖",null));
        subTypeClothingBeans.add(new SubTypeClothingBean(2,"长袖",null));
        subTypeClothingBeans.add(new SubTypeClothingBean(2,"长袖",null));
        subTypeClothingBeans.add(new SubTypeClothingBean(2,"长袖",null));
        subTypeClothingBeans.add(new SubTypeClothingBean(2,"长袖",null));
        subTypeClothingBeans.add(new SubTypeClothingBean(2,"长袖",null));
        return ClothesFragment.newInstance(subTypeClothingBeans);
    }

    @Override
    public int getItemCount(){
        if(types!=null){
            return types.size();
        }
        return 0;
    }


}
