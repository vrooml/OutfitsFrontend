package com.example.outfits;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.outfits.Adapter.BlogAdapter;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class ShowFansListActivity extends BaseActivity{

    private static final String TAG = "ShowFansListActivity";
    private RecyclerView list;
    private List<UserInfo> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_fans);
        list = (RecyclerView)this.findViewById(R.id.fans_recyclerview);
        initData();
        //设置默认的显示样式
        showList(true);

    }

    private void initData() {
        //List<DataBean>------>Adapter------>setAdapter---->显示数据
        //创建数据集合
        datas = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            UserInfo data = new UserInfo();
            data.setUserNickname("这是第" + i + "张图片");
            datas.add(data);
        }
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
