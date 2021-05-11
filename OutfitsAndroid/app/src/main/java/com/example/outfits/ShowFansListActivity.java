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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_fans);
        list = (RecyclerView)this.findViewById(R.id.fans_recyclerview);
        initData();
        //设置默认的显示样式
        showList(true);
        goback = (ImageView) findViewById(R.id.goback1);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ShowFansListActivity.this, UserFragment.class);
                startActivityForResult(intent1,1);
            }
        });
    }

    private void initData() {
        //List<DataBean>------>Adapter------>setAdapter---->显示数据
        //创建数据集合
        datas = new ArrayList<>();
        GetBlogRequest getBlogRequest = new GetBlogRequest(2);
        datas = RetrofitUtil.getSubscriber("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNjIwNTUwNzQ1LCJzdWIiOiIxODk2MDE0NzI3MiIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDgwOTk0NX0.HjCnvUYa6m7MjRUMpMd_hfiTNwE71oMdAaNnzcr_-Wo",
                getBlogRequest, datas);
        Toast.makeText(MyApplication.getContext(), "粉丝列表的长度为" + datas.size(), Toast.LENGTH_SHORT).show();
        Log.e("ShowFansListActivity", "粉丝列表的长度为" + datas.size());
    }

    private void showList(boolean isVertical) {
        //RecyclerView需要设置样式，其实就是设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置水平还是垂直
        layoutManager.setOrientation(isVertical? LinearLayoutManager.VERTICAL: LinearLayoutManager.HORIZONTAL);
        list.setLayoutManager(layoutManager);
        //创建适配器
        UserInfoAdapter adapter = new UserInfoAdapter(this,datas);
        //设置到RecyclerView中
        list.setAdapter(adapter);
    }

}
