package com.example.outfits.Bean;

public class Collection {
    private String blogPic;
    private String use_pic;
    private String user_nickname;
    private String blog_released_time;
    private int blogId;
    private int userId;

    public void setBlogPic(String blogPic) {
        this.blogPic = blogPic;
    }

    public void setUse_pic(String use_pic) {
        this.use_pic = use_pic;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public void setBlog_released_time(String blog_released_time) {
        this.blog_released_time = blog_released_time;
    }

    public void setBlogId(int blogId) {
        this.blogId = blogId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }

    public void setUser_state(int user_state) {
        this.user_state = user_state;
    }

    private String blogTitle;
    private int user_state;

    public String getBlogPic() {
        return blogPic;
    }

    public String getUse_pic() {
        return use_pic;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public String getBlog_released_time() {
        return blog_released_time;
    }

    public int getBlogId() {
        return blogId;
    }

    public int getUserId() {
        return userId;
    }

    public String getBlogTitle() {
        return blogTitle;
    }

    public int getUser_state() {
        return user_state;
    }

    public Collection(String blogPic, String use_pic, String user_nickname, String blog_released_time, int blogId, int userId, String blogTitle, int user_state) {
        this.blogPic = blogPic;
        this.use_pic = use_pic;
        this.user_nickname = user_nickname;
        this.blog_released_time = blog_released_time;
        this.blogId = blogId;
        this.userId = userId;
        this.blogTitle = blogTitle;
        this.user_state = user_state;
    }
}
