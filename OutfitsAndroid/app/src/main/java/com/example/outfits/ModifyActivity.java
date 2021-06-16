package com.example.outfits;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.outfits.Bean.UserInfo;
import com.example.outfits.RetrofitStuff.GetBlogRequest;
import com.example.outfits.RetrofitStuff.ModifyUserInfoRequest;
import com.example.outfits.RetrofitStuff.PostInterfaces;
import com.example.outfits.RetrofitStuff.ResponseModel;
import com.example.outfits.UI.UserFragment;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.outfits.MyApplication.getContext;
import static com.example.outfits.Utils.ConstantUtil.FAILED;
import static com.example.outfits.Utils.ConstantUtil.SUCCESS_CODE;

public class ModifyActivity extends BaseActivity{
    private Button confirmbutton;
    private ImageView goBackButton;
    private ImageView icon;
    private ImageView boy;
    private ImageView girl;
    private EditText nicknameEdit;
    private EditText profileEdit;

    String sex=null;
    String nickname=null;
    String profile=null;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_personalinformation);
        goBackButton=findViewById(R.id.goback);
        confirmbutton=findViewById(R.id.modify_button);
        boy=findViewById(R.id.register_boy2);
        girl=findViewById(R.id.register_girl2);
        nicknameEdit=findViewById(R.id.editTextNickName);
        profileEdit=findViewById(R.id.editTextProfile);
        icon=findViewById(R.id.user_image);
        sex=SharedPreferencesUtil.getStoredMessage(getContext(),"sex");
        nickname=SharedPreferencesUtil.getStoredMessage(getContext(),"username");
        profile=SharedPreferencesUtil.getStoredMessage(getContext(),"profile");
        if(nickname!=null){
            nicknameEdit.setText(nickname);
        }
        if(profile!=null){
            profileEdit.setText(profile);
        }
        if(sex!=null){
            if(sex.equals("男")){
                boy.setImageResource(R.drawable.boy);
                girl.setImageResource(R.drawable.girl_unselected);
            }else if(sex.equals("女")){
                boy.setImageResource(R.drawable.boy_unselected);
                girl.setImageResource(R.drawable.girl);
            }
        }

        PostInterfaces request=RetrofitUtil.retrofit.create(PostInterfaces.class);
        //获取用户头像和昵称
        Call<ResponseModel<UserInfo>> call1=request.getInfo(SharedPreferencesUtil.getStoredMessage(getContext(),"token"),
                new GetBlogRequest(
                        Integer.parseInt(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"userId"))
//                        6
                ));
        call1.enqueue(new Callback<ResponseModel<UserInfo>>(){
            @Override
            public void onResponse(Call<ResponseModel<UserInfo>> call,Response<ResponseModel<UserInfo>> response){
                if(response.body()!=null){
                    if(response.body().getCode()==SUCCESS_CODE){
                        Glide.with(getContext()).asBitmap()
                                .load(response.body().getData().getUserPic())
                                .centerCrop().into(new BitmapImageViewTarget(icon){
                            @Override
                            protected void setResource(Bitmap resource){
                                RoundedBitmapDrawable circularBitmapDrawable=RoundedBitmapDrawableFactory
                                        .create(getContext().getResources(),resource);
                                circularBitmapDrawable.setCircular(true);
                                icon.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                    }else{
                        Toast.makeText(getContext(),response.body().getMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseModel<UserInfo>> call,Throwable t){
                Toast.makeText(getContext(),FAILED,Toast.LENGTH_SHORT).show();
            }
        });


        goBackButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        confirmbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ModifyUserInfoRequest modifyUserInfoRequest=new ModifyUserInfoRequest(nicknameEdit.getText().toString(),sex,profileEdit.getText().toString());
                RetrofitUtil.modifyUserInfo(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),modifyUserInfoRequest);
                finish();
            }
        });
        boy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boy.setImageResource(R.drawable.boy);
                girl.setImageResource(R.drawable.girl_unselected);
                sex="男";
            }
        });
        girl.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                boy.setImageResource(R.drawable.boy_unselected);
                girl.setImageResource(R.drawable.girl);
                sex="女";
            }
        });
    }
}