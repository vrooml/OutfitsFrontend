package com.example.outfits.RetrofitStuff;

public class ModifyClothesRequest{
    int clothingId;
    int subtypeId;

    public ModifyClothesRequest(int clothingId,int subtypeId){
        this.clothingId=clothingId;
        this.subtypeId=subtypeId;
    }

    public int getClothingId(){
        return clothingId;
    }

    public void setClothingId(int clothingId){
        this.clothingId=clothingId;
    }

    public int getSubtypeId(){
        return subtypeId;
    }

    public void setSubtypeId(int subtypeId){
        this.subtypeId=subtypeId;
    }
}
