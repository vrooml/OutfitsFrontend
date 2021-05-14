package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.outfits.RetrofitStuff.LoginRequest;
import com.example.outfits.Utils.RetrofitUtil;

public class LoginActivity extends BaseActivity{
    private EditText phoneNumEdit;
    private EditText passwordEdit;
    private Button loginButton;
    private TextView register;
    private TextView forget;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton=(Button)findViewById(R.id.button2);
        register=(TextView)findViewById(R.id.button3);
        forget=(TextView)findViewById(R.id.button5);
        phoneNumEdit=(EditText)findViewById(R.id.editTextTextPersonName2);
        passwordEdit=(EditText)findViewById(R.id.editTextTextPassword2);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRequest loginRequest=new LoginRequest(phoneNumEdit.getText().toString(),passwordEdit.getText().toString());
                RetrofitUtil.postLogin(loginRequest,LoginActivity.this);//发送登录请求
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent2);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent3);
            }
        });
    }
}