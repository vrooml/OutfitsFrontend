package com.example.outfits.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.outfits.Adapter.RecommendOutfitAdapter;
import com.example.outfits.Bean.RecommendClothes;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.AddOutfitRequest;
import com.example.outfits.RetrofitStuff.GetRecommendOutfitRequest;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class RecommendOutfitFragment extends Fragment{
    RecyclerView recyclerView;
    Button tooHot;
    Button tooCold;
    int offset=0;
    RecommendOutfitAdapter recommendOutfitAdapter;
    List<RecommendClothes> recommendClothesList;


    public RecommendOutfitFragment(){
        // Required empty public constructor
    }

    public static RecommendOutfitFragment newInstance(){
        RecommendOutfitFragment fragment=new RecommendOutfitFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_recommend_outfit,container,false);
        recyclerView=view.findViewById(R.id.recommend_outfit_recyclerView);
        tooCold=view.findViewById(R.id.too_cold);
        tooHot=view.findViewById(R.id.too_hot);
        recommendClothesList=new ArrayList<>();
        GetRecommendOutfitRequest getRecommendOutfitRequest=new GetRecommendOutfitRequest(offset,OutfitFragment.getCityName());
        RetrofitUtil.postGetRecommendOutfit(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),getRecommendOutfitRequest,recommendClothesList,this);
        recommendOutfitAdapter=new RecommendOutfitAdapter(this,recommendClothesList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recommendOutfitAdapter);

        tooHot.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                offset-=1;
                GetRecommendOutfitRequest getRecommendOutfitRequest=new GetRecommendOutfitRequest(offset,OutfitFragment.getCityName());
                RetrofitUtil.postGetRecommendOutfit(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),getRecommendOutfitRequest,recommendClothesList,RecommendOutfitFragment.this);
            }
        });

        tooCold.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                offset+=1;
                GetRecommendOutfitRequest getRecommendOutfitRequest=new GetRecommendOutfitRequest(offset,OutfitFragment.getCityName());
                RetrofitUtil.postGetRecommendOutfit(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),getRecommendOutfitRequest,recommendClothesList,RecommendOutfitFragment.this);
            }
        });

        return view;
    }

    public void notifyDataSetChanged(){
        recommendOutfitAdapter.notifyDataSetChanged();
        recommendOutfitAdapter=new RecommendOutfitAdapter(this,recommendClothesList);
        recyclerView.setAdapter(recommendOutfitAdapter);
    }
}