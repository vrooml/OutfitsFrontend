package com.example.outfits.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.outfits.R;

public class RecommendOutfitFragment extends Fragment{
    RecyclerView recyclerView;


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
        return inflater.inflate(R.layout.fragment_recommend_outfit,container,false);
    }
}