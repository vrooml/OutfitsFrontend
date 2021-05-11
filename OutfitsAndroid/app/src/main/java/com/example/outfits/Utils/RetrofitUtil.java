package com.example.outfits.Utils;

import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;


import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Outfit;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.MyApplication;
import com.example.outfits.RetrofitStuff.AuthCodeRequest;
import com.example.outfits.RetrofitStuff.ForgetpasswordRequest;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.RetrofitStuff.GetClothingRequest;
import com.example.outfits.RetrofitStuff.GetOutfitRequest;
import com.example.outfits.RetrofitStuff.LoginRequest;
import com.example.outfits.RetrofitStuff.ModifyUserInfoRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.RegisterRequest;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.UI.ClosetFragment;
import com.example.outfits.UI.ClothesFragment;
import com.example.outfits.UI.OutfitFragment;
import com.example.outfits.UI.OutfitTypeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;

public class RetrofitUtil{

    private static OkHttpClient okHttpClient=new OkHttpClient.Builder()
            .retryOnConnectionFailure(false)
            .connectTimeout(10000,TimeUnit.MILLISECONDS)
            .build();
    public static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://121.5.100.116:8080")
            .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析
            .client(okHttpClient)
            .build();

/*
主界面、登录等界面
 */

    /**
     * 获取验证码
     * @param authCodeRequest 获取验证码请求体
     */
    public static void postAuthCode(AuthCodeRequest authCodeRequest){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<String>> call=request.postAuthCode(authCodeRequest);
        call.enqueue(new Callback<ResponseModel<String>>(){
            @Override
            public void onResponse(Call<ResponseModel<String>> call,Response<ResponseModel<String>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"验证码发送成功，注意查收",Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"authCodeToken",response.body().getData());
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<String>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 登录
     * @param loginRequest 登录请求体
     */
    public static void postLogin(String token, LoginRequest loginRequest){
        token=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"loginCodetoken");
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<String>> call=request.login(token,loginRequest);
        call.enqueue(new Callback<ResponseModel<String>>() {
            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == SUCCESS_CODE) {
                        RetrofitUtil.getUserInfo(response.body().getData());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(), "phoneNum", loginRequest.getPhone());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(), "Usertoken", response.body().getData());
                    } else {
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<ResponseModel<String>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(), FAILED, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 注册
     * @param registerRequest 注册请求体
     */
    public static void postRegister(String token, RegisterRequest registerRequest){
        token=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"authCodeToken");
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postRegister(token,
                registerRequest);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"phoneNum",registerRequest.getPhone());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"loginCodetoken", (String) response.body().getData());
                        Toast.makeText(MyApplication.getContext(),"注册成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void postForgetPassword(String token, ForgetpasswordRequest forgetpasswordRequest){
        token=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"authCodeToken");
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postForgetPassword(token,
                forgetpasswordRequest);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"phoneNum",forgetpasswordRequest.getPhone());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"loginCodetoken", (String) response.body().getData());
                        Toast.makeText(MyApplication.getContext(),"修改密码成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 请求修改个人资料
     *
     * @param token token
     * @param modifyUserInfoRequest 修改个人资料请求体
     */
    public static void postModifyUserInfo(String token,ModifyUserInfoRequest modifyUserInfoRequest){
        token=SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"Usertoken");
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postModifyUserInfo(token,modifyUserInfoRequest);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        RetrofitUtil.getUserInfo(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"));
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"gender",ModifyUserInfoRequest.getUserSex());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"username",ModifyUserInfoRequest.getUserNickname());
                        Toast.makeText(MyApplication.getContext(),"修改信息成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取某类别衣物
     *
     * @param getClothingRequest 获取某类别衣物请求体
     * @param subTypeClothingBeans 要填充的子类别数组
     */
    public static void postGetClothing(String token,GetClothingRequest getClothingRequest,List<SubTypeClothingBean> subTypeClothingBeans,ClothesFragment clothesFragment,LoadingDialog dialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<SubTypeClothingBean[]>> call=request.postGetClothing(token,getClothingRequest);
        call.enqueue(new Callback<ResponseModel<SubTypeClothingBean[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<SubTypeClothingBean[]>> call,Response<ResponseModel<SubTypeClothingBean[]>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        subTypeClothingBeans.clear();
                        for(SubTypeClothingBean i : response.body().getData()){
                            subTypeClothingBeans.add(i);
                        }
                        clothesFragment.adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
                Log.e("debug","onResponse: "+"GetClothing!"+clothesFragment.type.getTypeName());
            }

            @Override
            public void onFailure(Call<ResponseModel<SubTypeClothingBean[]>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    /**
     * 获取类别表
     *
     * @param types 要填充的类别
     */
    public static void getType(List<Type> types,ClosetFragment closetFragment){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<Type[]>> call=request.getType();
        call.enqueue(new Callback<ResponseModel<Type[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<Type[]>> call,Response<ResponseModel<Type[]>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        types.clear();
                        for(Type i:response.body().getData()){
                            types.add(i);
                        }

                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        closetFragment.init();

                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Type[]>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * 上传衣物
     *
     * @param token token
     * @param subtypeIds 子类别id（按顺序对应衣物图片列表）
     * @param uploadPic 请求中的衣物图片部分
     */
    public static void postUploadClothing(String token,List<Integer> subtypeIds,List<MultipartBody.Part> uploadPic){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postUploadClothing(token,subtypeIds,uploadPic);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"上传衣物成功！",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 获取场合表
     *
     * @param occasions 要填充的场合
     */
    public static void getOccasion(String token,List<Occasion> occasions,OutfitFragment outfitFragment){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<Occasion[]>> call=request.getOccasion(token);
        call.enqueue(new Callback<ResponseModel<Occasion[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<Occasion[]>> call,Response<ResponseModel<Occasion[]>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        occasions.clear();
                        for(Occasion i:response.body().getData()){
                            occasions.add(i);
                        }
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                        outfitFragment.init();

                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Occasion[]>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取某场合搭配
     *
     */
    public static void postGetOutfit(String token,GetOutfitRequest getOutfitRequest,List<Outfit> outfits,OutfitTypeFragment outfitTypeFragment,LoadingDialog dialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<Outfit[]>> call=request.postGetOutfit(token,getOutfitRequest);
        call.enqueue(new Callback<ResponseModel<Outfit[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<Outfit[]>> call,Response<ResponseModel<Outfit[]>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        outfits.clear();
                        for(Outfit i : response.body().getData()){
                            outfits.add(i);
                        }
                        outfitTypeFragment.adapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
                Log.e("debug","onResponse: "+"GetClothing!"+outfitTypeFragment.occasion.getOccasionName());
            }

            @Override
            public void onFailure(Call<ResponseModel<Outfit[]>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    /**
     * 请求用户信息
     *
     * @param token token
     */
    public static UserInfo getUserInfo(String token){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<UserInfo>> call=request.getUserInfo(token);
        final UserInfo[] userInfo = {null};

        call.enqueue(new Callback<ResponseModel<UserInfo>>(){
            @Override
            public void onResponse(Call<ResponseModel<UserInfo>> call,Response<ResponseModel<UserInfo>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        userInfo[0] =response.body().getData();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
        return userInfo[0];
    }

    /**
     * 修改用户信息
     * //TODO记得还要把注释改掉，该加的加上
     * @param token token
     */
    public static void modifyUserInfo(String token,UserInfo userInfoNew){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.modifyUserInfo(token,userInfoNew);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取我的发布博客
     *
     * @param token token
     * */
    public static void getBlog(String token, GetBlogRequest getBlogRequest, List<Blog> blogList, LoadingDialog dialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<Blog[]>> call=request.getBlog(token,getBlogRequest);
        call.enqueue(new Callback<ResponseModel<Blog[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<Blog[]>> call, Response<ResponseModel<Blog[]>> response) {
                if(response.body() != null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        for(Blog i : response.body().getData()){
                            blogList.add(i);
                            Toast.makeText(MyApplication.getContext(), blogList.get(0).getBlogTitle().toString(), Toast.LENGTH_SHORT);
                        }
                    }else {
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Blog[]>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                dialog.dismiss();;
            }
        });
    }

    /**
     * 获取我的收藏博客
     *
     * @param token token
     * */
    public static void getCollection(String token,GetBlogRequest getBlogRequest, List<Blog> blogList, LoadingDialog dialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<Blog[]>> call=request.getCollection(token,getBlogRequest);
        call.enqueue(new Callback<ResponseModel<Blog[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<Blog[]>> call, Response<ResponseModel<Blog[]>> response) {
                if(response.body()!=null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        for(Blog i : response.body().getData()){
                            blogList.add(i);
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Blog[]>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
    }

    /**
     * 获取我的粉丝列表
     *
     * @param token token
     * */
    public  static List<UserInfo> getSubscriber(String token, GetBlogRequest getBlogRequest, List<UserInfo> userInfoList){
        final PostInterfaces request = retrofit.create(PostInterfaces.class);
        Call<ResponseModel<UserInfo[]>> call = request.getSubscriber(token, getBlogRequest);
        call.enqueue(new Callback<ResponseModel<UserInfo[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserInfo[]>> call, Response<ResponseModel<UserInfo[]>> response) {
                if(response.body() != null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        for(UserInfo i : response.body().getData()){
                            userInfoList.add(i);
                            Toast.makeText(MyApplication.getContext(), String.valueOf(userInfoList.size()), Toast.LENGTH_SHORT).show();
                            Toast.makeText(MyApplication.getContext(), userInfoList.get(0).getUserNickname(), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo[]>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
        return userInfoList;
    }

    /**
     * 获取我的关注列表
     *
     * @param token token
     * */
    public  static void getSubscription(String token, GetBlogRequest getBlogRequest, List<UserInfo> userInfoList, LoadingDialog dialog){
        final PostInterfaces request = retrofit.create(PostInterfaces.class);
        Call<ResponseModel<UserInfo[]>> call = request.getSubscriber(token, getBlogRequest);
        call.enqueue(new Callback<ResponseModel<UserInfo[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<UserInfo[]>> call, Response<ResponseModel<UserInfo[]>> response) {
                if(response.body() != null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        for(UserInfo i : response.body().getData()){
                            userInfoList.add(i);
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo[]>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    
}
