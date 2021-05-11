package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.outfits.RetrofitStuff.AuthCodeRequest;
import com.example.outfits.RetrofitStuff.RegisterRequest;
import com.example.outfits.User.UserFragment;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

public class RegisterActivity extends BaseActivity{
    private EditText registerPhonenumEdit;
    private EditText registerCodeEdit;
    private EditText registerPasswordEdit;
    private EditText registerNicknameEdit;
    private ImageView imageView2;
    private ImageView imageView3;
    private Button button;
    private Button button4;
    String sex=null;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        registerPhonenumEdit=(EditText)findViewById(R.id.editTextTextPersonName);
        registerCodeEdit=(EditText)findViewById(R.id.editTextTextPassword);
        registerPasswordEdit=(EditText)findViewById(R.id.editTextTextPassword3);
        registerNicknameEdit=(EditText)findViewById(R.id.editTextTextMultiLine3);
        button=(Button)findViewById(R.id.button);
        button4=(Button)findViewById(R.id.button4);
        imageView2=(ImageView)findViewById(R.id.imageView2);
        imageView3=(ImageView)findViewById(R.id.imageView3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeMsg="注册";
                AuthCodeRequest authCodeRequest=new AuthCodeRequest(registerPhonenumEdit.getText().toString(),codeMsg);
                RetrofitUtil.postAuthCode(authCodeRequest);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterRequest registerRequest=new RegisterRequest(registerPhonenumEdit.getText().toString(),registerCodeEdit.getText().toString()
                        , SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"authCodeToken"),registerPasswordEdit.getText().toString(),registerNicknameEdit.getText().toString(),sex);
                RetrofitUtil.postRegister("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNjIwNTc1MzMwLCJzdWIiOiI2MzQwOTgiLCJpc3MiOiJydWlqaW4iLCJleHAiOjE2MjA1NzU5MzB9.qHY8OxshpoN18NPisKmDj2ZALJETqyP5I-xgPXoHcA0",registerRequest);
                Intent intent1=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView2.setImageResource(R.drawable.boy_unselected);
                sex="男";
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView3.setImageResource(R.drawable.girl_unselected);
                sex="女";
            }
        });
    }
}

