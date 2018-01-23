package com.cx.helloandroid2.util;

/**
 * Created by Administrator on 2017/10/11.
 */

public class EmptyUtil {

    public static boolean isEmptyOrNull(String str){
        if(str.equals("") || str==null){
            return true;
        }else{
            return false;
        }
    }

}
