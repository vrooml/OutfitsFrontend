package com.example.outfits.UI;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.R;
import com.example.outfits.Utils.MyGlideEngine;
import com.example.outfits.Utils.RetrofitUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfoActivity extends AppCompatActivity {
    private EditText nickname;
    private EditText profile;
    private Button confirm;
    private RadioGroup sex;
    private String sex1;
    private RadioButton rbMan;
    private RadioButton rbWoman;
    private ImageView icon;
    private String picUrl;
    private static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nickname = (EditText)findViewById(R.id.ed_nickname);
        profile = (EditText)findViewById(R.id.ed_profile);
        confirm = (Button)findViewById(R.id.btn_confirm);
        sex = (RadioGroup)findViewById(R.id.rg_sex);
        rbMan = (RadioButton)findViewById(R.id.rb_man);
        rbWoman = (RadioButton)findViewById(R.id.rb_woman);
        icon = (ImageView)findViewById(R.id.iv_icon);
        UserInfo userInfo=null;
        userInfo=RetrofitUtil.getUserInfo("token");
        nickname.setText(userInfo.getUserNickname().toString());
        profile.setText(userInfo.getUserProfile().toString());
        sex1 = userInfo.getUserSex().toString();
        picUrl = userInfo.getUserPic().toString();
        if(sex1.equals("男")){
            rbMan.setChecked(true);
        }else rbWoman.setChecked(true);
        Glide.with(UserInfoActivity.this).asBitmap()
                .load(picUrl)
                .centerCrop().into(new BitmapImageViewTarget(icon){
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory
                        .create(UserInfoActivity.this.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                icon.setImageDrawable(circularBitmapDrawable);
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://a440c33e-ef48-4f8d-9cba-85579f86a113.mock.pstmn.io/")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit1 = builder.build();
//                UserClient userClient1 = retrofit1.create(UserClient.class);
//                UserInfo userInfoNew = new UserInfo(
//                        nickname.getText().toString(),
//                        sex1,
//                        profile.getText().toString(),
//                        3
//                );
//                Call<UserInfoReturn> call1 =  userClient1.modifyUserInfo("token",userInfoNew);
//                call1.enqueue(new Callback<UserInfoReturn>() {
//                    @Override
//                    public void onResponse(Call<UserInfoReturn> call, Response<UserInfoReturn> response) {
//                    }
//
//                    @Override
//                    public void onFailure(Call<UserInfoReturn> call, Throwable t) {
//                    }
//                });
            }
        });
        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rb_man){
                    sex1 = "男";
                }
                else sex1 = "女";
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse
                        .from(UserInfoActivity.this)
                        //选择视频和图片
                        .choose(MimeType.ofAll())
                        //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true,"com.example.myapplication.fileProvider"))
                        //有序选择图片 123456...
                        .countable(true)
                        //最大选择数量为1
                        .maxSelectable(1)
                        //选择方向
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        //界面中缩略图的质量
                        .thumbnailScale(0.8f)
                        .theme(R.style.Matisse_Zhihu)
                        //Glide加载方式
                        .imageEngine(new MyGlideEngine())
                        //请求码
                        .forResult(REQUEST_CODE_CHOOSE);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_CHOOSE && requestCode == RESULT_OK){
            List<Uri> result = Matisse.obtainResult(data);
        }
    }
}