package com.example.outfits.UI;

import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.outfits.Adapter.ClothesFragmentAdapter;
import com.example.outfits.Adapter.OutfitFragmentAdapter;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Type;
import com.example.outfits.R;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class OutfitFragment extends Fragment{
    VerticalTabLayout tabLayout;
    ViewPager2 viewPager;
    List<Occasion> occasions;
    TextView myOutfit;
    TextView recommendOutfit;
    ConstraintLayout rootLayout;



    public OutfitFragment(){
        // Required empty public constructor
    }

    public static OutfitFragment newInstance(){
        return new OutfitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_outfit,container,false);
        tabLayout=view.findViewById(R.id.outfit_tab_layout);
        viewPager=view.findViewById(R.id.outfit_viewpager);
        myOutfit=view.findViewById(R.id.my_outfit);
        recommendOutfit=view.findViewById(R.id.recommend_outfit);
        rootLayout=view.findViewById(R.id.outfit_toolbar);
        occasions=new ArrayList<>();
//        RetrofitUtil.getType(types);
        occasions.add(new Occasion(1,"上课"));
        occasions.add(new Occasion(1,"出去玩"));
        occasions.add(new Occasion(1,"正式场合"));

        setupWithViewPager(viewPager,tabLayout);

//        tabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabView tab,int position) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabView tab, int position) {
//
//            }
//        });

        myOutfit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Transition transition = new ChangeBounds();
                transition.setDuration(1000);
                TransitionManager.beginDelayedTransition(rootLayout,transition);
                myOutfit.setTextSize(20);
                myOutfit.setTextColor(getActivity().getResources().getColor(R.color.main_color));
                recommendOutfit.setTextSize(16);
                recommendOutfit.setTextColor(getActivity().getResources().getColor(R.color.black));

            }
        });

        recommendOutfit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Transition transition = new ChangeBounds();
                transition.setDuration(1000);
                TransitionManager.beginDelayedTransition(rootLayout,transition);
                myOutfit.setTextSize(16);
                myOutfit.setTextColor(getActivity().getResources().getColor(R.color.black));
                recommendOutfit.setTextSize(20);
                recommendOutfit.setTextColor(getActivity().getResources().getColor(R.color.main_color));
            }
        });

        tabLayout.setTabAdapter(new TabAdapter() {

            @Override
            public int getCount() {
                return occasions.size();
            }

            @Override
            public TabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public TabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public TabView.TabTitle getTitle(int position) {
                return new QTabView.TabTitle.Builder()
                        .setTextColor(0xff9b8f92,0xFF87a8be)
                        .setTextSize(14)
                        .setContent(occasions.get(position).getOccasionName())
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });

        OutfitFragmentAdapter outfitFragmentAdapter=new OutfitFragmentAdapter(this,occasions);
        viewPager.setAdapter(outfitFragmentAdapter);
        viewPager.setUserInputEnabled(false);

        return view;
    }

    public void setupWithViewPager(@Nullable ViewPager2 viewPager,@Nullable VerticalTabLayout tabLayout) {
        tabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                if (viewPager != null && viewPager.getAdapter().getItemCount() >= position) {
                    viewPager.setCurrentItem(position);
                }
            }
            @Override
            public void onTabReselected(TabView tab, int position) {
            }
        });
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.setTabSelected(position,true);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }
}