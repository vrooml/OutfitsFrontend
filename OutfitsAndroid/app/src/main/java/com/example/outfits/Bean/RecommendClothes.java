package com.example.outfits.Bean;

public class RecommendClothes{
    private int clothingId;
    private String clothingPic;
    private String typeName;
    private String subtypeName;

    public RecommendClothes(int clothingId,String clothingPic,String typeName,String subtypeName){
        this.clothingId=clothingId;
        this.clothingPic=clothingPic;
        this.typeName=typeName;
        this.subtypeName=subtypeName;
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

    public String getTypeName(){
        return typeName;
    }

    public void setTypeName(String typeName){
        this.typeName=typeName;
    }

    public String getSubtypeName(){
        return subtypeName;
    }

    public void setSubtypeName(String subtypeName){
        this.subtypeName=subtypeName;
    }
}
