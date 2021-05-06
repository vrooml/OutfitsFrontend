package com.example.outfits.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserClient {
    //获取个人资料
    @FormUrlEncoded
    @POST("/user/getDetail")
    Call<UserInfoReturn> getUserInfo(@Field("token") String token);

    //修改个人资料
    @POST("/user/modifyInfo")
    Call<UserInfoReturn> modifyUserInfo(@Body UserInfo userInfoNew);

    //获取我的收藏
    @FormUrlEncoded
    @POST("/user/getCollection")
    Call<BlogListReturn> getCollection(@Field("token") String token, @Field("userId") int userId);

    //获取我的发布
    @FormUrlEncoded
    @POST("/user/getBlog")
    Call<BlogListReturn> getBlog(@Field("token") String token, @Field("userId") int userId);
}
