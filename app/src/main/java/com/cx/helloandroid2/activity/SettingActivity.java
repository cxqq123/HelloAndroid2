package com.cx.helloandroid2.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.util.Constancts;

public class SettingActivity extends AppCompatActivity {

    private RelativeLayout rlMainBack;
    private RelativeLayout rlEnter;
    private RelativeLayout rlZDList;
    private RelativeLayout rlZDList2;
    private RelativeLayout rlZDList3;


    private Context mContext;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_setting);
        mContext = SettingActivity.this;
        initView();
    }

    public void initView(){
        rlMainBack = (RelativeLayout) findViewById(R.id.rl_main_back);
        rlEnter = (RelativeLayout) findViewById(R.id.rlEnter);
        rlZDList = (RelativeLayout) findViewById(R.id.rlZDList);
        rlZDList2 = (RelativeLayout) findViewById(R.id.rlZDList2);
        rlZDList3 = (RelativeLayout) findViewById(R.id.rlZDList3);

        rlMainBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rlEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , WebViewActivity.class);
                intent.putExtra(Constancts.WEBSITE , "http://www.cxsmart123.cn");
                intent.putExtra(Constancts.WEBSITE_TITLE , "cx羽主页");
                startActivity(intent);
            }
        });

        rlZDList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , WebViewActivity.class);
                intent.putExtra(Constancts.WEBSITE , "https://github.com/cxqq123");
                intent.putExtra(Constancts.WEBSITE_TITLE , "Github");
                startActivity(intent);
            }
        });

        rlZDList2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , WebViewActivity.class);
                intent.putExtra(Constancts.WEBSITE , "https://blog.csdn.net/m0_37094131");
                intent.putExtra(Constancts.WEBSITE_TITLE , "csdn");
                startActivity(intent);
            }
        });

        rlZDList3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , WebViewActivity.class);
                intent.putExtra(Constancts.WEBSITE , "https://www.baidu.com");
                intent.putExtra(Constancts.WEBSITE_TITLE , "百度");
                startActivity(intent);
            }
        });
    }
}
