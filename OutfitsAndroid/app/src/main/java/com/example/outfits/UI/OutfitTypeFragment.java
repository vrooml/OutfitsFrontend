package com.example.outfits.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.outfits.Adapter.ClothesRecyclerAdapter;
import com.example.outfits.Adapter.OutfitRecyclerAdapter;
import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.Outfit;
import com.example.outfits.MyApplication;
import com.example.outfits.R;

import java.util.ArrayList;
import java.util.List;

public class OutfitTypeFragment extends Fragment{
    RecyclerView recyclerView;
    OutfitRecyclerAdapter adapter;
    List<Outfit> outfits;

    public OutfitTypeFragment(){
        // Required empty public constructor
    }

    public static OutfitTypeFragment newInstance(List<Outfit> outfits){
        OutfitTypeFragment fragment=new OutfitTypeFragment();
        fragment.outfits=outfits;
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
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter=new OutfitRecyclerAdapter(outfits,this);
        recyclerView.setAdapter(adapter);
        return view;
    }
}