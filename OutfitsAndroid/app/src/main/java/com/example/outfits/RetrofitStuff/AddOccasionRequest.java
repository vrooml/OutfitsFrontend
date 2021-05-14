package com.example.outfits.RetrofitStuff;

public class AddOccasionRequest{
    private String occasionName;

    public AddOccasionRequest(String occasionName){
        this.occasionName=occasionName;
    }

    public String getOccasionName(){
        return occasionName;
    }

    public void setOccasionName(String occasionName){
        this.occasionName=occasionName;
    }
}
