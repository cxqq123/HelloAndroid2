package com.cx.storagedemo;

import android.content.Context;

/**
 * Created by Administrator on 2017/11/10.
 */

public class CommonUtil {
    //懒汉单例模式，延迟加载实例，只有用到时，才加载
    private static CommonUtil instance;
    private Context context;

    public CommonUtil(Context context) {
        this.context = context;
    }

    public static CommonUtil getInstance(Context context){
        if(null == instance){
            instance = new CommonUtil(context);
        }
        return instance;
    }
}
