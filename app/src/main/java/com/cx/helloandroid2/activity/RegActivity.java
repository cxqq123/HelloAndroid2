package com.cx.helloandroid2.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.util.Utils;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {


    private RelativeLayout rlMainBack;
    private EditText etName;
    private EditText etPassword;
    private EditText etPasswordAgain;
    private Button btnReg;
    private Context mContext ;
    private Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_reg);
        mContext = RegActivity.this;
        initView();
    }

    public void initView(){
        rlMainBack = (RelativeLayout) findViewById(R.id.rl_main_back);
        etName = (EditText) findViewById(R.id.et_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        etPasswordAgain = (EditText) findViewById(R.id.et_password_again);
        btnReg = (Button) findViewById(R.id.btn_reg);

        btnReg.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_reg:
                rexRegInfo();
                break;
        }
    }

    //验证注册信息
    public void rexRegInfo(){
        String regPassword = etPassword.getText().toString();
        String regPasswordAgain = etPasswordAgain.getText().toString();
        String regName = etName.getText().toString();

        if(Utils.isNullOrEmpty(regName)){
            Toast.makeText(mContext , "注册名不能为空" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(Utils.isNullOrEmpty(regPassword)){
            Toast.makeText(mContext , "注册密码不能为空" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(Utils.isNullOrEmpty(regPasswordAgain)){
            Toast.makeText(mContext , "再次确认密码不能为空" , Toast.LENGTH_SHORT).show();
            return;
        }

        if(!regPasswordAgain.equals(regPassword)){
            Toast.makeText(mContext , "两次密码不一致,请重新输入" , Toast.LENGTH_SHORT).show();
            etPassword.setText("");
            etPasswordAgain.setText("");
        }else{
            Toast.makeText(mContext, "恭喜你注册成功,可以去登录，尽情的跟好友聊天了" , Toast.LENGTH_SHORT).show();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                      Intent intent = new Intent(mContext, LoginActivity.class);
                      startActivity(intent);
                      finish();
                }
            },3000);

        }
    }
}
