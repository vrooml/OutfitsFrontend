package com.example.outfits;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.kongzue.dialog.v2.DialogSettings;

import static com.kongzue.dialog.v2.DialogSettings.STYLE_MATERIAL;


//便于获取全局context
public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
        DialogSettings.style = STYLE_MATERIAL;
        //百度地图初始化
        SDKInitializer.initialize(this);
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public static Context getContext(){
        return context;
    }
}
