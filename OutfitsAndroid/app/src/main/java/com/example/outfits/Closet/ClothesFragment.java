package com.example.outfits.Closet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.outfits.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClothesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClothesFragment extends Fragment{


    List<String> mSubTypes;
    List<Clothes>[] mClothes;

    public ClothesFragment(){
        // Required empty public constructor
    }

    public static ClothesFragment newInstance(List<String> subTypes,List<Clothes>[] clothes){
        ClothesFragment fragment=new ClothesFragment();
        fragment.mSubTypes=subTypes;
        fragment.mClothes=clothes;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_clothes,container,false);
    }
}