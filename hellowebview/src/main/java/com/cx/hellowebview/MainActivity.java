package com.cx.hellowebview;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {

    private FrameLayout videoview;
    private Button videolandport;
    private WebView videowebview;
    private Boolean islandport = true;
    private View xCustomView;
    private xWebChromeClient xwebchromeclient;
    private String url = "http://www.iqiyi.com/";
    private WebChromeClient.CustomViewCallback 	xCustomViewCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initwidget();
        initListener();
        videowebview.loadUrl(url);
    }

    private void initListener() {
        // TODO Auto-generated method stub
        videolandport.setOnClickListener(new Listener());
    }

    private void initwidget() {
        // TODO Auto-generated method stub
        videoview = (FrameLayout) findViewById(R.id.video_view);
        videolandport = (Button) findViewById(R.id.video_landport);
        videowebview = (WebView) findViewById(R.id.video_webview);
        WebSettings ws = videowebview.getSettings();
        ws.setBuiltInZoomControls(true);
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        ws.setUseWideViewPort(true);
        ws.setLoadWithOverviewMode(true);
        ws.setSavePassword(true);
        ws.setSaveFormData(true);
        ws.setJavaScriptEnabled(true);
        ws.setGeolocationEnabled(true);
        ws.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");
        ws.setDomStorageEnabled(true);
        xwebchromeclient = new xWebChromeClient();
        videowebview.setWebChromeClient(xwebchromeclient);
        videowebview.setWebViewClient(new xWebViewClientent());
    }

    class Listener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {
                case R.id.video_landport:
                    if (islandport) {
                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        videolandport.setText("横屏");
                    }else {

                        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        videolandport.setText("竖屏");
                    }
                    break;

                default:
                    break;
            }
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (inCustomView()) {
                hideCustomView();
                return true;
            }else {

                videowebview.loadUrl("about:blank");
//		       		 mTestWebView.loadData("", "text/html; charset=UTF-8", null);
                MainActivity.this.finish();
                Log.i("testwebview", "===>>>2");
            }
        }
        return true;
    }
    public boolean inCustomView() {
        return (xCustomView != null);
    }
    public void hideCustomView() {
        xwebchromeclient.onHideCustomView();
    }
    public class xWebChromeClient extends WebChromeClient {
        private Bitmap xdefaltvideo;
        private View xprogressvideo;
        @Override
        public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback)
        {
            if (islandport) {

            }
            else{

//				ii = "1";
//				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            videowebview.setVisibility(View.GONE);
            if (xCustomView != null) {
                callback.onCustomViewHidden();
                return;
            }

            videoview.addView(view);
            xCustomView = view;
            xCustomViewCallback = callback;
            videoview.setVisibility(View.VISIBLE);
        }

        @Override
        public void onHideCustomView() {

            if (xCustomView == null)
                return;

            // Hide the custom view.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            xCustomView.setVisibility(View.GONE);

            // Remove the custom view from its container.
            videoview.removeView(xCustomView);
            xCustomView = null;
            videoview.setVisibility(View.GONE);
            xCustomViewCallback.onCustomViewHidden();

            videowebview.setVisibility(View.VISIBLE);

            //Log.i(LOGTAG, "set it to webVew");
        }
        @Override
        public Bitmap getDefaultVideoPoster() {
            //Log.i(LOGTAG, "here in on getDefaultVideoPoster");
            if (xdefaltvideo == null) {
                xdefaltvideo = BitmapFactory.decodeResource(
                        getResources(), R.drawable.videoicon);
            }
            return xdefaltvideo;
        }
        @Override
        public View getVideoLoadingProgressView() {
            //Log.i(LOGTAG, "here in on getVideoLoadingPregressView");

            if (xprogressvideo == null) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                xprogressvideo = inflater.inflate(R.layout.video_loading_progress, null);
            }
            return xprogressvideo;
        }
        @Override
        public void onReceivedTitle(WebView view, String title) {
            (MainActivity.this).setTitle(title);
        }

//         @Override
//         public void onProgressChanged(WebView view, int newProgress) {
//        	 (MainActivity.this).getWindow().setFeatureInt(Window.FEATURE_PROGRESS, newProgress*100);
//         }


    }

    public class xWebViewClientent extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.i("cx", "shouldOverrideUrlLoading: "+url);
            videowebview.loadUrl(url);
            return false;
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        Log.i("cx", "=====<<<  onConfigurationChanged  >>>=====");
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            islandport = false;
        }else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            islandport = true;
        }
    }
}
