package com.example.outfits.UI;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.outfits.MyApplication;
import com.example.outfits.R;
import com.example.outfits.Utils.SharedPreferencesUtil;

public class ChatFragment extends Fragment{
    WebView webView;

    public ChatFragment(){
        // Required empty public constructor
    }


    public static ChatFragment newInstance(){
        ChatFragment fragment=new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.fragment_chat,container,false);
        webView=view.findViewById(R.id.webview);
        webView.loadUrl("http://121.5.100.116/index.html#/index");
        webView.loadUrl("javascript:getToken("+SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token")+")");
        webView.addJavascriptInterface(new JsInterface(getActivity(),webView), "android");
        //声明WebSettings子类
        WebSettings webSettings = webView.getSettings();

        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
        // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可


        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        //缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        //其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式

        return view;
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
            return SharedPreferencesUtil.getStoredMessage(MyApplication.getContext(),"token");
        }
    }

}