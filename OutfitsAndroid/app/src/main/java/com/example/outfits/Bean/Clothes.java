package com.example.outfits.Bean;

import java.io.Serializable;

public class Clothes implements Serializable{

    private int clothingId;
    private String clothingPic;

    public Clothes(int clothingId,String clothingPic){
        this.clothingId=clothingId;
        this.clothingPic=clothingPic;
    }

    public int getClothingId(){
        return clothingId;
    }

    public void setClothingId(int clothingId){
        this.clothingId=clothingId;
    }

    public String getClothingPic(){
        return clothingPic;
    }

    public void setClothingPic(String clothingPic){
        this.clothingPic=clothingPic;
    }
}
