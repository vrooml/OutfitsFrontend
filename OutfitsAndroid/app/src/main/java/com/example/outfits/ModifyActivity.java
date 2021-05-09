package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.outfits.User.UserFragment;

public class ModifyActivity extends BaseActivity {
    private Button button;
    private ImageButton btn_return;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_personalinformation);
        btn_return=(ImageButton) findViewById(R.id.imageButton);
        button=(Button) findViewById(R.id.btn_modify);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ModifyActivity.this,UserFragment.class);
                startActivityForResult(intent1,1);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(ModifyActivity.this,UserFragment.class);
                startActivityForResult(intent2,1);
            }
        });
    }
}
