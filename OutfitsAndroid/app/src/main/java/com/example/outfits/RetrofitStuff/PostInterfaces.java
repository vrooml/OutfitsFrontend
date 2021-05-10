package com.example.outfits.RetrofitStuff;

import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface PostInterfaces{
    @POST("/user/login")
    Call<ResponseModel<String>> login(@Header("token") String token,@Body LoginRequest loginRequest);

    @POST("/user/register")
    Call<ResponseModel> postRegister(@Header("token") String token,@Body RegisterRequest registerRequest);
    //获取验证码
    @POST("/user/sendSmsCode")
    Call<ResponseModel<String>> postAuthCode(@Body AuthCodeRequest authCodeRequest);

    @POST("/user/modifyInfo")
    Call<ResponseModel> postModifyUserInfoRequest(@Body ModifyUserInfoRequest modifyUserInfoRequest);

    //请求子类别的衣物
    @POST("/wardrobe/getClothing")
    Call<ResponseModel<SubTypeClothingBean[]>> postGetClothing(@Header("token") String token,
                                                               @Body GetClothingRequest getClothingRequest);

    //请求类别
    @GET("/wardrobe/getType")
    Call<ResponseModel<Type[]>> getType();

    //上传衣物
    @Multipart
    @POST("/wardrobe/importClothing")
    Call<ResponseModel> postUploadClothing(@Header("token") String token,
                                           @Part("subtypeIds") List<Integer> subtypeIds,
                                           @Part List<MultipartBody.Part> uploadPic);

    //获取个人资料
    @FormUrlEncoded
    @POST("/user/getDetail")
    Call<ResponseModel<UserInfo>> getUserInfo(@Header("token") String token);

    //修改个人资料
    @POST("/user/modifyInfo")
    Call<ResponseModel> modifyUserInfo(@Header("token") String token,@Body UserInfo userInfoNew);

    //获取我的收藏
    @FormUrlEncoded
    @POST("/user/getCollection")
    Call<ResponseModel<List<Blog>>> getCollection(@Header("token") String token,@Field("userId") int userId);

    //获取我的发布
    @FormUrlEncoded
    @POST("/user/getBlog")
    Call<ResponseModel<List<Blog>>> getBlog(@Header("token") String token,@Field("userId") int userId);

}
