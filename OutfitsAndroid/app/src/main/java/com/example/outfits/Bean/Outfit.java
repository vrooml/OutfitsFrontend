package com.example.outfits.Bean;

import java.util.List;

public class Outfit{
    private int matchId;
    private String introduce;
    private List<Clothes> clothing;

    public Outfit(int matchId,String introduce,List<Clothes> clothing){
        this.matchId=matchId;
        this.introduce=introduce;
        this.clothing=clothing;
    }

    public int getMatchId(){
        return matchId;
    }

    public void setMatchId(int matchId){
        this.matchId=matchId;
    }

    public String getIntroduce(){
        return introduce;
    }

    public void setIntroduce(String introduce){
        this.introduce=introduce;
    }

    public List<Clothes> getClothing(){
        return clothing;
    }

    public void setClothing(List<Clothes> clothing){
        this.clothing=clothing;
    }
}
