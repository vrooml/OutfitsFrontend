package com.example.outfits;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;


public class WelcomeActivity extends BaseActivity{

    private static final String TAG="debug";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        requestReadExternalPermission();
        checkLocationPermission();
    }

    private void init(){
        //判断之前是否登录过决定下一页面
        if(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")==null||
                SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token").equals("false")){
            new Thread(new Runnable(){
                @Override
                public void run(){
                    try{
                        Thread.sleep(2000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            WelcomeActivity.this.finish();
                        }
                    });
                }
            }).start();


        }else{
            new Thread(new Runnable(){
                @Override
                public void run(){
                    try{
                        Thread.sleep(2000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run(){
                            RetrofitUtil.getUserInfoAndStore(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"));
                            Log.d(TAG,"token"+SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"));
                            Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            WelcomeActivity.this.finish();
                        }
                    });
                }
            }).start();

        }
    }


    @SuppressLint("NewApi")
    private void requestReadExternalPermission(){
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MyApplication.getContext(),"未获得储存权限,请手动开启并重启应用",Toast.LENGTH_SHORT).show();
            if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                Log.d(TAG,"11111111111111");
            }else{
                // 0 是自己定义的请求code
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                Log.d(TAG,"222222222222");
            }
        }else{
            init();
        }
    }

    private void checkLocationPermission(){

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                !=PackageManager.PERMISSION_GRANTED){//未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},200);
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        switch(requestCode){
            case 0:{
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    init();
                    // permission was granted
                    // request successfully, handle you transactions
                }else{
                    Toast.makeText(this,"未开启权限,请手动到设置页面去开启权限",Toast.LENGTH_LONG).show();
//                    System.exit(0);
                    // permission denied
                    // request failed
                }
                return;
            }
            case 200:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){//用户同意权限,执行我们的操作

                }else{//用户拒绝之后,当然我们也可以弹出一个窗口,直接跳转到系统设置页面
                    Toast.makeText(this,"未开启定位权限,请手动到设置页面去开启权限",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }
}
