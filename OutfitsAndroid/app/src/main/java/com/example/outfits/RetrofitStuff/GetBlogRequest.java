package com.example.outfits.RetrofitStuff;

public class GetBlogRequest {
    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public GetBlogRequest(int userId) {
        this.userId = userId;
    }
}
