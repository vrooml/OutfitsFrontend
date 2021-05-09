package com.example.outfits.Bean;

import java.util.List;

public class Type{

    private int typeId;
    private String typeName;
    private List<SubType> subType;

    public Type(int typeId,String typeName,List<SubType> subType){
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

    public List<SubType> getSubType(){
        return subType;
    }

    public void setSubType(List<SubType> subType){
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
