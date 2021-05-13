package com.example.outfits;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.UI.MyBlogFragment;
import com.example.outfits.UI.MyCollectionFragment;
import com.example.outfits.Utils.RetrofitUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;

public class OtherUserActivity extends AppCompatActivity {
    private RecyclerView list;
    private List<Blog> datas;
    private TextView fllowCount;
    private TextView fansCount;
    private Button btn_modify;
    private FloatingActionButton btn_createBlog;
    private TextView blocUser;
    private TextView blocFollow;
    private ViewPager2 viewPager2;
    private List<Fragment> mFragmentArray=new ArrayList<>();
    private TextView name;
    private ImageView icon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_user);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int userId = bundle.getInt("userId");
        fllowCount = findViewById(R.id.followCount);
        fansCount = findViewById(R.id.fansCount);
        btn_modify = findViewById(R.id.btn_modify);
        btn_createBlog = findViewById(R.id.btn_createBlog);
        blocUser = findViewById(R.id.blog_user);
        blocFollow = findViewById(R.id.blog_follow);
        name = findViewById(R.id.user_name);
        icon = findViewById(R.id.user_image);
        mFragmentArray.add(MyBlogFragment.newInstance(null));
        mFragmentArray.add(MyCollectionFragment.newInstance(null));
//        BlogFragmentAdapter blogFragmentAdapter = new BlogFragmentAdapter(this, null);
        viewPager2 = findViewById(R.id.vp_blog);
        //      viewPager2.setAdapter(blogFragmentAdapter);

        PostInterfaces request = RetrofitUtil.retrofit.create(PostInterfaces.class);

        //获取用户头像和昵称
        Call<ResponseModel<UserInfo>> call1 = request.getIntro("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNjIwNzM1NjAyLCJzdWIiOiIxNTI2MDAxMTM4NSIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDk5NDgwMn0.SM7ERdR_qw3gSHjwtoYuM9XO2Zjd7IHymHTAHusRYFw",
                new GetBlogRequest(userId));
        call1.enqueue(new Callback<ResponseModel<UserInfo>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserInfo>> call, Response<ResponseModel<UserInfo>> response) {
                if(response.body() != null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        name.setText(response.body().getData().getUserNickname());
                        Glide.with(MyApplication.getContext()).asBitmap()
                                .load(response.body().getData().getUserPic())
                                .centerCrop().into(new BitmapImageViewTarget(icon){
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                                        .create(MyApplication.getContext().getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                icon.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }else{
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });

        //获取用户粉丝数
        Call<ResponseModel<UserInfo[]>> call2 = request.getSubscriber("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNjIwNzM1NjAyLCJzdWIiOiIxNTI2MDAxMTM4NSIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDk5NDgwMn0.SM7ERdR_qw3gSHjwtoYuM9XO2Zjd7IHymHTAHusRYFw",
                new GetBlogRequest(userId));
        call2.enqueue(new Callback<ResponseModel<UserInfo[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserInfo[]>> call, Response<ResponseModel<UserInfo[]>> response) {
                if(response.body() != null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        fansCount.setText(String.valueOf(response.body().getData().length));
                    }else{
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo[]>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });

        //获取用户关注数
        Call<ResponseModel<UserInfo[]>> call3 = request.getSubscription("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNjIwNzM1NjAyLCJzdWIiOiIxNTI2MDAxMTM4NSIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDk5NDgwMn0.SM7ERdR_qw3gSHjwtoYuM9XO2Zjd7IHymHTAHusRYFw",
                new GetBlogRequest(userId));
        call3.enqueue(new Callback<ResponseModel<UserInfo[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserInfo[]>> call, Response<ResponseModel<UserInfo[]>> response) {
                if(response.body() != null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        fllowCount.setText(String.valueOf(response.body().getData().length));
                    }else{
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo[]>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }
}