package com.example.outfits.RetrofitStuff;

public class GetRecommendOutfitRequest{
    private int offset;

    public GetRecommendOutfitRequest(int offset){
        this.offset=offset;
    }

    public int getOffset(){
        return offset;
    }

    public void setOffset(int offset){
        this.offset=offset;
    }
}
