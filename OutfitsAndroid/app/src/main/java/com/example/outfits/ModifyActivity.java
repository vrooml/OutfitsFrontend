package com.example.outfits;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.outfits.UI.UserFragment;

public class ModifyActivity extends BaseActivity {
    private Button button;
    private ImageButton btn_return;
    private ImageView boy;
    private ImageView girl;
    String sex=null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_personalinformation);
        btn_return=(ImageButton) findViewById(R.id.imageButton);
        button=(Button) findViewById(R.id.btn_modify);
        boy=findViewById(R.id.imageView6);
        girl=findViewById(R.id.imageView5);
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(ModifyActivity.this,UserFragment.class);
                startActivity(intent1);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(ModifyActivity.this,UserFragment.class);
                startActivity(intent2);
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
}
