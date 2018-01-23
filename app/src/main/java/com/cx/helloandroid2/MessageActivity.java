package com.cx.helloandroid2;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.cx.helloandroid2.adapter.AdapterMessage;
import com.cx.helloandroid2.adapter.AdapterPagePop;
import com.cx.helloandroid2.model.ModelMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener{


    private ListView lvMessage;
    private RelativeLayout rlMessageAdd; //头部右边的添加按钮
    private RelativeLayout rlMainHeader; //整个头部布局

    private PopupWindow popWindow; //弹出菜单
    private ListView lvPop; //弹出菜单的listView

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
        setListener();
    }

    public void initView(){
        lvMessage = (ListView) findViewById(R.id.lv_message);
        rlMessageAdd = (RelativeLayout) findViewById(R.id.rl_message_add);
        rlMainHeader = (RelativeLayout) findViewById(R.id.rl_main_header);

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
                    Intent intent =new Intent(mContext,DetailActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void setListener(){
        rlMessageAdd.setOnClickListener(this);
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

    /**
     * 显示弹出菜单
     */
    public void showPopupWindow(List<String> data,View v){
        int width = getWindowManager().getDefaultDisplay().getWidth();
        if(popWindow == null || lvPop == null){
            View popView = View.inflate(mContext, R.layout.layout_page_page, null);
            lvPop = (ListView)popView.findViewById(R.id.lvPagePop);
            popWindow = new PopupWindow(popView, 2 * width/5, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        AdapterPagePop circlePop = new AdapterPagePop(mContext, data);
        lvPop.setAdapter(circlePop);
        //设置adapter之后获取listView的高度

        //设置点击弹出菜单后,窗体背景的透明度
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.7f;
//        getWindow().setAttributes(lp);

        popWindow.setFocusable(true);
//        popWindow.setOutsideTouchable(false);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popWindow.setBackgroundDrawable(new BitmapDrawable());
        popWindow.showAsDropDown(v, v.getWidth(), 0); //显示的位置

        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });

        lvPop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popWindow.dismiss();
                switch(position){
                    case 0://设置
                        break;
                    case 1://扫码
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rl_message_add:
                //显示PopWindow
                List<String> data = new ArrayList<>();
                data.add("设置");
//                data.add("个人中心");
                data.add("扫一扫");
                showPopupWindow(data,rlMainHeader);
                break;
        }
    }
}
