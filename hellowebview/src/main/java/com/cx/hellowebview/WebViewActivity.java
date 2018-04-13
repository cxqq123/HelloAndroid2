package com.cx.hellowebview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;

public class WebViewActivity extends AppCompatActivity {

    private RelativeLayout rlTop;
    private EditText etUrl;
    private LinearLayout llWebView;
    private Button btnBack;
    private Button btnForward;
    private Button btnHome;

    private WebView webView;
    private Context mContext;

    private String currentUrl = "http://www.youku.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT){
            //若是4.4以上的手机
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }else if(Build.VERSION.SDK_INT <Build.VERSION_CODES.KITKAT){
            //4.4以下的手机
//            ToastUtils.makeTextShort(mContext,"你的android 版本低于4.4太低，建议你升级android系统!!!");
        }
        setContentView(R.layout.activity_web_view);
        mContext = WebViewActivity.this;
        initView();
        bindData();
    }

    public void initView(){
        rlTop = (RelativeLayout) findViewById(R.id.rl_top);
        etUrl = (EditText) findViewById(R.id.et_url);
        llWebView = (LinearLayout) findViewById(R.id.ll_webView);
        btnBack = (Button) findViewById(R.id.btn_back);
        btnForward = (Button) findViewById(R.id.btn_forward);
        btnHome = (Button) findViewById(R.id.btn_home);

    }

    public void bindData(){
        webView = new WebView(mContext);
        initWebView();
    }

    public void initWebView(){
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        webView.setLayoutParams(lp);
        llWebView.addView(webView);
        final WebSettings webSetting = webView.getSettings();
        //设置支持js
        webSetting.setJavaScriptEnabled(true);
        //设置不保存密码，防止弹出保存密码对话框
        webSetting.setSavePassword(false);
        //设置不保存表单
        webSetting.setSaveFormData(false);
        //设置可以支持缩放
        webSetting.setSupportZoom(true);
        //启用数据库
        webSetting.setDatabaseEnabled(true);
        File fAppCache = new File(mContext.getCacheDir().getAbsolutePath(),"webview_cache");
        webSetting.setAppCachePath(fAppCache.getAbsolutePath());

        String dir = mContext.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();
        //启用地理定位
        webSetting.setGeolocationEnabled(true);
        //设置定位的数据库路径
        webSetting.setGeolocationDatabasePath(dir);
        //设置dom
        webSetting.setDomStorageEnabled(true);
        //设置是否可以缩放
        webSetting.setBuiltInZoomControls(true);
        webSetting.setAllowContentAccess(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setPluginState(WebSettings.PluginState.ON);
        if(Build.VERSION.SDK_INT > 14){
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                    WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        }
        if(Build.VERSION.SDK_INT > 15){

            webSetting.setAllowFileAccessFromFileURLs(true);
            webSetting.setAllowUniversalAccessFromFileURLs(true);
        }
        // 设置出现缩放工具
        if(Build.VERSION.SDK_INT > 10){
            webSetting.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //扩大比例的缩放
        webSetting.setUseWideViewPort(true);
//        //自适应屏幕
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setLoadWithOverviewMode(true);
        //不显示水平滚动条
        webView.setHorizontalScrollBarEnabled(false);

        webView.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("cc" , "onPageStarted url : " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("cc" , "onPageFinished : " + url);

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                Log.e("cc" , "shouldOverrideUrlLoading url : " + url);
                return super.shouldOverrideUrlLoading(view, request);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                Log.e("cc" , "shouldInterceptRequest url : " + url);
                return super.shouldInterceptRequest(view, request);
            }
        });

        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.e("cc" , "shouldInterceptRequest url : " + view.getUrl());
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onPermissionRequest(PermissionRequest request) {
                super.onPermissionRequest(request);
            }
        });

        loadUrl();

    }

    public void loadUrl(){
        if(webView != null){
            webView.loadUrl(currentUrl);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(webView != null){
            webView.loadUrl("");
            llWebView.removeAllViews();
            webView.getSettings().setJavaScriptEnabled(false);
            webView.stopLoading();
            webView.destroy();
            webView = null;
        }
    }
}
