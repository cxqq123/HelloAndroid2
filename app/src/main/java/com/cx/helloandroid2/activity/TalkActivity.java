package com.cx.helloandroid2.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.cx.helloandroid2.model.ChatMsg;
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

    private String TAG="cx Log";

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
    private List<ChatMsg> chatMsgList = new ArrayList<>();
    private ImageView ivVoice;
    private TextView tvTalkUserName;
    private TextView tvSend;

    public  AdapterChatMsg adapterChatMsgList;
    private AdapterTalk adapterTalk;


    //输入法管理器
    private InputMethodManager inputManager;


    @TargetApi(21)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_talk);
        mContext = TalkActivity.this;
        inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE); //实例化输入法管理器
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
        chatMsgList.clear();
        loadChatMsg();
        adapterChatMsgList = new AdapterChatMsg(TalkActivity.this, R.layout.adapter_chat_other, chatMsgList);
        lvMessage.setAdapter(adapterChatMsgList);


    }
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
                if (!content.isEmpty()) {
                    ChatMsg msg = new ChatMsg();
                    msg.setContent(content);
                    msg.setUsername(ServerManager.getServerManager().getUsername());
                    msg.setIconID(ServerManager.getServerManager().getIconID());
                    msg.setMyInfo(true);
                    msg.setChatObj(chatObj);
                    msg.setGroup(group.equals("0") ? chatObj : " ");
                    if (sendToChatObj(msg.getContent())) {
                        ChatMsg.chatMsgList.add(msg);
                        chatMsgList.add(msg);
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

    private void refreshData(){
        if(adapterChatMsgList == null){
            adapterChatMsgList = new AdapterChatMsg(mContext,R.layout.adapter_chat_other,chatMsgList);
            adapterChatMsgList.notifyDataSetChanged();
        }else{
            adapterChatMsgList.setData(chatMsgList);
        }
        lvMessage.setAdapter(adapterTalk);
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
            for (ChatMsg chatMsg : ChatMsg.chatMsgList) {
                if (chatMsg.getGroup().equals(chatObj)) {
                    Log.e("cx" ,"loadChatMsg :" + chatMsg.toString());
                    chatMsgList.add(chatMsg);
                }
            }
        } else {
            for (ChatMsg chatMsg : ChatMsg.chatMsgList) {
                if (chatMsg.getChatObj().equals(chatObj) && chatMsg.getGroup().equals(" ")) {
                    Log.e("cx" ,"loadChatMsg 2:" + chatMsg.toString());
                    chatMsgList.add(chatMsg);
                }
            }
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(){
        inputManager.hideSoftInputFromWindow(etText.getWindowToken(),0);
    }
}
