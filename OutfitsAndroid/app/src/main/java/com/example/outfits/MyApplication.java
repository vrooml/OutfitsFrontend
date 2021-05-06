package com.example.outfits;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

//便于获取全局context
public class MyApplication extends Application{
    private static Context context;

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
        LitePal.initialize(this);
    }

    public static Context getContext(){
        return context;
    }
}
