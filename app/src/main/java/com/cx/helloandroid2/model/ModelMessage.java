package com.cx.helloandroid2.model;

import android.graphics.Bitmap;

/**
 * Created by cx on 2017/9/29.
 */

public class ModelMessage {

    public  Bitmap icon;
    public  String name;
    public  String message;
    public  String date;

    public ModelMessage(Bitmap icon, String name, String message, String date) {
        this.icon = icon;
        this.name = name;
        this.message = message;
        this.date = date;
    }
}
