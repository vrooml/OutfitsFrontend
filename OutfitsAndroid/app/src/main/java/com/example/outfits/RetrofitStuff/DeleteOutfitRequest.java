package com.example.outfits.RetrofitStuff;

public class DeleteOutfitRequest{
    private int matchId;

    public DeleteOutfitRequest(int matchId){
        this.matchId=matchId;
    }

    public int getMatchId(){
        return matchId;
    }

    public void setMatchId(int matchId){
        this.matchId=matchId;
    }
}
