package com.example.outfits.Bean;

import com.example.outfits.Bean.Clothes;

import java.io.Serializable;

public class SubTypeClothingBean implements Serializable{
    private int subtypeId;
    private String subtypeName;
    private Clothes[] clothing;

    public SubTypeClothingBean(int subtypeId,String subtypeName,Clothes[] clothing){
        this.subtypeId=subtypeId;
        this.subtypeName=subtypeName;
        this.clothing=clothing;
    }

    public int getSubtypeId(){
        return subtypeId;
    }

    public void setSubtypeId(int subtypeId){
        this.subtypeId=subtypeId;
    }

    public String getSubtypeName(){
        return subtypeName;
    }

    public void setSubtypeName(String subtypeName){
        this.subtypeName=subtypeName;
    }

    public Clothes[] getClothing(){
        return clothing;
    }

    public void setClothing(Clothes[] clothing){
        this.clothing=clothing;
    }
}
