package com.example.outfits.UI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.outfits.Adapter.OutfitFragmentAdapter;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.AddOccasionRequest;
import com.example.outfits.RetrofitStuff.DeleteOccasionRequest;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;
import com.kongzue.dialog.listener.InputDialogOkButtonClickListener;
import com.kongzue.dialog.v2.InputDialog;
import com.kongzue.dialog.v2.SelectDialog;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class MyOutfitFragment extends Fragment{
    VerticalTabLayout tabLayout;
    ViewPager2 viewPager;
    public List<Occasion> occasions;
    ImageView addOccasion;

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
        addOccasion=view.findViewById(R.id.add_occasion);
        tabLayout=view.findViewById(R.id.outfit_tab_layout);
        viewPager=view.findViewById(R.id.outfit_viewpager);
        occasions=new ArrayList<>();
        RetrofitUtil.getOccasion(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),occasions,this);
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

        addOccasion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InputDialog.show(getContext(), "新建场合", "请输入场合的名称", new InputDialogOkButtonClickListener() {
                    @Override
                    public void onClick(Dialog dialog,String inputText) {
                        if(inputText.isEmpty()){
                            Toast.makeText(getContext(), "还没有输入场合名称哦", Toast.LENGTH_SHORT).show();
                        }else{
                            AddOccasionRequest addOccasionRequest=new AddOccasionRequest(inputText);
                            RetrofitUtil.postAddOccasion(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                                    addOccasionRequest,
                                    MyOutfitFragment.this);
                            dialog.dismiss();
                        }
                    }
                });

            }
        });

        outfitFragmentAdapter=new OutfitFragmentAdapter(this,occasions);

        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        RetrofitUtil.getOccasion(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),occasions,this);
    }

    public void init(){
        tabLayout.setTabAdapter(tabAdapter);
        viewPager.setAdapter(outfitFragmentAdapter);
        outfitFragmentAdapter.notifyDataSetChanged();
        viewPager.setUserInputEnabled(false);
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabView tab = tabLayout.getTabAt(i);
            if (tab != null) {
                tab.setOnLongClickListener(new View.OnLongClickListener(){
                    @Override
                    public boolean onLongClick(View v){
                        SelectDialog.show(getContext(), "删除场合", "要删除"+tab.getTitleView().getText().toString()+"场合吗？", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                DeleteOccasionRequest deleteOccasionRequest=new DeleteOccasionRequest(fromNameToOccasion(tab.getTitleView().getText().toString()).getOccasionId());
                                RetrofitUtil.postDeleteOccasion(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                                        deleteOccasionRequest,
                                        MyOutfitFragment.this);
                                RetrofitUtil.getOccasion(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),occasions,MyOutfitFragment.this);
                            }
                        });
                        return false;
                    }
                });
            }
        }
        outfitFragmentAdapter.RefreshFragments(occasions);
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

    public Occasion fromNameToOccasion(String name){
        for(Occasion i:occasions){
            if(i.getOccasionName()==name){
                return i;
            }
        }
        return null;
    }
}