package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.outfits.RetrofitStuff.AuthCodeRequest;
import com.example.outfits.RetrofitStuff.ForgetpasswordRequest;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

public class ForgerpasswordActivity extends BaseActivity{
    private EditText registerPhonenumEdit;
    private EditText registerCodeEdit;
    private EditText registerPasswordEdit;
    private Button button6;
    private Button button9;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword);
        registerPhonenumEdit=(EditText)findViewById(R.id.editTextTextPersonName4);
        registerCodeEdit=(EditText)findViewById(R.id.editTextTextPersonName5);
        registerPasswordEdit=(EditText)findViewById(R.id.editTextTextMultiLine4);
        button6=(Button)findViewById(R.id.button6);
        button9=(Button)findViewById(R.id.button9);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codeMsg="修改密码";
                AuthCodeRequest authCodeRequest=new AuthCodeRequest(registerPhonenumEdit.getText().toString(),codeMsg);
                RetrofitUtil.postAuthCode(authCodeRequest);
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetpasswordRequest forgetpasswordRequest=new ForgetpasswordRequest(registerPhonenumEdit.getText().toString(),registerCodeEdit.getText().toString()
                        , SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"authCodeToken"),registerPasswordEdit.getText().toString());
                RetrofitUtil.postForgetPassword("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNjIwNTc1MzMwLCJzdWIiOiI2MzQwOTgiLCJpc3MiOiJydWlqaW4iLCJleHAiOjE2MjA1NzU5MzB9.qHY8OxshpoN18NPisKmDj2ZALJETqyP5I-xgPXoHcA0",forgetpasswordRequest);
                Intent intent1=new Intent(ForgerpasswordActivity.this,LoginActivity.class);
                startActivity(intent1);
            }
        });
    }
}
