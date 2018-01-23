package com.cx.helloandroid2.activity;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cx.helloandroid2.R;
import com.cx.helloandroid2.fragment.ConnectFragment;
import com.cx.helloandroid2.fragment.FindFragment;
import com.cx.helloandroid2.fragment.MeFragment;
import com.cx.helloandroid2.fragment.TalkFragment;

public class MainActivity extends AppCompatActivity {

    private FrameLayout rlContainter;
    private RadioGroup rdgAll;
    private RadioButton rbVideos;
    private RadioButton rbNovel;
    private RadioButton rbCartoon;
    private RadioButton rbMine;


    private TalkFragment talkFragment;
    private ConnectFragment connectFragment;
    private FindFragment findFragment;
    private MeFragment meFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.parseColor("#393A3F")); //更改状态栏的颜色
        setContentView(getLayoutId());
        initView();

    }

    public int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initView() {

        rlContainter = (FrameLayout) findViewById(R.id.rl_containter);
        rdgAll = (RadioGroup) findViewById(R.id.rdg_all);
        rbVideos = (RadioButton) findViewById(R.id.rb_videos);
        rbNovel = (RadioButton) findViewById(R.id.rb_novel);
        rbCartoon = (RadioButton) findViewById(R.id.rb_cartoon);
        rbMine = (RadioButton) findViewById(R.id.rb_mine);
        switchFragment(0);
        setListener();
    }

    public void setListener() {
        rdgAll.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                for (int i = 0; i < radioGroup.getChildCount(); i++) {
                    if (radioGroup.getChildAt(i).getId() == checkedId) {
                        switchFragment(i);  //来回切换Fragment
                    }
                }
            }
        });
    }

    private void switchFragment(int i) {
        FragmentTransaction transaction= getFragmentManager().beginTransaction();
        hideFragment(transaction);
        switch(i){
            case 0:
                if(talkFragment==null){
                    talkFragment=new TalkFragment();
                    transaction.add(R.id.rl_containter,talkFragment);
                }else{
                    transaction.show(talkFragment);
                }
                break;
            case 1:
                if(connectFragment==null){
                    connectFragment=new ConnectFragment();
                    transaction.add(R.id.rl_containter,connectFragment);
                }else{
                    transaction.show(connectFragment);
                }
                break;
            case 2:
                if(findFragment==null){
                    findFragment=new FindFragment();
                    transaction.add(R.id.rl_containter,findFragment);
                }else{
                    transaction.show(findFragment);
                }
                break;
            case 3:
                if(meFragment==null){
                    meFragment=new MeFragment();
                    transaction.add(R.id.rl_containter,meFragment);
                }else{
                    transaction.show(meFragment);
                }
        }
        transaction.commit();
    }

    //隐藏Fragment
    public void hideFragment(FragmentTransaction transaction){
        if(talkFragment!=null){
            transaction.hide(talkFragment);
        }
        if(connectFragment!=null){
            transaction.hide(connectFragment);
        }
        if(findFragment!=null){
            transaction.hide(findFragment);
        }
        if(meFragment!=null){
            transaction.hide(meFragment);
        }
    }
}
