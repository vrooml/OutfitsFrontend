package com.example.outfits;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.outfits.RetrofitStuff.AuthCodeRequest;
import com.example.outfits.RetrofitStuff.RegisterRequest;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends BaseActivity{
    private ImageView boy;
    private ImageView girl;
    private EditText registerPhonenumEdit;
    private EditText registerNicknameEdit;
    private EditText registerCodeEdit;
    private Button getCodeButton;
    private EditText registerPasswordEdit;
    private EditText registerConfirmPasswordEdit;
    private Button registerButton;
    String sex=null;
    private int i=60;
    public static final String regularStr=
            "^(?=.*[a-zA-Z0-9].*)(?=.*[a-zA-Z\\W].*)(?=.*[0-9\\W].*).{6,20}$";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerPhonenumEdit=findViewById(R.id.editTextPersonName);
        registerNicknameEdit=findViewById(R.id.editTextNickName);
        registerCodeEdit=findViewById(R.id.register_inputcode);
        getCodeButton=findViewById(R.id.register_getcode);
        registerPasswordEdit=findViewById(R.id.register_inputpassword);
        registerConfirmPasswordEdit=findViewById(R.id.register_inputpassword_verify);
        registerButton=findViewById(R.id.register_button);
        boy=findViewById(R.id.register_boy);
        girl=findViewById(R.id.register_girl);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        getCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsgCode(registerPhonenumEdit.getText().toString());
            }
        });
        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boy.setImageResource(R.drawable.boy);
                girl.setImageResource(R.drawable.girl_unselected);
                sex="男";
            }
        });
        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boy.setImageResource(R.drawable.boy_unselected);
                girl.setImageResource(R.drawable.girl);
                sex="女";
            }
        });
    }

    private void register(){
        String password = registerPasswordEdit.getText().toString();
        String passwordVerify = registerConfirmPasswordEdit.getText().toString();
        String smsCode=registerCodeEdit.getText().toString();
        String phone =registerPhonenumEdit.getText().toString();
        Pattern pattern=Pattern.compile(regularStr);
        if(sex==null){
            Toast.makeText(this,"请选择性别",Toast.LENGTH_SHORT).show();
        }else if("".equals(phone))
            Toast.makeText(this,"请输入手机号",Toast.LENGTH_SHORT).show();
        else if("".equals(smsCode)){
            Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
        }
        else if(smsCode.length()!=6)
            Toast.makeText(getApplicationContext(),"验证码长度为6位",Toast.LENGTH_SHORT).show();
        else if (!password.equals(passwordVerify)){
            //两次密码不一致，发送验证，并传递密码和账号
            Toast.makeText(this,"两次密码不一致请重新输入",Toast.LENGTH_SHORT).show();
            registerConfirmPasswordEdit.setText("");
        }
        else if(!pattern.matcher(password).matches()){
            Toast.makeText(this,"密码强度过低,长度在6到20位之间\n" +
                    "数字、字母、特殊字符中两种以上的任意搭配",Toast.LENGTH_SHORT).show();
            registerConfirmPasswordEdit.setText("");
            registerPasswordEdit.setText("");
        }else {
            RegisterRequest registerRequest=new RegisterRequest(registerPhonenumEdit.getText().toString()
                    ,registerCodeEdit.getText().toString()
                    , SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")
                    ,registerPasswordEdit.getText().toString()
                    ,registerNicknameEdit.getText().toString()
                    ,sex);
            RetrofitUtil.postRegister(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"smsCodeToken"),registerRequest,this);
        }
    }

    private void sendMsgCode(String phone){
        String codeMsg="注册";
        AuthCodeRequest authCodeRequest=new AuthCodeRequest(phone,codeMsg);
        RetrofitUtil.postAuthCode(authCodeRequest);
        getCodeButton.setClickable(false);
        //开始倒计时
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    handler1.sendEmptyMessage(-1);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                handler1.sendEmptyMessage(-2);
            }
        });
        thread.start();
    }

    @SuppressLint("HandlerLeak")
    Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {//验证码发送相关Handler
            //Log.i(ACTIVITY_TAG,"发送返回码:"+msg.what);
            switch (msg.what){
                case -1:
                    getCodeButton.setText(i + " s");
                    break;
                case -2:
                    getCodeButton.setText("重新发送");
                    getCodeButton.setClickable(true);
                    i = 60;
                    break;
            }

        }
    };
}

