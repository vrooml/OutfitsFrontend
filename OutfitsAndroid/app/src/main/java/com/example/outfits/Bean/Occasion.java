package com.example.outfits.Bean;

public class Occasion{
    private int occasionId;
    private String occasionName;

    public Occasion(int occasionId,String occasionName){
        this.occasionId=occasionId;
        this.occasionName=occasionName;
    }

    public int getOccasionId(){
        return occasionId;
    }

    public void setOccasionId(int occasionId){
        this.occasionId=occasionId;
    }

    public String getOccasionName(){
        return occasionName;
    }

    public void setOccasionName(String occasionName){
        this.occasionName=occasionName;
    }
}
