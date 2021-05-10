package com.example.outfits.RetrofitStuff;

public class RegisterRequest {
    private String phone;
    private String vercode;
    private String token;
    private String password;
    private String nickname;
    private String sex;

    public RegisterRequest(String phone,String vercode,String token,String password,String nickname,String sex){
        this.phone=phone;
        this.vercode=vercode;
        this.token=token;
        this.password=password;
        this.nickname=nickname;
        this.sex=sex;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone=phone;
    }

    public String getVercode(){
        return vercode;
    }

    public void setVercode(String vercode){
        this.vercode=vercode;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
        this.token=token;
    }
}
