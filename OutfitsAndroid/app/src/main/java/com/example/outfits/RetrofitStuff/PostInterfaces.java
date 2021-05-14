package com.example.outfits.RetrofitStuff;

import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.Collection;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Outfit;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface PostInterfaces{

//TODO 登录注册等

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

//TODO 衣柜

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

    //删除衣物
    @POST("/wardrobe/deleteClothing")
    Call<ResponseModel> postDeleteClothing(@Body DeleteClothingRequest deleteClothingRequest);

//TODO 搭配

    //请求场合
    @POST("/match/listOccasion")
    Call<ResponseModel<Occasion[]>> getOccasion(@Header("token") String token);

    //新建场合
    @POST("/match/addOccasion")
    Call<ResponseModel> addOccasion(@Header("token") String token,
                                    @Body AddOccasionRequest addOccasionRequest);

    //删除场合
    @POST("/match/deleteOccasion")
    Call<ResponseModel> deleteOccasion(@Header("token") String token,
                                    @Body DeleteOccasionRequest deleteOccasionRequest);

    //请求子类别的搭配
    @POST("/match/listMatch")
    Call<ResponseModel<Outfit[]>> postGetOutfit(@Header("token") String token,
                                                @Body GetOutfitRequest getOutfitRequest);

    //请求添加搭配
    @POST("/match/addMatch")
    Call<ResponseModel> postAddOutfit(@Header("token") String token,
                                                @Body AddOutfitRequest addOutfitRequest);

    //请求添加搭配
    @POST("/match/deleteMatch")
    Call<ResponseModel> postDeleteOutfit(@Header("token") String token,
                                      @Body DeleteOutfitRequest deleteOutfitRequest);

//TODO 我的

    //获取个人资料
    @POST("/user/getDetail")
    Call<ResponseModel<UserInfo>> getUserInfo(@Header("token") String token);

    //获取用户资料
    @POST("/user/getIntro")
    Call<ResponseModel<UserInfo>> getIntro(@Header("token") String token, @Body GetBlogRequest getBlogRequest);

    //修改个人资料
    @POST("/user/modifyInfo")
    Call<ResponseModel> modifyUserInfo(@Header("token") String token,@Body ModifyUserInfoRequest modifyUserInfoRequest);

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

    //发布博客
    @Multipart
    @POST("/blog/post")
    Call<ResponseModel> postBlog(@Header("token") String token,
                                           @Part("blogArticle") String blogArticle,
                                           @Part("blogTitle") String blogTitle,
                                           @Part List<MultipartBody.Part> uploadPic);
}
