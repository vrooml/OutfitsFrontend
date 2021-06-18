package com.example.outfits.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.outfits.Bean.Blog;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.Adapter.BlogAdapter;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyBlogFragment extends Fragment {

    private View view;
    public RecyclerView recyclerView;
    private List<Blog> blogLists;
    public static BlogAdapter blogAdapter;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private int userId=-1;


    public MyBlogFragment() {
        // Required empty public constructor
    }

    public static MyBlogFragment newInstance(List<Blog> blogList) {
        MyBlogFragment fragment = new MyBlogFragment();
        fragment.blogLists = blogList;
        return fragment;
    }

    public static MyBlogFragment newInstance(List<Blog> blogList,int userId) {
        MyBlogFragment fragment = new MyBlogFragment();
        fragment.blogLists = blogList;
        fragment.userId=userId;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_my_blog,container,false);
        //获取SwipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.sr_myBlog);
        //获取RecyclerView
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_myBlog);
        blogAdapter = new BlogAdapter(this, blogLists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置adapter
        recyclerView.setAdapter(blogAdapter);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetBlogRequest getBlogRequest;
                if(userId==-1){
                    getBlogRequest = new GetBlogRequest(Integer.parseInt(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId")));
                }else{
                    getBlogRequest = new GetBlogRequest(userId);
                }
                RetrofitUtil.getBlog(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")
                        ,getBlogRequest, blogLists, null);
            }
        });
        return view;
    }
}