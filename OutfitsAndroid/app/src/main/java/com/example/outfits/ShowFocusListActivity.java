package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfits.Adapter.UserInfoAdapter;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.UI.UserFragment;
import com.example.outfits.Utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;

public class ShowFocusListActivity extends BaseActivity{
    private static final String TAG = "ShowFocusListActivity";
    private RecyclerView list;
    private List<UserInfo> datas = new ArrayList<>();
    private ImageView goback;
    public static UserInfoAdapter userInfoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_focus);
        list = (RecyclerView) this.findViewById(R.id.focus_recyclerview);
        //设置默认的显示样式
        //RecyclerView需要设置样式，其实就是设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        //创建适配器
        PostInterfaces request = RetrofitUtil.retrofit.create(PostInterfaces.class);
        Call<ResponseModel<UserInfo[]>> call = request.getSubscription("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNjIwNzM1NjAyLCJzdWIiOiIxNTI2MDAxMTM4NSIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDk5NDgwMn0.SM7ERdR_qw3gSHjwtoYuM9XO2Zjd7IHymHTAHusRYFw",
                new GetBlogRequest(2));
        call.enqueue(new Callback<ResponseModel<UserInfo[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserInfo[]>> call, Response<ResponseModel<UserInfo[]>> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == SUCCESS_CODE) {
                        for (UserInfo i : response.body().getData()) {
                            datas.add(i);
                        }
                        userInfoAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo[]>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(), FAILED, Toast.LENGTH_SHORT).show();
            }
        });
        //   Toast.makeText(this, "粉丝的数量为" + datas.size(), Toast.LENGTH_SHORT).show();
        //设置到RecyclerView中
        userInfoAdapter = new UserInfoAdapter(this, datas);
        list.setAdapter(userInfoAdapter);
        goback = (ImageView) findViewById(R.id.goback);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
