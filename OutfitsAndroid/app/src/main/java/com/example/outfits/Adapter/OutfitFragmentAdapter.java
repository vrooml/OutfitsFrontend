package com.example.outfits.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Outfit;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.UI.ClothesFragment;
import com.example.outfits.UI.OutfitFragment;
import com.example.outfits.UI.OutfitTypeFragment;

import java.util.ArrayList;
import java.util.List;

//
public class OutfitFragmentAdapter extends FragmentStateAdapter{
    List<Occasion> occasions;

    public OutfitFragmentAdapter(Fragment fragment,List<Occasion> occasions) {
        super(fragment);
        this.occasions=occasions;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        OutfitTypeFragment outfitTypeFragment=new OutfitTypeFragment();
        outfitTypeFragment.occasion=occasions.get(position);
        return outfitTypeFragment;
    }

    @Override
    public int getItemCount() {
        if(occasions!=null){
            return occasions.size();
        }
        return 0;
    }


}
