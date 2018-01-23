package com.cx.helloandroid2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ModelChatMsg {

    private boolean myInfo;
    private int iconID;
    private String username;
    private String content;
    private String chatObj;
    private String group;

    public static List<ModelChatMsg> chatMsgList = new ArrayList<>();
    public boolean isMyInfo() {
        return myInfo;
    }

    public void setMyInfo(boolean myInfo) {
        this.myInfo = myInfo;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getChatObj() {
        return chatObj;
    }

    public void setChatObj(String chatObj) {
        this.chatObj = chatObj;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
