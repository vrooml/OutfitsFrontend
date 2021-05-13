package com.example.outfits.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Type;
import com.example.outfits.UI.ChooseClothesFragment;
import com.example.outfits.UI.ClosetFragment;
import com.example.outfits.UI.ClothesFragment;

import java.util.List;

public class ClothesFragmentAdapter extends FragmentStateAdapter{
    List<Type> types;
    Fragment fragment;
    int mode=1;

    public ClothesFragmentAdapter(Fragment fragment,List<Type> types){
        super(fragment);
        this.types=types;
        this.fragment=fragment;
        this.mode=1;
    }

    public ClothesFragmentAdapter(@NonNull FragmentManager fragmentManager,@NonNull Lifecycle lifecycle,List<Type> types){
        super(fragmentManager,lifecycle);
        this.types=types;
        this.mode=2;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        if(mode==2){
            ChooseClothesFragment chooseClothesFragment=new ChooseClothesFragment();
            chooseClothesFragment.type=types.get(position);
            return chooseClothesFragment;
        }
//        ClothesFragment clothesFragment=new ClothesFragment();
//        clothesFragment.type=types.get(position);
//        return clothesFragment;
        return ClothesFragment.newInstance(types.get(position),(ClosetFragment)fragment);
    }



    @Override
    public int getItemCount(){
        if(types!=null){
            return types.size();
        }
        return 0;
    }

    public void RefreshFragments(List<Type> types){
        this.types=types;
        notifyDataSetChanged();
    }

}
