package com.example.outfits.RetrofitStuff;

public class DeleteClothingRequest{
    private int clothingId;

    public DeleteClothingRequest(int clothingId){
        this.clothingId=clothingId;
    }

    public int getClothingId(){
        return clothingId;
    }

    public void setClothingId(int clothingId){
        this.clothingId=clothingId;
    }
}
