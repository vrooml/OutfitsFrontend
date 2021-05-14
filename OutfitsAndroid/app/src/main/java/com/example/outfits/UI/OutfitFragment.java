package com.example.outfits.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.outfits.R;

public class OutfitFragment extends Fragment{
    ViewPager2 viewPager2;
    TextView myOutfit;
    TextView recommendOutfit;
    ConstraintLayout rootLayout;

    public OutfitFragment(){
        // Required empty public constructor
    }


    public static OutfitFragment newInstance(){
        OutfitFragment fragment=new OutfitFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_outfit,container,false);
        viewPager2=view.findViewById(R.id.outfit_viewpager);
        myOutfit=view.findViewById(R.id.my_outfit);
        recommendOutfit=view.findViewById(R.id.recommend_outfit);
        rootLayout=view.findViewById(R.id.outfit_toolbar);
        FragmentStateAdapter adapter=new FragmentStateAdapter(this){
            @NonNull
            @Override
            public Fragment createFragment(int position){
                if(position==0){
                    return MyOutfitFragment.newInstance();
                }else{
                    return RecommendOutfitFragment.newInstance();
                }
            }

            @Override
            public int getItemCount(){
                return 2;
            }
        };

        myOutfit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Transition transition = new ChangeBounds();
                transition.setDuration(1000);
                TransitionManager.beginDelayedTransition(rootLayout,transition);
                myOutfit.setTextSize(20);
                recommendOutfit.setTextSize(16);
                viewPager2.setCurrentItem(0,true);
            }
        });

        recommendOutfit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Transition transition = new ChangeBounds();
                transition.setDuration(1000);
                TransitionManager.beginDelayedTransition(rootLayout,transition);
                myOutfit.setTextSize(16);
                recommendOutfit.setTextSize(20);
                viewPager2.setCurrentItem(1,true);
            }
        });
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        Transition transition = new ChangeBounds();
                        transition.setDuration(1000);
                        TransitionManager.beginDelayedTransition(rootLayout,transition);
                        myOutfit.setTextSize(20);
                        recommendOutfit.setTextSize(16);
                        break;
                    case 1:
                        transition = new ChangeBounds();
                        transition.setDuration(1000);
                        TransitionManager.beginDelayedTransition(rootLayout,transition);
                        myOutfit.setTextSize(16);
                        recommendOutfit.setTextSize(20);
                        break;
                }
            }
        });

        return view;
    }
}