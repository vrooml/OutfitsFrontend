package com.example.outfits.Utils;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.example.outfits.Bean.Blog;

import com.example.outfits.Bean.Collection;
import com.example.outfits.Bean.Occasion;
import com.example.outfits.Bean.Outfit;
import com.example.outfits.Bean.RecommendClothes;
import com.example.outfits.Bean.SubTypeClothingBean;
import com.example.outfits.Bean.Type;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.ForgetPasswordActivity;
import com.example.outfits.LoginActivity;
import com.example.outfits.MainActivity;
import com.example.outfits.MyApplication;
import com.example.outfits.RegisterActivity;
import com.example.outfits.RetrofitStuff.AddOccasionRequest;
import com.example.outfits.RetrofitStuff.AddOutfitRequest;
import com.example.outfits.RetrofitStuff.AuthCodeRequest;
import com.example.outfits.RetrofitStuff.DeleteClothingRequest;
import com.example.outfits.RetrofitStuff.DeleteOccasionRequest;
import com.example.outfits.RetrofitStuff.DeleteOutfitRequest;
import com.example.outfits.RetrofitStuff.ForgetpasswordRequest;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.RetrofitStuff.GetClothingRequest;
import com.example.outfits.RetrofitStuff.GetOutfitRequest;
import com.example.outfits.RetrofitStuff.GetRecommendOutfitRequest;
import com.example.outfits.RetrofitStuff.LoginRequest;
import com.example.outfits.RetrofitStuff.ModifyUserInfoRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.RegisterRequest;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.ShowFansListActivity;
import com.example.outfits.UI.ClosetFragment;
import com.example.outfits.UI.ClothesDetailActivity;
import com.example.outfits.UI.ClothesFragment;
import com.example.outfits.UI.MyBlogFragment;
import com.example.outfits.UI.MyCollectionFragment;
import com.example.outfits.UI.ChooseClothesFragment;
import com.example.outfits.UI.MyOutfitFragment;
import com.example.outfits.UI.OutfitTypeFragment;
import com.example.outfits.UI.RecommendOutfitFragment;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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
            .connectTimeout(200000,TimeUnit.MILLISECONDS)
            .build();
    public static Retrofit retrofit=new Retrofit.Builder()
            .baseUrl("http://121.5.100.116:8080")
            .addConverterFactory(GsonConverterFactory.create())//设置使用Gson解析
            .client(okHttpClient)
            .build();


//TODO 主界面、登录等界面


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
                        Toast.makeText(MyApplication.getContext(),authCodeRequest.getPhone(),Toast.LENGTH_SHORT).show();
                        Toast.makeText(MyApplication.getContext(),"验证码发送成功，注意查收",Toast.LENGTH_SHORT).show();
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"smsCodeToken",response.body().getData());
                        Toast.makeText(MyApplication.getContext(),response.body().getData(),Toast.LENGTH_SHORT).show();
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
    public static void postLogin(LoginRequest loginRequest,LoginActivity loginActivity){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<String>> call=request.login("",loginRequest);
        call.enqueue(new Callback<ResponseModel<String>>() {
            @Override
            public void onResponse(Call<ResponseModel<String>> call, Response<ResponseModel<String>> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == SUCCESS_CODE) {
                        RetrofitUtil.getUserInfoAndStore(response.body().getData());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(), "phoneNum", loginRequest.getPhone());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(), "token", response.body().getData());
                        Intent intent1=new Intent(loginActivity, MainActivity.class);
                        loginActivity.startActivity(intent1);
                        loginActivity.finish();
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
    public static void postRegister(String token,RegisterRequest registerRequest,RegisterActivity registerActivity){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postRegister(token,registerRequest);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"phoneNum",registerRequest.getPhone());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"token", (String) response.body().getData());
                        Toast.makeText(MyApplication.getContext(),"注册成功",Toast.LENGTH_SHORT).show();
                        registerActivity.finish();
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
     * 忘记密码
     * @param forgetpasswordRequest 忘记密码请求体
     */
    public static void postForgetPassword(String token,ForgetpasswordRequest forgetpasswordRequest,ForgetPasswordActivity forgetPasswordActivity){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postForgetPassword(token,forgetpasswordRequest);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"phoneNum",forgetpasswordRequest.getPhone());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"token", (String) response.body().getData());
                        Toast.makeText(MyApplication.getContext(),"修改密码成功",Toast.LENGTH_SHORT).show();
                        forgetPasswordActivity.finish();
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
     * 储存用户信息
     *
     * @param token token
     */
    public static void getUserInfoAndStore(String token){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<UserInfo>> call=request.getUserInfo(token);
        call.enqueue(new Callback<ResponseModel<UserInfo>>(){
            @Override
            public void onResponse(Call<ResponseModel<UserInfo>> call,Response<ResponseModel<UserInfo>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        UserInfo userInfo=response.body().getData();
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"username",userInfo.getUserNickname());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"userAccount",String.valueOf(userInfo.getUserAccount()));
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"sex",userInfo.getSex());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"avatar",userInfo.getUserPic());
                        SharedPreferencesUtil.setStoredMessage(MyApplication.getContext(),"profile",userInfo.getProfile());

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

//TODO 衣柜界面

    /**
     * 获取某类别衣物（衣柜页面）
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
                        if(dialog!=null){
                            dialog.dismiss();
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<SubTypeClothingBean[]>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                if(dialog!=null){
                    dialog.dismiss();
                }
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
     * 获取类别表
     *
     * @param types 要填充的类别
     */
    public static void getType(List<Type> types,ClothesDetailActivity clothesDetailActivity){
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
                        clothesDetailActivity.initSpinner();
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
    public static void postUploadClothing(String token,List<Integer> subtypeIds,List<MultipartBody.Part> uploadPic,ClosetFragment closetFragment){
        Log.e("postUploadClothing: ",subtypeIds.toString());
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postUploadClothing(token,subtypeIds,uploadPic);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"上传衣物成功！",Toast.LENGTH_SHORT).show();
                        closetFragment.init();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    Log.e("onResponse: ",response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),"FAILED",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 删除衣物
     *
     * @param deleteClothingRequest 删除衣物的id
     */
    public static void postDeleteClothing(DeleteClothingRequest deleteClothingRequest,ClosetFragment closetFragment){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postDeleteClothing(deleteClothingRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"删除衣物成功！",Toast.LENGTH_SHORT).show();
                        closetFragment.init();
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


//TODO 搭配界面

    /**
     * 获取场合表
     *
     * @param occasions 要填充的场合
     */
    public static void getOccasion(String token,List<Occasion> occasions,MyOutfitFragment myOutfitFragment){
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
                        myOutfitFragment.init();

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
     * 新建场合
     *
     * @param addOccasionRequest 要新建的场合
     */
    public static void postAddOccasion(String token,AddOccasionRequest addOccasionRequest,MyOutfitFragment myOutfitFragment){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.addOccasion(token,addOccasionRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        RetrofitUtil.getOccasion(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),myOutfitFragment.occasions,myOutfitFragment);
                        Toast.makeText(MyApplication.getContext(),"新建场合成功",Toast.LENGTH_SHORT).show();
                        myOutfitFragment.init();
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
     * 删除场合
     *
     * @param deleteOccasionRequest 要删除的场合
     */
    public static void postDeleteOccasion(String token,DeleteOccasionRequest deleteOccasionRequest,MyOutfitFragment myOutfitFragment){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.deleteOccasion(token,deleteOccasionRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        RetrofitUtil.getOccasion(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),myOutfitFragment.occasions,myOutfitFragment);
                        Toast.makeText(MyApplication.getContext(), "已删除场合", Toast.LENGTH_SHORT).show();
                        myOutfitFragment.init();
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
                        if(dialog!=null){
                            dialog.dismiss();
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Outfit[]>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
    }

    /**
     * 上传新搭配
     *
     */
    public static void postAddOutfit(String token,AddOutfitRequest addOutfitRequest){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postAddOutfit(token,addOutfitRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(),"添加搭配成功",Toast.LENGTH_SHORT).show();
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
     * 删除搭配
     *
     * @param deleteOutfitRequest 要删除的搭配id
     */
    public static void postDeleteOutfit(String token,DeleteOutfitRequest deleteOutfitRequest,MyOutfitFragment myOutfitFragment){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postDeleteOutfit(token,deleteOutfitRequest);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Toast.makeText(MyApplication.getContext(), "已删除搭配", Toast.LENGTH_SHORT).show();
                        myOutfitFragment.init();
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
     * 获取推荐搭配
     *
     */
    public static void postGetRecommendOutfit(String token,GetRecommendOutfitRequest getRecommendOutfitRequest,List<RecommendClothes> recommendClothes,RecommendOutfitFragment recommendOutfitFragment){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<List<RecommendClothes>>> call=request.postGetRecommendOutfit(token,getRecommendOutfitRequest);
        call.enqueue(new Callback<ResponseModel<List<RecommendClothes>>>(){
            @Override
            public void onResponse(Call<ResponseModel<List<RecommendClothes>>> call,Response<ResponseModel<List<RecommendClothes>>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        recommendClothes.clear();
                        for(RecommendClothes i : response.body().getData()){
                            recommendClothes.add(i);
                        }
                        recommendOutfitFragment.notifyDataSetChanged();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<List<RecommendClothes>>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 获取某类别衣物(选择界面)
     *
     * @param getClothingRequest 获取某类别衣物请求体
     * @param subTypeClothingBeans 要填充的子类别数组
     */
    public static void postGetClothing(String token,GetClothingRequest getClothingRequest,List<SubTypeClothingBean> subTypeClothingBeans,ChooseClothesFragment chooseClothesFragment,LoadingDialog dialog){
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
                        chooseClothesFragment.adapter.notifyDataSetChanged();
                        if(dialog!=null){
                            dialog.dismiss();
                        }
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<SubTypeClothingBean[]>> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),FAILED,Toast.LENGTH_SHORT).show();
                if(dialog!=null){
                    dialog.dismiss();
                }
            }
        });
    }

//TODO 我的界面

    /**
     * 修改用户信息
     * //TODO记得还要把注释改掉，该加的加上
     * @param token token
     */
    public static void modifyUserInfo(String token,ModifyUserInfoRequest modifyUserInfoRequest){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.modifyUserInfo(token,modifyUserInfoRequest);
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
    public static void getBlog(String token, GetBlogRequest getBlogRequest, List<Blog> blogList,LoadingDialog dialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<Blog[]>> call=request.getBlog(token,getBlogRequest);
        call.enqueue(new Callback<ResponseModel<Blog[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<Blog[]>> call, Response<ResponseModel<Blog[]>> response) {
                if(response.body() != null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        for(Blog i : response.body().getData()){
                            blogList.add(i);
                        }
                        MyBlogFragment.blogAdapter.notifyDataSetChanged();
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
    public static void getCollection(String token,GetBlogRequest getBlogRequest, List<Collection> collectionList, LoadingDialog dialog){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel<Collection[]>> call=request.getCollection(token,getBlogRequest);
        call.enqueue(new Callback<ResponseModel<Collection[]>>() {
            @Override
            public void onResponse(Call<ResponseModel<Collection[]>> call, Response<ResponseModel<Collection[]>> response) {
                if(response.body()!=null){
                    if(response.body().getCode() == SUCCESS_CODE) {
                        for(Collection i : response.body().getData()){
                            collectionList.add(i);
                        }
                        MyCollectionFragment.collectionAdapter.notifyDataSetChanged();
                    }else{
                        Toast.makeText(MyApplication.getContext(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<Collection[]>> call, Throwable t) {
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
    public  static void getSubscriber(String token, GetBlogRequest getBlogRequest, List<UserInfo> userInfoList){
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
                        ShowFansListActivity.userInfoAdapter.notifyDataSetChanged();
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

    /**
     * 发布博客
     *
     * @param token token
     */
    public static void postBlog(String token, String blogArticle, String blogTitle, List<MultipartBody.Part> uploadPic){
        final PostInterfaces request=retrofit.create(PostInterfaces.class);
        Call<ResponseModel> call=request.postBlog(token, blogArticle, blogTitle, uploadPic);
        call.enqueue(new Callback<ResponseModel>(){
            @Override
            public void onResponse(Call<ResponseModel> call,Response<ResponseModel> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
     //                   Toast.makeText(MyApplication.getContext(),"上传衣物成功！",Toast.LENGTH_SHORT).show();
     //                   closetFragment.init();
                    }else{
                        Toast.makeText(MyApplication.getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                    Log.e("onResponse: ",response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call,Throwable t){
                Toast.makeText(MyApplication.getContext(),"FAILED",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
