package com.example.outfits.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.outfits.Adapter.OutfitRecyclerAdapter;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Outfit;
import com.example.outfits.R;
import com.example.outfits.RetrofitStuff.GetClothingRequest;
import com.example.outfits.RetrofitStuff.GetOutfitRequest;
import com.example.outfits.Utils.LoadingDialog;
import com.example.outfits.Utils.RetrofitUtil;

import java.util.ArrayList;
import java.util.List;

public class OutfitTypeFragment extends Fragment{
    RecyclerView recyclerView;
    public OutfitRecyclerAdapter adapter;
    public List<Outfit> outfits;
    public LoadingDialog dialog;
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
        adapter=new OutfitRecyclerAdapter(outfits,this);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        GetOutfitRequest getOutfitRequest=new GetOutfitRequest(occasion.getOccasionId());
        dialog=new LoadingDialog.Builder(getContext())
                .setMessage("加载中...")
                .setCancelable(false)
                .create();
        dialog.show();
        RetrofitUtil.postGetOutfit("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNjIwNTUwNzQ1LCJzdWIiOiIxODk2MDE0NzI3MiIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDgwOTk0NX0.HjCnvUYa6m7MjRUMpMd_hfiTNwE71oMdAaNnzcr_-Wo"
                ,getOutfitRequest,outfits,this,dialog);

    }

}