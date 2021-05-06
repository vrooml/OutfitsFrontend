package com.example.outfits.Utils;

import android.widget.Toast;


import com.example.outfits.MyApplication;
import com.example.outfits.RetrofitStuff.AuthCodeRequest;
import com.example.outfits.RetrofitStuff.ModifyUserInfoRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;

import java.util.concurrent.TimeUnit;

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
    private  static Retrofit retrofit1=new Retrofit.Builder()
            .baseUrl("http://2457sx9279.qicp.vip")
            .addConverterFactory(GsonConverterFactory.create())
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
        final PostInterfaces request1=retrofit1.create(PostInterfaces.class);
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

}
