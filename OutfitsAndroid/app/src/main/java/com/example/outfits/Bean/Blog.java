package com.example.outfits.Bean;

public class Blog {
    private int blogId;
    private String blogTitle;
    private String blogPic;
    private int userId;
    private String blogIntroduce;
    public int getBlogId() {
        return blogId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public String getBlogPic() {
        return blogPic;
    }

    public String getBlogIntroduce() {
        return blogIntroduce;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public void setBlogPic(String blogPic) {
        this.blogPic = blogPic;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBlogIntroduce(String blogIntroduce) {
        this.blogIntroduce=blogIntroduce;
    }
}
