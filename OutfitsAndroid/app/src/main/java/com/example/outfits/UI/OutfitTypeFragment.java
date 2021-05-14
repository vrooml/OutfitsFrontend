package com.example.outfits.UI;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.outfits.Adapter.OutfitRecyclerAdapter;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Outfit;
import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.GetClothingRequest;
import com.example.outfits.RetrofitStuff.GetOutfitRequest;
import com.example.outfits.Utils.LoadingDialog;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

public class OutfitTypeFragment extends Fragment{
    RecyclerView recyclerView;
    public OutfitRecyclerAdapter adapter;
    public List<Outfit> outfits;
    public Occasion occasion;

    public OutfitTypeFragment(){
        // Required empty public constructor
    }

    public static OutfitTypeFragment newInstance(Occasion occasion){
        OutfitTypeFragment fragment=new OutfitTypeFragment();
        fragment.occasion=occasion;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_outfit_type,container,false);
        recyclerView=view.findViewById(R.id.outfit_type_recyclerview);
        outfits=new ArrayList<>();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new OutfitRecyclerAdapter(outfits,occasion,this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        GetOutfitRequest getOutfitRequest=new GetOutfitRequest(occasion.getOccasionId());
        RetrofitUtil.postGetOutfit(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")
                ,getOutfitRequest,outfits,this,null);
    }

}