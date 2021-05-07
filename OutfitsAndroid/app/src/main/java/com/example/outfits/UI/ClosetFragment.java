package com.example.outfits.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.outfits.Adapter.ClothesFragmentAdapter;
import com.example.outfits.Bean.Type;
import com.example.outfits.R;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class ClosetFragment extends Fragment{
    VerticalTabLayout tabLayout;
    ViewPager2 viewPager;
    List<Type> types;


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
//        RetrofitUtil.getType(types);
        Type.SubType[] subType=new Type.SubType[2];
        subType[0]=new Type.SubType(1,"短袖");
        subType[1]=new Type.SubType(2,"长袖");
        types.add(new Type(1,"上衣",subType));

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

        tabLayout.setTabAdapter(new TabAdapter() {

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
                        .setTextColor(0xff000000,0xFF87a8be)
                        .setTextSize(18)
                        .setContent(types.get(position).getTypeName())
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });

        ClothesFragmentAdapter clothesFragmentAdapter=new ClothesFragmentAdapter(this,types);
        viewPager.setAdapter(clothesFragmentAdapter);


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