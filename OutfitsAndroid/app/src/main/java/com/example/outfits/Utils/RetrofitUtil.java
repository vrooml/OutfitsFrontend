package com.example.outfits.Utils;

import android.app.Dialog;
import android.util.Log;
import android.widget.Toast;


import com.example.outfits.Bean.Blog;
import com.example.outfits.Bean.Clothes;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.MyApplication;
import com.example.outfits.RetrofitStuff.AuthCodeRequest;
import com.example.outfits.RetrofitStuff.GetClothingRequest;
import com.example.outfits.RetrofitStuff.LoginRequest;
import com.example.outfits.RetrofitStuff.ModifyUserInfoRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.RegisterRequest;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.UI.ClosetFragment;
import com.example.outfits.UI.ClothesFragment;

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
<<<<<<< Updated upstream
    public static Retrofit retrofit=new Retrofit.Builder()
=======
    private static Retrofit retrofit=new Retrofit.Builder()
>>>>>>> Stashed changes
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

    public static void postLogin(String token,LoginRequest loginRequest){
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
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(), "token", response.body().getData());
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

    public static void postRegister(String token,RegisterRequest registerRequest){
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

    public static void postModifyUserInfo(ModifyUserInfoRequest modifyUserInfoRequest){
        final PostInterfaces request1=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request1.postModifyUserInfoRequest(modifyUserInfoRequest);
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
     *
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
    public static List<Blog> getBlog(String token, int userId){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<List<Blog>>> call=request.getBlog(token,userId);
        final List<Blog>[] blogList = new List[]{new ArrayList<>()};
        call.enqueue(new Callback<ResponseModel<List<Blog>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<Blog>>> call, Response<ResponseModel<List<Blog>>> response) {
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        blogList[0] =response.body().getData();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<Blog>>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
        return blogList[0];
    }

    /**
     * 获取我的收藏博客
     *
     * @param token token
     * */
    public static List<Blog> getCollection(String token,int userId){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<List<Blog>>> call=request.getCollection(token,userId);
        final List<Blog>[] blogList = new List[]{new ArrayList<>()};
        call.enqueue(new Callback<ResponseModel<List<Blog>>>() {
            @Override
            public void onResponse(Call<ResponseModel<List<Blog>>> call, Response<ResponseModel<List<Blog>>> response) {
                if(response.body()!=null){
                    blogList[0] =response.body().getData();
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<Blog>>> call, Throwable t) {
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
        return blogList[0];
    }

    
}
