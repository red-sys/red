package com.haha.baidumap;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.baidu.mapapi.model.LatLng;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PoiDetailsActivity extends BaseActivity implements View.OnClickListener{
    private ImageView iv_back;
    private String name;
    private LatLng latLng;
    private WebView webView;
    @Override
    protected void initView() {
        super.initView();
        webView = findViewById(R.id.webview);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        name = getIntent().getStringExtra("name");
        latLng = new LatLng(getIntent().getDoubleExtra("latitude",0),getIntent().getDoubleExtra("longitude",0));
        webView.setWebViewClient(new WebViewClient());//添加WebViewClient实例
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl(getIntent().getStringExtra("url"));//添加浏览器地址
        //重回下面俩个方法，就会在App中打开页面，而不是跳转到浏览器
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http://")||url.startsWith("https://")){
                    webView.loadUrl(url);
                    return true;
                }else {
                    try{
                        if(url.startsWith("baidumap://")||url.startsWith("bdapi://")||url.startsWith("tel://")){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                            startActivity(intent);
                            return true;
                        }
                    }catch (Exception e){
                        return false;
                    }
                    webView.loadUrl(url);
                    return true;
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_poidetails;
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
