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
import com.example.outfits.R;
import com.example.outfits.Adapter.BlogAdapter;
import com.example.outfits.Utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyBlogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyBlogFragment extends Fragment {

    private View view;
    public RecyclerView recyclerView;
    private List<Blog> blogLists;
    public static BlogAdapter blogAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;


    public MyBlogFragment() {
        // Required empty public constructor
    }
    public static MyBlogFragment newInstance(List<Blog> blogList) {
        MyBlogFragment fragment = new MyBlogFragment();
        fragment.blogLists = blogList;
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
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //设置adapter
        recyclerView.setAdapter(blogAdapter);
        return view;
    }

    private void handleDownPullUpdate() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }
}