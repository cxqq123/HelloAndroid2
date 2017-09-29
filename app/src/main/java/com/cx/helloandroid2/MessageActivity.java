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
import android.widget.AdapterView;
import android.widget.ListView;

import com.cx.helloandroid2.adapter.AdapterMessage;
import com.cx.helloandroid2.model.ModelMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {


    private ListView lvMessage;
    private AdapterMessage adapterMessage;
    private Context mContext;
    private List<ModelMessage> list;

    @TargetApi(21)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(R.layout.activity_message);
        mContext =MessageActivity.this;
        initView();
    }

    public void initView(){
        lvMessage = (ListView) findViewById(R.id.lv_message);
        //添加头布局（搜索）
        lvMessage.addHeaderView(getLayoutInflater().inflate(R.layout.layout_message_header,null));
        adapterMessage =new AdapterMessage(mContext,initList());
        lvMessage.setAdapter(adapterMessage);
        lvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position==0){
                    return ;
                }else{
                    Intent intent =new Intent(mContext,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //初始化数据
    public List<ModelMessage> initList(){
        list =new ArrayList<>();
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(),R.drawable.icx_apple);
        for(int i=0;i<15;i++){
            list.add(new ModelMessage(bitmap,"cx"+i,"第"+i+"消息","9/"+i));
        }
        return list;
    }
}
