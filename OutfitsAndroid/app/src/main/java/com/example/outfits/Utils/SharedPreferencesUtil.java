package com.example.outfits.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesUtil{

    /*
        authToken:验证码token
        username:用户名
        userId:用户id
        gender:性别
        phoneNum:手机号
        token:用户令牌
     */


    static SharedPreferences sp;

    //获取SharedPreferences存储内容
    public static String getStoredMessage(Context context,String key){
        sp=context.getSharedPreferences("Outfits",Context.MODE_PRIVATE);
        return sp.getString(key,null);
    }

    //存储信息
    public static void setStoredMessage(Context context,String key,String value){
        sp=context.getSharedPreferences("Outfits",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString(key,value);
        editor.commit();
        //editor是同步的，有返回成功或者失败，这里使用同步比较稳妥
    }
}
