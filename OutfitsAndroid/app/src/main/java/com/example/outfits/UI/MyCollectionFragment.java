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
    private List<Collection> collectionList;
    public static CollectionAdapter collectionAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));
        //设置adapter
        recyclerView.setAdapter(collectionAdapter);
        return view;
    }
}