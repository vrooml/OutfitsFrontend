package com.example.outfits.RetrofitStuff;

import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.Collection;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Outfit;
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
    //获取验证码
    @POST("/user/sendSmsCode")
    Call<ResponseModel<String>> postAuthCode(@Body AuthCodeRequest authCodeRequest);

    //登录
    @POST("/user/login")
    Call<ResponseModel<String>> login(@Header("token") String token,@Body LoginRequest loginRequest);

    //注册
    @POST("/user/register")
    Call<ResponseModel> postRegister(@Header("token") String token,@Body RegisterRequest registerRequest);

    //忘记密码
    @POST("/user/info/update/password")
    Call<ResponseModel> postForgetPassword(@Header("token") String token,@Body ForgetpasswordRequest forgetpasswordRequest1);

    //修改个人资料
    @POST("/user/modifyInfo")
    Call<ResponseModel> postModifyUserInfo(@Header("token") String token,@Body ModifyUserInfoRequest modifyUserInfoRequest);

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
                                           @Part("subtypeId") List<Integer> subtypeIds,
                                           @Part List<MultipartBody.Part> uploadPic);

    //请求场合
    @POST("/match/listOccasion")
    Call<ResponseModel<Occasion[]>> getOccasion(@Header("token") String token);

    //请求子类别的搭配
    @POST("/match/listMatch")
    Call<ResponseModel<Outfit[]>> postGetOutfit(@Header("token") String token,
                                                @Body GetOutfitRequest getOutfitRequest);


    //获取个人资料
    @FormUrlEncoded
    @POST("/user/getDetail")
    Call<ResponseModel<UserInfo>> getUserInfo(@Header("token") String token);

    //修改个人资料
    @POST("/user/modifyInfo")
    Call<ResponseModel> modifyUserInfo(@Header("token") String token,@Body UserInfo userInfoNew);

    //获取我的收藏
    @POST("/blog/getCollection")
    Call<ResponseModel<Collection[]>> getCollection(@Header("token") String token, @Body GetBlogRequest getBlogRequest);

    //获取我的发布
    @POST("/user/getBlog")
    Call<ResponseModel<Blog[]>> getBlog(@Header("token") String token, @Body GetBlogRequest getBlogRequest);

    //获取我的粉丝列表
    @POST("/user/getSubscriber")
    Call<ResponseModel<UserInfo[]>> getSubscriber(@Header("token") String token, @Body GetBlogRequest getBlogRequest);

    //获取我的关注列表
    @POST("/user/getSubscription")
    Call<ResponseModel<UserInfo[]>> getSubscription(@Header("token") String token, @Body GetBlogRequest getBlogRequest);

}
