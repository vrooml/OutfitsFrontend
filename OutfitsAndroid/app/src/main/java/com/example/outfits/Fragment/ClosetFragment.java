package com.example.outfits.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.outfits.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class ClosetFragment extends Fragment{
    VerticalTabLayout tabLayout;
    ViewPager2 viewPager;

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
            String test[]={
                    "111","222","333"
            };
            @Override
            public int getCount() {
                return test.length;
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
                        .setContent(test[position])
                        .build();
            }

            @Override
            public int getBackground(int position) {
                return 0;
            }
        });

        viewPager.setAdapter(new RecyclerView.Adapter<ViewPagerViewHolder>() {
            String test[]={
                    "111","222","333"
            };

            @NonNull
            @Override
            public ViewPagerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View inflate = LayoutInflater.from(getContext()).inflate(R.layout.closet_viewpager_item, parent,false);
                return new ViewPagerViewHolder(inflate);
            }

            @Override
            public void onBindViewHolder(@NonNull ViewPagerViewHolder holder, int position) {
                holder.textView.setText(test[position]);
            }

            @Override
            public int getItemCount() {
                return test.length;
            }


        });





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

    class ViewPagerViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public ViewPagerViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.textView);
        }
    }
}