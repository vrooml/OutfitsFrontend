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

import com.example.outfits.Adapter.OutfitFragmentAdapter;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.R;
import com.example.outfits.Utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class MyOutfitFragment extends Fragment{
    VerticalTabLayout tabLayout;
    ViewPager2 viewPager;
    List<Occasion> occasions;

    TabAdapter tabAdapter;
    OutfitFragmentAdapter outfitFragmentAdapter;



    public MyOutfitFragment(){
        // Required empty public constructor
    }

    public static MyOutfitFragment newInstance(){
        return new MyOutfitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_my_outfit,container,false);
        tabLayout=view.findViewById(R.id.outfit_tab_layout);
        viewPager=view.findViewById(R.id.outfit_viewpager);
        occasions=new ArrayList<>();
        RetrofitUtil.getOccasion("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNjIwNTUwNzQ1LCJzdWIiOiIxODk2MDE0NzI3MiIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDgwOTk0NX0.HjCnvUYa6m7MjRUMpMd_hfiTNwE71oMdAaNnzcr_-Wo",occasions,this);
        setupWithViewPager(viewPager,tabLayout);

        tabAdapter=new TabAdapter() {

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
        };

        outfitFragmentAdapter=new OutfitFragmentAdapter(this,occasions);

        return view;
    }

    public void init(){
        tabLayout.setTabAdapter(tabAdapter);
        viewPager.setAdapter(outfitFragmentAdapter);
        outfitFragmentAdapter.notifyDataSetChanged();
        viewPager.setUserInputEnabled(false);
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