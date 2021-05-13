package com.example.outfits.RetrofitStuff;

public class ModifyUserInfoRequest {
    private String userNickname;
    private String userSex;
    private String userProfile;

    public ModifyUserInfoRequest(String userNickname,String userSex,String userProfile){
        this.userNickname=userNickname;
        this.userSex=userSex;
        this.userProfile=userProfile;
    }

    public String getUserNickname(){
        return userNickname;
    }

    public void setUserNickname(String userNickname){
        this.userNickname=userNickname;
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
