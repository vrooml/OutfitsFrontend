package com.example.outfits.Bean;

public class UserInfo {
    private int userId;
    private String userAccount;
    private String userNickname;
    private String userPic;
    private String sex;
    private String profile;
    public UserInfo(int userId,String userAccount,String userNickname,String userPic,String sex,String profile){
        this.userId=userId;
        this.userAccount=userAccount;
        this.userNickname=userNickname;
        this.userPic=userPic;
        this.sex=sex;
        this.profile=profile;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId=userId;
    }

    public String getUserAccount(){
        return userAccount;
    }

    public void setUserAccount(String userAccount){
        this.userAccount=userAccount;
    }

    public String getUserNickname(){
        return userNickname;
    }

    public void setUserNickname(String userNickname){
        this.userNickname=userNickname;
    }

    public String getUserPic(){
        return userPic;
    }

    public void setUserPic(String userPic){
        this.userPic=userPic;
    }

    public String getSex(){
        return sex;
    }

    public void setSex(String sex){
        this.sex=sex;
    }

    public String getProfile(){
        return profile;
    }

    public void setProfile(String profile){
        this.profile=profile;
    }
}
