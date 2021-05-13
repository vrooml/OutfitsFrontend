package com.example.outfits.RetrofitStuff;

public class DeleteOccasionRequest{
    private int occasionId;

    public DeleteOccasionRequest(int occasionId){
        this.occasionId=occasionId;
    }

    public int getOccasionId(){
        return occasionId;
    }

    public void setOccasionId(int occasionId){
        this.occasionId=occasionId;
    }
}
