package com.example.outfits.RetrofitStuff;

public class ForgetpasswordRequest {
    private String phone;
    private String vercode;
    private String password;

    public ForgetpasswordRequest(String phone,String vercode,String password){
        this.phone=phone;
        this.vercode=vercode;
        this.password=password;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
