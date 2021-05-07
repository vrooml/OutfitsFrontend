package com.example.outfits;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.outfits.Adapter.MainActivityPagerAdapter;
import com.example.outfits.CustomView.ScrollableViewPager;
import com.example.outfits.Chat.ChatFragment;
import com.example.outfits.UI.ClosetFragment;
import com.example.outfits.UI.OutfitFragment;
import com.example.outfits.UI.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity{
    private BottomNavigationView bottomNavigationView;
    private long firstClickTime;//记录退出点击时间
    private ScrollableViewPager scrollableViewPager;


    //4个切换的页面的fragment.
    private List<Fragment> mFragmentArray=new ArrayList<>();

    //tab栏的字
    private String mTextArray[]={"衣柜","搭配","社区","我的"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initBottomNavigationView();
    }

    //初始化底部栏
    private void initBottomNavigationView(){
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem){
//                resetToDefaultIcon();//重置到默认不选中图片
                switch(menuItem.getItemId()){
                    case R.id.navigation_closet:
                        scrollableViewPager.setCurrentItem(0);
//                        menuItem.setIcon(R.drawable.closet_fill);
                        break;
                    case R.id.navigation_outfit:
                        scrollableViewPager.setCurrentItem(1);
//                        menuItem.setIcon(R.drawable.outfit_fill);
                        break;
                    case R.id.navigation_chat:
                        scrollableViewPager.setCurrentItem(2);
//                        menuItem.setIcon(R.drawable.chat_fill);
                        break;
                    case R.id.navigation_user:
                        scrollableViewPager.setCurrentItem(3);
//                        menuItem.setIcon(R.drawable.user_fill);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        MainActivityPagerAdapter adapter=new MainActivityPagerAdapter(getSupportFragmentManager());
        mFragmentArray.add(ClosetFragment.newInstance());
        mFragmentArray.add(OutfitFragment.newInstance("",""));
        mFragmentArray.add(ChatFragment.newInstance("",""));
        mFragmentArray.add(UserFragment.newInstance("",""));
        adapter.setList(mFragmentArray);

        scrollableViewPager=findViewById(R.id.scrollable_viewpager);
        scrollableViewPager.setOffscreenPageLimit(4);
        scrollableViewPager.setAdapter(adapter);
        scrollableViewPager.setScroll(true);
        scrollableViewPager.setAnimate(true);

        scrollableViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int i,float v,int i1){

            }

            @Override
            public void onPageSelected(int i){
                bottomNavigationView.getMenu().getItem(i).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i){

            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture){

    }

    //得到当前显示的fragment
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager=MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments=fragmentManager.getFragments();
        for(Fragment fragment: fragments){
            if(fragment!=null&&fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode,KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if(event.getAction()==KeyEvent.ACTION_DOWN){
                long secondTime=System.currentTimeMillis();
                if(secondTime-firstClickTime>2000){
                    Toast.makeText(MainActivity.this,"再次点击退出Outfits",Toast.LENGTH_SHORT).show();
                    firstClickTime=secondTime;
                }else{
                    System.exit(0);
                }
            }
            return true;
        }
        return false;
    }


}