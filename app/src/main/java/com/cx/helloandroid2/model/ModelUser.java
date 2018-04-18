package com.cx.helloandroid2.model;

import android.graphics.Bitmap;

/**
 * Created by cx on 2017/9/29.
 */

public class ModelUser {

    public  Bitmap icon;
    public  String name;
    public  String message;
    public  String date;
    public String userPhone;
    public String userEmail;
    public String userPart;
    public ModelUser(){

    }
    public ModelUser(Bitmap icon, String name, String message, String date) {
        this.icon = icon;
        this.name = name;
        this.message = message;
        this.date = date;
    }
}
