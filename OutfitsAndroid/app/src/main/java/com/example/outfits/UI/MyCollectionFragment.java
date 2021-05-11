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
 * Use the {@link MyCollectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyCollectionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private View view;
    public RecyclerView recyclerView;
    private List<Blog> blogLists;
    private BlogAdapter blogAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyCollectionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyCollectionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyCollectionFragment newInstance(String param1, String param2) {
        MyCollectionFragment fragment = new MyCollectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_my_collection, container, false);
        }
        initData();
        initView();
        return view;
    }

    private void initView() {
        //获取SwipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.sr_myCollection);
        //获取RecyclerView
        recyclerView = (RecyclerView)view.findViewById(R.id.rv_myCollection);
        //创建adapter
        blogAdapter = new BlogAdapter(this, blogLists);
        //设置adapter
        recyclerView.setAdapter(blogAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        //设置下拉刷新
        handleDownPullUpdate();
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

    private void initData() {
        blogLists = new ArrayList<>();
        Request();
    }
    private void Request(){
        //blogLists = RetrofitUtil.getCollection("token", 3);
    }
}