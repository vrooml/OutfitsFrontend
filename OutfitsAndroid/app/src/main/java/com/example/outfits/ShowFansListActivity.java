package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfits.Adapter.BlogAdapter;
import com.example.outfits.Adapter.UserInfoAdapter;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.UI.UserFragment;
import com.example.outfits.Utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

public class ShowFansListActivity extends BaseActivity{

    private static final String TAG = "ShowFansListActivity";
    private RecyclerView list;
    private List<UserInfo> datas;
    private ImageView goback;
    public static UserInfoAdapter userInfoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_fans);
        list = (RecyclerView)this.findViewById(R.id.fans_recyclerview);
        //设置默认的显示样式
        //RecyclerView需要设置样式，其实就是设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        //创建适配器
        userInfoAdapter = new UserInfoAdapter(this,datas);
        RetrofitUtil.getSubscriber("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNjIwNTUwNzQ1LCJzdWIiOiIxODk2MDE0NzI3MiIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDgwOTk0NX0.HjCnvUYa6m7MjRUMpMd_hfiTNwE71oMdAaNnzcr_-Wo",
                new GetBlogRequest(2), null);
        //设置到RecyclerView中
        list.setAdapter(userInfoAdapter);
        goback = (ImageView) findViewById(R.id.goback1);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ShowFansListActivity.this, UserFragment.class);
                startActivityForResult(intent1,1);
            }
        });
    }

}
