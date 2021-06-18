package com.example.outfits.RetrofitStuff;

public class DeleteBlogRequest{
    int blogId;

    public DeleteBlogRequest(int blogId){
        this.blogId=blogId;
    }

    public int getBlogId(){
        return blogId;
    }

    public void setBlogId(int blogId){
        this.blogId=blogId;
    }
}
