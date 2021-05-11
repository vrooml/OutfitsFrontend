package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.outfits.RetrofitStuff.LoginRequest;
import com.example.outfits.User.UserFragment;
import com.example.outfits.Utils.RetrofitUtil;

public class LoginActivity extends BaseActivity{
    private EditText phoneNumEdit;
    private EditText passwordEdit;
    private Button button2;
    private Button button3;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        phoneNumEdit=(EditText)findViewById(R.id.editTextTextPersonName2);
        passwordEdit=(EditText)findViewById(R.id.editTextTextPassword2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest loginRequest=new LoginRequest(phoneNumEdit.getText().toString(),passwordEdit.getText().toString());
//                RetrofitUtil.postLogin("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNjIwNTc1MzMwLCJzdWIiOiI2MzQwOTgiLCJpc3MiOiJydWlqaW4iLCJleHAiOjE2MjA1NzU5MzB9.qHY8OxshpoN18NPisKmDj2ZALJETqyP5I-xgPXoHcA0",loginRequest);//发送登录请求
                Intent intent1=new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent1);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent2);
            }
        });
    }
}
