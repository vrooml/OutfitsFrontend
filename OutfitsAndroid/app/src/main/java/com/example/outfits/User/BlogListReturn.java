package com.example.outfits.User;

import java.util.List;

public class BlogListReturn {
    private int code;
    private String msg;
    private List<Blog> data;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public List<Blog> getData() {
        return data;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(List<Blog> data) {
        this.data = data;
    }
}
