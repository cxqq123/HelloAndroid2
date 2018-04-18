package com.cx.helloandroid2.model;

/**
 * Created by cx on 2018/4/18.
 */

public class UserInfo {

    public long userID;
    public String userName;
    public String password;
    public String userSign;
    public String userPhone;
    public String userEmail;
    public String userPart;

    public UserInfo(){

    }

    public UserInfo(String userPhone , String userEmail , String userPart){
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userPart = userPart;
    }
}
