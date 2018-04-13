package com.cx.helloandroid2.util;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cx on 2018/4/13.
 */

public class Utils {

    //检查字符串是否为空
    public static Boolean isNullOrEmpty(String s)
    {
        if (s == null)
        {
            return true;
        }
        else if (s.length() == 0)
        {
            return true;
        }
        return false;
    }

    /***
     * 先获取当前时间的年月日
     * 再分别比较当前时间的年月日和传进来的时间的年月日
     * 1 如果年份不同，则直接显示 年月日时分(2015年6月20  下午5:20)
     * 2 年份相同，比较月份，如果月份不同，则直接显示月日 时分(6月23日  下午16:30)
     * 3 月份相同，则比较日期，如果日期相同，显示今天(今天  上午9：20)
     * 如果日期不同，如果是昨天就显示昨天(昨天  下午15：20)
     */
    public static String compareTime(long time){
        String myTime = "";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
        SimpleDateFormat sdf3 = new SimpleDateFormat("dd");
        String year = sdf.format(new Date(System.currentTimeMillis()));
        String month = sdf2.format(new Date(System.currentTimeMillis()));
        String date = sdf3.format(new Date(System.currentTimeMillis()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日  HH:mm");
        String lastTime = dateFormat.format(new Date(time));
        myTime = lastTime;
        String lastYear = lastTime.substring(0, 5);
        String lastMonth = lastTime.split("年")[1];
        String lastDate = lastMonth.substring(3, 5);
        String min = lastMonth.substring(7, lastMonth.length());
        if(!lastTime.startsWith(year)){
            return lastTime;
        }else{
            myTime = lastMonth;
            if(!lastMonth.startsWith(month)){
                System.out.println(lastMonth);
                return lastMonth;
            }else{
                if(Integer.parseInt(lastDate) - Integer.parseInt(date) == -1){//昨天
                    return "昨天 "+min;
                }else if(Integer.parseInt(lastDate) - Integer.parseInt(date) == 0){
                    return "今天 "+min;
                }else{
                    return myTime;
                }
            }
        }
    }

    public static String Date2Str(Long date, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        //System.out.println("Date2Str:"+s);
        return sdf.format(new Date(date));
    }
    public static String Date2Str(Date date, String format)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        //System.out.println("Date2Str:"+s);
        return sdf.format(date);
    }

    public static Date Str2Date(String str, String format)
    { // "yyyy-MM-dd HH:mm:ss"
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try
        {
            return sdf.parse(str);
        } catch (Exception ex)
        {
            Log.e("Me.Str2Date", "str=" + str + " |format=" + format);
            return new Date(0);
        }
    }

    public static String Date2Str(Date date)
    {
        return Date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }
    public static String Date2Str(Long dateMills)
    {
        return Date2Str(new Date(dateMills), "yyyy-MM-dd HH:mm:ss");
    }

    public static Date Str2Date(String str)
    {
        return Str2Date(str, "yyyy-MM-dd HH:mm:ss");
    }

    public static int dip2Px(Context context, float dpValue)
    {
        //final float scale = context.getResources().getDisplayMetrics().density;
        final float scale = context.getResources().getDisplayMetrics().density;
        return floatToInt(dpValue * scale + 0.5f);
    }

    public static int floatToInt(float f){
        int i = 0;
        if(f>0) //正数
            i = (int) ((f*10 + 5)/10);
        else if(f<0) //负数
            i = (int) ((f*10 - 5)/10);
        else i = 0;

        return i;

    }
}
