package com.example.outfits.RetrofitStuff;

public class GetOutfitRequest{
    private int occasionId;

    public GetOutfitRequest(int occasionId){
        this.occasionId=occasionId;
    }

    public int getOccasionId(){
        return occasionId;
    }

    public void setOccasionId(int occasionId){
        this.occasionId=occasionId;
    }
}
