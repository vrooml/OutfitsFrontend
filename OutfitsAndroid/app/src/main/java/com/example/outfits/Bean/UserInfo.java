package com.example.outfits.Bean;

public class UserInfo {
    private int userId;
    private String userAccount;
    private String userNickname;
    private String userPic;
    private String userSex;
    private String userProfile;
    public UserInfo(int userId,String userAccount,String userNickname,String userPic,String userSex,String userProfile){
        this.userId=userId;
        this.userAccount=userAccount;
        this.userNickname=userNickname;
        this.userPic=userPic;
        this.userSex=userSex;
        this.userProfile=userProfile;
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

    public String getUserSex(){
        return userSex;
    }

    public void setUserSex(String userSex){
        this.userSex=userSex;
    }

    public String getUserProfile(){
        return userProfile;
    }

    public void setUserProfile(String userProfile){
        this.userProfile=userProfile;
    }
}
