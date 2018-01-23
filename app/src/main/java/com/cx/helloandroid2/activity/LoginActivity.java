package com.cx.helloandroid2.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cx.helloandroid2.MessageActivity;
import com.cx.helloandroid2.R;
import com.cx.helloandroid2.server.ServerManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout rlMainBack;
    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;

    private ServerManager serverManager = ServerManager.getServerManager();
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        initView();
        setListener();
    }

    private void initView(){
        rlMainBack = (RelativeLayout) findViewById(R.id.rl_main_back);
        etName = (EditText) findViewById(R.id.et_name);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        serverManager.start(); //启动线程
    }

    private void setListener(){
        rlMainBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void goHome(){
//        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        Intent intent = new Intent(mContext, MessageActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_main_back:
                finish();
                break;

            case R.id.btn_login:
                String username = etName.getText().toString();
                String password = etPassword.getText().toString();
                if(login(username,password)){
                    serverManager.setUsername(username);
                    goHome();
                    finish();
                }else {
                    etName.setText("");
                    etPassword.setText("");
                    Toast.makeText(mContext,"用户名与密码不正确",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean login(String username,String password){
        //确认用户名和密码是否合法

        if(username.equals("cx") && password.equals("123")){
            return true;
        }
        if(username == null || password == null){
            return false;
        }
        //发消息给服务器
        String msg = "[LOGIN]:[" + username + ", " + password + "]";
        Log.i("cx",msg);
        serverManager.sendMessage(this,msg);
        //从服务器上获取ACK 确认信息
        String ack = serverManager.getMessage();
//        Log.i("cx",ack);
        //处理ack返回的消息
        if(ack == null){
            return false;
        }
        serverManager.setMessage(null);
        String p = "\\[ACKLOGIN\\]:\\[(.*)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        boolean flag = matcher.find() && matcher.group(1).equals("1");
        return flag;
    }
}
