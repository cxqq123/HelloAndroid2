package com.cx.helloandroid2.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.model.UserInfo;
import com.cx.helloandroid2.util.Constancts;
import com.cx.helloandroid2.util.Utils;
import com.cx.helloandroid2.view.CircleImageDrawable;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    private RelativeLayout rlMainBack;
    private Button icMainBtnSendMsg;
    private ImageView ivApple;
    private TextView tvDetailUserName;
    private Context mContext;
    private TextView icxTvShowPhone;
    private TextView icxTvShowEmail;
    private TextView icxTvShowBranch;
    private Button btnVoiceTalk;
    private TextView tvJiancheng;



    private String userName = "";

    private List<UserInfo> userInfos = new ArrayList<>();

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_detail);
        mContext = DetailActivity.this;
        bindData();
        initView();
        setListener();
    }

    public void bindData(){
        Intent intent = getIntent();
        userName = intent.getStringExtra(Constancts.USER_NAME);
        initPhoneAndEmail();
    }

    public void initView(){
        rlMainBack = (RelativeLayout) findViewById(R.id.rl_main_back);
        icMainBtnSendMsg = (Button) findViewById(R.id.ic_main_btn_sendMsg);
        ivApple = (ImageView) findViewById(R.id.iv_apple);
        tvDetailUserName = (TextView) findViewById(R.id.tv_detail_user_name);
        btnVoiceTalk = (Button) findViewById(R.id.btn_voice_talk);
        tvJiancheng = (TextView) findViewById(R.id.tv_jiancheng);

        icxTvShowPhone = (TextView) findViewById(R.id.icx_tv_showPhone);
        icxTvShowEmail = (TextView) findViewById(R.id.icx_tv_showEmail);
        icxTvShowBranch = (TextView) findViewById(R.id.icx_tv_showBranch);

        icxTvShowPhone.setText(userInfos.get(getRandom(10)).userPhone);
        icxTvShowEmail.setText(userInfos.get(getRandom(10)).userEmail);
        icxTvShowBranch.setText(userInfos.get(getRandom(10)).userPart);


        Log.e("cx" , "username :" + userName);
        if(!Utils.isNullOrEmpty(userName)) {
            tvDetailUserName.setText(userName);
            tvJiancheng.setText(userName.substring(0,1));
        }else{
            tvDetailUserName.setText("好友");
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.icx_user_icon);
        ivApple.setImageDrawable(new CircleImageDrawable(bitmap));

    }

    //获取[0,n)之间的一个随机整数
    public static int getRandom(int n) {
        int a = (int)(Math.random() * n);
        Log.e("cx" , "getRandom : " + a);
        return a;
    }

    public void setListener(){
        rlMainBack.setOnClickListener(this);
        icMainBtnSendMsg.setOnClickListener(this);
        btnVoiceTalk.setOnClickListener(this);
    }



    private void initPhoneAndEmail(){
        String[] phones = { "15180463773" ,
                            "17680263437" ,
                            "15925463742" ,
                            "15865646573" ,
                            "13804637731" ,
                            "15084346375" ,
                            "15980563740" ,
                            "18632463779" ,
                            "13733453774" ,
                            "13560463772"
        };

        String[] emails = { "744185734@qq.com",
                            "334145534@qq.com",
                            "265166534@qq.com",
                            "7514183334@qq.com",
                            "134182334@qq.com",
                            "719626734@qq.com",
                            "141185584@qq.com",
                            "2354156244@qq.com",
                            "344185634@qq.com",
                            "111857342@qq.com"
        };

        String[] parts = {  "孙悟空",
                            "东天门",
                            "南天门",
                            "西天门",
                            "北天门",
                            "故宫",
                            "长安",
                            "天坛",
                            "世界之窗",
                            "宝安机场",

        };

        for(int i = 0 ; i < 10 ; i++){
            userInfos.add(new UserInfo(phones[i] , emails[i] , parts[i]));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_main_back:
                finish();
                break;
            case R.id.ic_main_btn_sendMsg:
                Intent intent =new Intent(mContext, TalkActivity.class);
                intent.putExtra(Constancts.USER_NAME , userName);
                startActivity(intent);
                break;
            case R.id.btn_voice_talk:
                Toast.makeText(mContext , "语音通话功能还在内测中,会在以后的版本更新，敬请期待!" , Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
