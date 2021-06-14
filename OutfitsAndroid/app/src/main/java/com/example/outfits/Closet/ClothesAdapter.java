package com.example.outfits.Closet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;


public class ClothesAdapter extends FragmentStateAdapter{

    public ClothesAdapter(Fragment fragment,List<String> subTypes,List<Clothes>[] clothes) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Return a NEW fragment instance in createFragment(int)
        Fragment fragment = new ClothesFragment();
        Bundle args = new Bundle();
        // Our object is just an integer :-P
        args.putInt("position", position + 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
