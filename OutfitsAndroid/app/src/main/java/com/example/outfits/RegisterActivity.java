package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.outfits.User.UserFragment;

public class RegisterActivity extends BaseActivity{
    private Button button4;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        button4=(Button)findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivityForResult(intent1,1);
            }
        });
    }
}
