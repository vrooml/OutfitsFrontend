package com.example.outfits.RetrofitStuff;

public class ModifyUserInfoRequest {
    private int userId;
    private static String userNickname;
    private static String userSex;
    private String userProfile;

    public ModifyUserInfoRequest(int userId,String userNickname,String userSex,String userProfile) {
        this.userId=userId;
        this.userNickname=userNickname;
        this.userSex=userSex;
        this.userProfile=userProfile;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public static String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }
}
