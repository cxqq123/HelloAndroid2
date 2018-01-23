package com.cx.helloandroid2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cx.helloandroid2.view.CircleImageDrawable;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rlMainBack;
    private Button icMainBtnSendMsg;
    private ImageView ivApple;


    private Context mContext;

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_detail);
        mContext =DetailActivity.this;
        initView();
        setListener();
    }

    public void initView(){
        rlMainBack = (RelativeLayout) findViewById(R.id.rl_main_back);
        icMainBtnSendMsg = (Button) findViewById(R.id.ic_main_btn_sendMsg);
        ivApple = (ImageView) findViewById(R.id.iv_apple);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icx_user_icon);
        ivApple.setImageDrawable(new CircleImageDrawable(bitmap));

    }

    public void setListener(){
        rlMainBack.setOnClickListener(this);
        icMainBtnSendMsg.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_main_back:
                finish();
                break;
            case R.id.ic_main_btn_sendMsg:
                Intent intent =new Intent(mContext, TalkActivity.class);
                startActivity(intent);
                break;
        }
    }
}
