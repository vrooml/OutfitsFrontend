package com.example.outfits.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outfits.Adapter.ClothesFragmentAdapter;
import com.example.outfits.Bean.Type;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.AddOutfitRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.Utils.LoadingDialog;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.QTabView;
import q.rorbin.verticaltablayout.widget.TabView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.outfits.MyApplication.getContext;
import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;
import static com.example.outfits.Utils.RetrofitUtil.retrofit;

public class AddOutfitActivity extends AppCompatActivity{
    public static List<Integer> chosenImageIds=new ArrayList<>();
    public VerticalTabLayout tabLayout;
    ViewPager2 viewPager;
    EditText editIntroduceText;
    List<Type> types;
    int occasionId;
    public TabAdapter tabAdapter;
    ClothesFragmentAdapter clothesFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_outfit);
        occasionId=getIntent().getIntExtra("occasionId",0);
        tabLayout=findViewById(R.id.closet_tab_layout);
        viewPager=findViewById(R.id.closet_viewpager);
        editIntroduceText=findViewById(R.id.edit_introduce);
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

        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<Type[]>> call=request.getType();
        call.enqueue(new Callback<ResponseModel<Type[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<Type[]>> call,Response<ResponseModel<Type[]>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        types.clear();
                        for(Type i:response.body().getData()){
                            types.add(i);
                        }
                        Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        init();

                    }else{
                        Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Type[]>> call,Throwable t){
                Toast.makeText(getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
        clothesFragmentAdapter=new ClothesFragmentAdapter(getSupportFragmentManager(),getLifecycle(),types);
        TextView commitAdd=findViewById(R.id.add_button);
        commitAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(editIntroduceText.getText().toString().equals("")){
                    Toast.makeText(MyApplication.getContext(),"请输入介绍",Toast.LENGTH_SHORT).show();
                }else if(chosenImageIds.size()==0){
                    Toast.makeText(MyApplication.getContext(),"请先选择衣物",Toast.LENGTH_SHORT).show();
                }else{
                    AddOutfitRequest addOutfitRequest=new AddOutfitRequest(occasionId,editIntroduceText.getText().toString(),chosenImageIds);
                    RetrofitUtil.postAddOutfit(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),addOutfitRequest);
                    finish();
                }
            }
        });

    }

    public void init(){
        tabLayout.setTabAdapter(tabAdapter);
        viewPager.setAdapter(clothesFragmentAdapter);
        clothesFragmentAdapter.notifyDataSetChanged();
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        chosenImageIds.clear();
    }
}