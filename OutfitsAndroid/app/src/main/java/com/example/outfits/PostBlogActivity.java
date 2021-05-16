package com.example.outfits;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.outfits.Utils.MyGlideEngine;
import com.example.outfits.Utils.RetrofitUtil;
import com.example.outfits.Utils.SharedPreferencesUtil;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PostBlogActivity extends AppCompatActivity{
    private Button goback;
    public static EditText content;
    private ImageView icon;
    public static EditText title;
    private TextView post;
    public static List<Uri> list=new ArrayList<>();
    private static final int REQUEST_CODE_CHOOSE=23;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_blog);
        goback=findViewById(R.id.btn_back);
        content=findViewById(R.id.et_content);
        icon=findViewById(R.id.choose);
        title=findViewById(R.id.et_title);
        post=findViewById(R.id.tv_post);
        goback.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                finish();
            }
        });
        icon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Matisse
                        .from(PostBlogActivity.this)
                        //选择视频和图片
                        .choose(MimeType.ofAll())
                        //这两行要连用 是否在选择图片中展示照相 和适配安卓7.0 FileProvider
                        .capture(true)
                        .captureStrategy(new CaptureStrategy(true,"com.example.myapplication.fileProvider"))
                        //有序选择图片 123456...
                        .countable(true)
                        //最大选择数量为1
                        .maxSelectable(1)
                        //选择方向
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                        //界面中缩略图的质量
                        .thumbnailScale(0.8f)
                        .theme(R.style.Matisse_Zhihu)
                        //Glide加载方式
                        .imageEngine(new MyGlideEngine())
                        //请求码
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
        post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String titleForBlog=title.getText().toString();
                String contentForBlog=content.getText().toString();
                RetrofitUtil.postBlog(SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token"),
                        contentForBlog,titleForBlog,getImg(list.get(0)),PostBlogActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==REQUEST_CODE_CHOOSE&&resultCode==RESULT_OK){
            list=Matisse.obtainResult(data);
            List<String> path=Matisse.obtainPathResult(data);
            icon.setImageURI(list.get(0));
        }
    }

    private MultipartBody.Part getImg(Uri origin){
        //将contentUri转化为真实路径Uri
        String res=null;
        String[] proj={MediaStore.Images.Media.DATA};
        Cursor cursor=this.getContentResolver().query(origin,proj,null,null,null);
        if(cursor.moveToFirst()){
            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res=cursor.getString(column_index);
        }
        cursor.close();
        //将路径uri转化为file
        File file=new File(res);
        //将路径file转化为RequestBody
        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        //将RequestBody转化为MultipartBody.Part
        MultipartBody.Part finalRequest=MultipartBody.Part.createFormData("blogPic",file.getName(),requestBody);//后端的key
        return finalRequest;
    }
}