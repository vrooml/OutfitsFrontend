package com.example.outfits.Utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;


import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.MyApplication;
import com.example.outfits.RetrofitStuff.AuthCodeRequest;
import com.example.outfits.RetrofitStuff.GetClothingRequest;
import com.example.outfits.RetrofitStuff.ModifyUserInfoRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.UI.UserInfoActivity;

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
    private static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://2457sx9279.qicp.vip")
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

    public static void postModifyUserInfo(ModifyUserInfoRequest modifyUserInfoRequest){
        final PostInterfaces request1=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request1.postModifyUserInfoRequest(modifyUserInfoRequest);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
//                        RetrofitUtil.postModifyUserInfo(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"));
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
    public static void postGetClothing(String token,GetClothingRequest getClothingRequest,List<SubTypeClothingBean> subTypeClothingBeans){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<SubTypeClothingBean[]>> call=request.postGetClothing(token,getClothingRequest);
        call.enqueue(new Callback<ResponseModel<SubTypeClothingBean[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<SubTypeClothingBean[]>> call,Response<ResponseModel<SubTypeClothingBean[]>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        for(SubTypeClothingBean i:response.body().getData()){
                            subTypeClothingBeans.add(i);
                        }
                        Log.e("debug","onResponse: "+response.body().getData());

                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<SubTypeClothingBean[]>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取类别表
     *
     * @param types 要填充的类别
     */
    public static void getType(List<Type> types){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<Type[]>> call=request.getType();
        call.enqueue(new Callback<ResponseModel<Type[]>>(){
            @Override
            public void onResponse(Call<ResponseModel<Type[]>> call,Response<ResponseModel<Type[]>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        types.addAll(Arrays.asList(response.body().getData()));
                        Log.e("debug","onResponse: "+response.body().getData());

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
        UserInfo userInfo=null;
        call.enqueue(new Callback<ResponseModel<UserInfo>>(){
            @Override
            public void onResponse(Call<ResponseModel<UserInfo>> call,Response<ResponseModel<UserInfo>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        UserInfo userInfo=response.body().getData();
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
        return userInfo;
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

}
