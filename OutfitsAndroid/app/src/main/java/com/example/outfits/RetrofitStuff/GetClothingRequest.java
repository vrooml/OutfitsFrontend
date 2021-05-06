package com.example.outfits.RetrofitStuff;

public class GetClothingRequest{
    private int typeId;

    public GetClothingRequest(int typeId){
        this.typeId=typeId;
    }

    public int getTypeId(){
        return typeId;
    }

    public void setTypeId(int typeId){
        this.typeId=typeId;
    }
}
