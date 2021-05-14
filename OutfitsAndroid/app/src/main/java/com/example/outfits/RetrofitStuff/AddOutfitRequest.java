package com.example.outfits.RetrofitStuff;

import java.util.List;

public class AddOutfitRequest{
    private int occasionId;
    private String introduce;
    private List<Integer> clothingIdArray;

    public AddOutfitRequest(int occasionId,String introduce,List<Integer> clothingIdArray){
        this.occasionId=occasionId;
        this.introduce=introduce;
        this.clothingIdArray=clothingIdArray;
    }

    public int getOccasionId(){
        return occasionId;
    }

    public void setOccasionId(int occasionId){
        this.occasionId=occasionId;
    }

    public String getIntroduce(){
        return introduce;
    }

    public void setIntroduce(String introduce){
        this.introduce=introduce;
    }

    public List<Integer> getClothingIdArray(){
        return clothingIdArray;
    }

    public void setClothingIdArray(List<Integer> clothingIdArray){
        this.clothingIdArray=clothingIdArray;
    }
}
