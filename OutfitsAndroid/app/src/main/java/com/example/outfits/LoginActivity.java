package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.outfits.User.UserFragment;

public class LoginActivity extends BaseActivity{
    private Button button2;
    private Button button3;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        button2=(Button)findViewById(R.id.button2);
        button3=(Button)findViewById(R.id.button3);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(LoginActivity.this, UserFragment.class);
                startActivityForResult(intent1,1);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent2,1);
            }
        });
    }
}
