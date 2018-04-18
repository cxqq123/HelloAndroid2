package com.cx.helloandroid2.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.server.ServerManager;
import com.cx.helloandroid2.util.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;
    private RelativeLayout rlMainBack;
    private EditText etName;
    private EditText etPassword;
    private Button btnLogin;

    private ServerManager serverManager = ServerManager.getServerManager();

    private String username , password = "";

    private static final int LOGIN_WHAT = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOGIN_WHAT:
                    boolean isLogin = (boolean) msg.obj;
                    handlerLogin(isLogin);
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_login);
        mContext = LoginActivity.this;
        ServerManager.setContext(mContext);
        initView();
        setListener();
    }

    private void initView(){
        rlMainBack = (RelativeLayout) findViewById(R.id.rl_main_back);
        etName = (EditText) findViewById(R.id.et_name);
        if(!Utils.isNullOrEmpty(etName.getText().toString())){
            String str = etName.getText().toString();
            etName.setSelection(str.length());
        }
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        serverManager.start(); //启动线程
    }

    private void setListener(){
        rlMainBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void goHome(){
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
                username = etName.getText().toString();
                password = etPassword.getText().toString();
                if(Utils.getNetworkState(mContext) == 0){
                    Toast.makeText(mContext,"当前无网络连接,请连接网络",Toast.LENGTH_LONG).show();
                }else{
                    loginTask(username,password);
                }
                break;
        }
    }


    public void handlerLogin(boolean isLogin){
        if (isLogin) {
            serverManager.setUsername(username);
            goHome();
            finish();
        } else {
            etName.setText("");
            etPassword.setText("");
            Toast.makeText(mContext,"用户名与密码不正确",Toast.LENGTH_SHORT).show();
        }
    }

    private void loginTask(final String userName , final String password){
        new Thread(){
            @Override
            public void run() {
                boolean isLogin = false;
                super.run();
                if (userName == null || userName.length() > 10 || password.length() > 20) {
                    return;
                }
                // send msg to servers
                String msg = "[LOGIN]:[" + userName + ", " + password + "]";
                serverManager.sendMessage(mContext , msg);
                // get msg from servers return
                String ack = serverManager.getMessage();
                Log.e("cx" ,"ack :" + ack);
                // deal msg
                if (ack == null) {
                    return ;
                }
                serverManager.setMessage(null);
                String p = "\\[ACKLOGIN\\]:\\[(.*)\\]";
                Pattern pattern = Pattern.compile(p);
                Matcher matcher = pattern.matcher(ack);
                isLogin = matcher.find() && matcher.group(1).equals("1");

                Message message = new Message();
                message.what = LOGIN_WHAT;
                message.obj = isLogin;
                mHandler.sendMessage(message);
                return ;
            }
        }.start();
    }
}
