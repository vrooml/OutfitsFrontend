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

import com.example.outfits.Adapter.CollectionAdapter;
import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.Collection;
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


public class MyCollectionFragment extends Fragment {

    private View view;
    public RecyclerView recyclerView;
    private List<Collection> collectionList;
    public static CollectionAdapter collectionAdapter;
    public static SwipeRefreshLayout swipeRefreshLayout;

    public MyCollectionFragment() {
        // Required empty public constructor
    }

    public static MyCollectionFragment newInstance(List<Collection> collectionList) {
        MyCollectionFragment fragment = new MyCollectionFragment();
        fragment.collectionList = collectionList;
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
        view = inflater.inflate(R.layout.fragment_my_collection,container,false);
        //获取SwipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.sr_myCollection);
        //获取RecyclerView
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_myCollection);
        //创建adapter
        collectionAdapter = new CollectionAdapter(this, collectionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        //设置adapter
        recyclerView.setAdapter(collectionAdapter);
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetBlogRequest getBlogRequest = new GetBlogRequest(Integer.parseInt(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId")));
                RetrofitUtil.getCollection(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")
                        ,getBlogRequest,collectionList, null);
            }
        });
        return view;
    }
}