package com.cx.helloandroid2.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.adapter.AdapterChatMsg;
import com.cx.helloandroid2.adapter.AdapterTalk;
import com.cx.helloandroid2.model.ModelChatMsg;
import com.cx.helloandroid2.model.ModelTalk;
import com.cx.helloandroid2.server.ParaseData;
import com.cx.helloandroid2.server.ServerManager;
import com.cx.helloandroid2.util.Constancts;
import com.cx.helloandroid2.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cx on 2017/9/29.
 * 聊天Activity
 */

public class TalkActivity extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "cx Log";
    private RelativeLayout rlMainBack;
    private EditText etText;
    private boolean isShowSoftKeyBoard;
    private Context mContext;
    private RelativeLayout rlTalkAll;
    private LinearLayout llMessage;
    private ListView lvMessage;

    private List<ModelTalk> talkList =new ArrayList<>();
    private ModelTalk modelTalk = new ModelTalk();

    private String chatObj;
    private String group;
    public  List<ModelChatMsg> modelChatMsgList = new ArrayList<>();
    private ImageView ivVoice;
    private TextView tvTalkUserName;
    private TextView tvSend;

    public  AdapterChatMsg adapterChatMsgList;
    private AdapterTalk adapterTalk;

    //输入法管理器
    private InputMethodManager inputManager;

    private ReadChatMessageBroadcast readChatMessageBroadcast;
    private IntentFilter intentFilter;

    private static final int TALK_WHAT = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case TALK_WHAT :
                    ModelChatMsg modelChatMsg = (ModelChatMsg) msg.obj;
                    handleChatMsg(modelChatMsg);
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_talk);
        mContext = TalkActivity.this;
        inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE); //实例化输入法管理器
        ServerManager.setContext(mContext);
        initView();
        bindData(); //绑定数据
        setListener();
    }

    public void initView(){
        rlMainBack = (RelativeLayout) findViewById(R.id.rl_main_back);
        etText = (EditText) findViewById(R.id.et_text);
        etText.setCursorVisible(false);
        rlTalkAll = (RelativeLayout) findViewById(R.id.rl_talk_all);
        llMessage = (LinearLayout) findViewById(R.id.ll_message);
        lvMessage = (ListView) findViewById(R.id.lv_message);
        tvTalkUserName = (TextView) findViewById(R.id.tv_talk_user_name);
        tvSend = (TextView) findViewById(R.id.tv_send);
        ivVoice = (ImageView) findViewById(R.id.iv_voice);
//
        //注册一条广播，用来收集服务器返回的信息
        readChatMessageBroadcast = new ReadChatMessageBroadcast();
        intentFilter = new IntentFilter();
        intentFilter.addAction(Constancts.READ_CHAT_MESSAGE);
        registerReceiver(readChatMessageBroadcast,intentFilter);


        //编辑框中的字的变化
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void setListener(){
        rlMainBack.setOnClickListener(this);
        etText.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }


    public void bindData(){
        Intent intent = getIntent();
        String userName = intent.getStringExtra(Constancts.USER_NAME);
        if(!Utils.isNullOrEmpty(userName)){
            tvTalkUserName.setText(userName);
        }
        chatObj = intent.getStringExtra(Constancts.USER_NAME);
        group = ParaseData.getAllGroupList(this).contains(chatObj) ? "0" : "1";
        modelChatMsgList.clear();
        loadChatMsg();
        adapterChatMsgList = new AdapterChatMsg(TalkActivity.this, R.layout.adapter_chat_other, modelChatMsgList);
        lvMessage.setAdapter(adapterChatMsgList);

    }
    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.rl_main_back:
                finish();
                break;
            case R.id.et_text:
                //显示软件盘,不会把布局顶上去
                inputManager.showSoftInput(etText,0);
                break;
            case R.id.tv_send:
                Toast.makeText(mContext,"发送了一条消息",Toast.LENGTH_SHORT).show();

                String content = etText.getText().toString();
                if (!Utils.isNullOrEmpty(content)) {
                    ModelChatMsg msg = new ModelChatMsg();
                    msg.setContent(content);
                    msg.setUsername(ServerManager.getServerManager().getUsername());
                    msg.setIconID(ServerManager.getServerManager().getIconID());
                    msg.setMyInfo(true);
                    msg.setChatObj(chatObj);
                    msg.setGroup(group.equals("0") ? chatObj : " ");
                    if (sendToChatObj(msg.getContent())) {
                        ModelChatMsg.modelChatMsgList.add(msg);
                        modelChatMsgList.add(msg);
                        etText.setText("");
                    } else {
                        Toast.makeText(TalkActivity.this, "send failed", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    public void addMessage(String str){
        //封装一条消息
        modelTalk.fbID=0;
        modelTalk.fbID++;
        modelTalk.fbContent =str;
        modelTalk.fbReply="这是一条回复";
        talkList.add(modelTalk);
        etText.setText("");
//        refreshData();
    }

    private void refreshData(List<ModelChatMsg> modelChatMsgs){
        if(adapterChatMsgList == null){
            adapterChatMsgList = new AdapterChatMsg(mContext,R.layout.adapter_chat_other, modelChatMsgs);
            adapterChatMsgList.notifyDataSetChanged();
        }else{
            adapterChatMsgList.setData(modelChatMsgs);
        }
        lvMessage.setAdapter(adapterChatMsgList);
    }

    private boolean sendToChatObj(String content) {
        String msg = "[CHATMSG]:[" + chatObj + ", " + content + ", " + ServerManager.getServerManager().getIconID() +", Text]";
        ServerManager serverManager = ServerManager.getServerManager();
        serverManager.sendMessage(this, msg);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String ack = serverManager.getMessage();
        if (ack == null) {
            return false;
        }
        String p = "\\[ACKCHATMSG\\]:\\[(.*)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(ack);
        return matcher.find() && matcher.group(1).equals("1");
    }

    private void loadChatMsg() {
        if (group == "0") {
            for (ModelChatMsg modelChatMsg : ModelChatMsg.modelChatMsgList) {
                if (modelChatMsg.getGroup().equals(chatObj)) {
                    Log.e("cx" ,"loadChatMsg :" + modelChatMsg.toString());
                    modelChatMsgList.add(modelChatMsg);
                }
            }
        } else {
            for (ModelChatMsg modelChatMsg : ModelChatMsg.modelChatMsgList) {
                if (modelChatMsg.getChatObj().equals(chatObj) && modelChatMsg.getGroup().equals(" ")) {
                    Log.e("cx" ,"loadChatMsg 2:" + modelChatMsg.toString());
                    modelChatMsgList.add(modelChatMsg);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(readChatMessageBroadcast);
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(){
        inputManager.hideSoftInputFromWindow(etText.getWindowToken(),0);
    }


    private class ReadChatMessageBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = intent.getStringExtra(Constancts.READ_CHAT_MESSAGE);
            Log.e("cx" , "ReadChatMessageBroadcast :" + msg);
            delChatMsg(msg);
        }
    }

    private void handleChatMsg(ModelChatMsg modelChatMsg){
        if(modelChatMsg != null){
            modelChatMsgList.add(modelChatMsg);
            refreshData(modelChatMsgList);
        }
    }

    public void delChatMsg(final String msg){
        final String[] sendName = {null};
        final String[] content = {null};
        final String[] avatarID = {null};
        final String[] fileType = {null};
        final String[] group = {null};

        new Thread(){
            @Override
            public void run() {
                super.run();
                ServerManager.getServerManager().setMessage(null);
                String p = "\\[GETCHATMSG\\]:\\[(.*), (.*), (.*), (.*), (.*)\\]";
                Pattern pattern = Pattern.compile(p);
                Matcher matcher = pattern.matcher(msg);
                if (matcher.find()) {
                    sendName[0] = matcher.group(1);
                    content[0] = matcher.group(2);
                    avatarID[0] = matcher.group(3);
                    fileType[0] = matcher.group(4);
                    group[0] = matcher.group(5);

                    ModelChatMsg modelChatMsg = new ModelChatMsg();
                    modelChatMsg.setMyInfo(false);
                    modelChatMsg.setContent(content[0]);
                    modelChatMsg.setChatObj(sendName[0]);
                    modelChatMsg.setUsername(ServerManager.getServerManager().getUsername());
                    modelChatMsg.setGroup(group[0]);
                    modelChatMsg.setIconID(Integer.parseInt(avatarID[0]));

                    Message message = new Message();
                    message.what = TALK_WHAT;
                    message.obj = modelChatMsg;
                    mHandler.sendMessage(message);
                    Log.v("cx" , "modelChatMsg :" + modelChatMsg.toString());
                }
            }
        }.start();

    }
}
