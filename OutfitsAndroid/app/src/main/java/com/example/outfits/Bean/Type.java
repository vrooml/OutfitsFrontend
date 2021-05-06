package com.example.outfits.Bean;

import org.litepal.annotation.Column;
import org.litepal.crud.LitePalSupport;

public class Type extends LitePalSupport{
    @Column(unique=true)
    private int typeId;
    @Column
    private String typeName;
    @Column(ignore=true)
    private SubType[] subType;

    public Type(int typeId,String typeName,SubType[] subType){
        this.typeId=typeId;
        this.typeName=typeName;
        this.subType=subType;
    }

    public int getTypeId(){
        return typeId;
    }

    public void setTypeId(int typeId){
        this.typeId=typeId;
    }

    public String getTypeName(){
        return typeName;
    }

    public void setTypeName(String typeName){
        this.typeName=typeName;
    }

    public SubType[] getSubType(){
        return subType;
    }

    public void setSubType(SubType[] subType){
        this.subType=subType;
    }

    public static class SubType{
        int subtypeId;
        String subtypeName;

        public SubType(int subtypeId,String subtypeName){
            this.subtypeId=subtypeId;
            this.subtypeName=subtypeName;
        }
    }

}
