package com.example.outfits.RetrofitStuff;

public class GetRecommendOutfitRequest{
    private int offset;
    private String location;

    public GetRecommendOutfitRequest(int offset,String location){
        this.offset=offset;
        this.location=location;
    }

    public int getOffset(){
        return offset;
    }

    public void setOffset(int offset){
        this.offset=offset;
    }
}
