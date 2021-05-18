package com.example.outfits.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.Utils.SharedPreferencesUtil;

public class BlogDetailActivity extends AppCompatActivity{
    WebView webView;
    int blogId;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);
        webView=findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        blogId=getIntent().getIntExtra("blogId",0);
        webView.addJavascriptInterface(new JsInterface(MyApplication.getContext(),webView), "android");
        //声明WebSettings子类

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可


//        //设置自适应屏幕，两者合用
//        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//
//        //缩放操作
//        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
//        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
//        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
//
//        //其他细节操作
//        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
//        webSettings.setAllowFileAccess(true); //设置可以访问文件
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
//        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
//        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webView.loadUrl("http://121.5.100.116/index.html#/blogContent?blogId="+blogId);
    }

    public class JsInterface extends Object{
        private WebView mWebView;
        private Context mContext;

        public JsInterface(Context context,WebView webView) {
            this.mWebView = webView;
            this.mContext=context;
        }

        //该方法将会被js 调用
        @JavascriptInterface
        public String getToken(String jsCallJava) {
            Log.e("chat","getToken: ");
            return SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token");
        }

        //该方法将会被js 调用
        @JavascriptInterface
        public String getBlogId(String jsCallJava) {
            Log.e("chat","getToken: ");
            return SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token");
        }
    }
}