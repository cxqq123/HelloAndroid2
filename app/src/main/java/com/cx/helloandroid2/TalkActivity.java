package com.cx.helloandroid2;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cx.helloandroid2.adapter.AdapterTalk;
import com.cx.helloandroid2.model.ModelTalk;
import com.cx.helloandroid2.util.EmptyUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    private Button btnSend;
    private LinearLayout llMessage;
    private ListView lvMessage;

    private List<ModelTalk> talkList =new ArrayList<>();
    private ModelTalk modelTalk = new ModelTalk();


    private ImageView ivVoice;
    private ImageView ivSmice;
    private ImageView ivMore;

    private AdapterTalk adapterTalk;


    //输入法管理器
    private InputMethodManager inputManager;


    @TargetApi(21)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_talk);
        mContext =TalkActivity.this;
        inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE); //实例化输入法管理器
        initView();
        bindData(talkList); //绑定数据
        setListener();
    }

    public void initView(){
        rlMainBack = (RelativeLayout) findViewById(R.id.rl_main_back);
        etText = (EditText) findViewById(R.id.et_text);
        etText.setCursorVisible(false);
        rlTalkAll = (RelativeLayout) findViewById(R.id.rl_talk_all);
        llMessage = (LinearLayout) findViewById(R.id.ll_message);
        lvMessage = (ListView) findViewById(R.id.lv_message);

        btnSend = (Button) findViewById(R.id.btn_send);
        ivVoice = (ImageView) findViewById(R.id.iv_voice);
        ivSmice = (ImageView) findViewById(R.id.iv_smice);
        ivMore = (ImageView) findViewById(R.id.iv_more);


        //编辑框中的字的变化
        etText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String content = etText.getText().toString().trim();
                if (!content.equals("")) {
                    ivMore.setVisibility(View.GONE);
                    btnSend.setVisibility(View.VISIBLE);
                }else{
                    ivMore.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void setListener(){
        rlMainBack.setOnClickListener(this);
        etText.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        ivMore.setOnClickListener(this);
    }


    public void bindData(List<ModelTalk> result){
        if(result == null || result.size() ==0){
            llMessage.setVisibility(View.VISIBLE);
            lvMessage.setVisibility(View.GONE);
        }else{
            llMessage.setVisibility(View.GONE);
            lvMessage.setVisibility(View.VISIBLE);
            //排序  以前的排在上面
            Collections.sort(result, new Comparator<ModelTalk>() {

                @Override
                public int compare(ModelTalk lhs, ModelTalk rhs) {
                    if(lhs.fbID - rhs.fbID > 0){
                        return 1;
                    }else{
                        return -1;
                    }
                }
            });

            adapterTalk = new AdapterTalk(mContext, result);
            lvMessage.setAdapter(adapterTalk);
        }

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
            case R.id.iv_more:
                Log.v(TAG,"more");
                break;
            case R.id.btn_send:
                Toast.makeText(mContext,"发送了一条消息",Toast.LENGTH_SHORT).show();
                String msg=etText.getText().toString();
                if(EmptyUtil.isEmptyOrNull(msg)){
                    addMessage(msg); //添加一条消息
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
        bindData(talkList); //刷新绑定数据
        etText.setText("");
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput(){
        inputManager.hideSoftInputFromWindow(etText.getWindowToken(),0);
    }
}
