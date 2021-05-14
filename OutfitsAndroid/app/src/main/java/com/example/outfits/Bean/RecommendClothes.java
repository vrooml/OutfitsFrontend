package com.example.outfits.Bean;

public class RecommendClothes{
    private int clothingId;
    private String clothingPic;
    private String clothingType;
    private String clothingSubtype;

    public RecommendClothes(int clothingId,String clothingPic,String clothingType,String clothingSubtype){
        this.clothingId=clothingId;
        this.clothingPic=clothingPic;
        this.clothingType=clothingType;
        this.clothingSubtype=clothingSubtype;
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

    public String getClothingType(){
        return clothingType;
    }

    public void setClothingType(String clothingType){
        this.clothingType=clothingType;
    }

    public String getClothingSubtype(){
        return clothingSubtype;
    }

    public void setClothingSubtype(String clothingSubtype){
        this.clothingSubtype=clothingSubtype;
    }
}
