package com.example.outfits.RetrofitStuff;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostInterfaces{
    //获取验证码
    @POST("/fdb1.0.0/sms/send")
    Call<ResponseModel<String>> postAuthCode(@Body AuthCodeRequest token);

    @POST("/fdb1.0.0/user/modifyInfo")
    Call<ResponseModel> postModifyUserInfoRequest(@Body ModifyUserInfoRequest modifyUserInfoRequest);

}
