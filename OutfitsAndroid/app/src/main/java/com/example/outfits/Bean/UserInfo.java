package com.example.outfits.Bean;

public class UserInfo {
    public UserInfo(String userNickname, String userSex, String userProfile, int userId) {
        this.userNickname = userNickname;
        this.userSex = userSex;
        this.userProfile = userProfile;
        this.userId = userId;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    private String userNickname;
    private String userSex;

    public String getUserNickname() {
        return userNickname;
    }

    public String getUserPic() {
        return userPic;
    }

    private String userPic;

    public String getUserSex() {
        return userSex;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    private String userProfile;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    private String userAccount;
}
