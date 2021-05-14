package com.example.outfits.UI;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.outfits.Adapter.ClothesFragmentAdapter;
import com.example.outfits.Bean.Type;
import com.example.outfits.R;
import com.example.outfits.Utils.RetrofitUtil;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class ClosetFragment extends Fragment{
    public  VerticalTabLayout tabLayout;
    ViewPager2 viewPager;
    List<Type> types;
    public TabAdapter tabAdapter;
    ClothesFragmentAdapter clothesFragmentAdapter;

    public ClosetFragment(){
        // Required empty public constructor
    }

    public static ClosetFragment newInstance(){
        return new ClosetFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_closet,container,false);
        tabLayout=view.findViewById(R.id.closet_tab_layout);
        viewPager=view.findViewById(R.id.closet_viewpager);
        types=new ArrayList<>();
        setupWithViewPager(viewPager,tabLayout);
        tabAdapter=new TabAdapter() {

            @Override
            public int getCount() {
                return types.size();
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
                        .setContent(types.get(position).getTypeName())
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        };
        RetrofitUtil.getType(types,this);
        clothesFragmentAdapter=new ClothesFragmentAdapter(this,types);

        return view;
    }

    public void init(){
        tabLayout.setTabAdapter(tabAdapter);
        viewPager.setAdapter(clothesFragmentAdapter);
        clothesFragmentAdapter.notifyDataSetChanged();
        viewPager.setUserInputEnabled(false);
        clothesFragmentAdapter.RefreshFragments(types);
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