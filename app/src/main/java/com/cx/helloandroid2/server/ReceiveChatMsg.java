package com.cx.helloandroid2.server;

import com.cx.helloandroid2.model.ModelChatMsg;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ReceiveChatMsg {

    public void delChatMsg(String msg){
        String sendName = null;
        String content = null;
        String avatarID = null;
        String fileType = null;
        String group = null;

        ServerManager.getServerManager().setMessage(null);
        String p = "\\[GETCHATMSG\\]:\\[(.*), (.*), (.*), (.*), (.*)\\]";
        Pattern pattern = Pattern.compile(p);
        Matcher matcher = pattern.matcher(msg);
        if (matcher.find()) {
            sendName = matcher.group(1);
            content = matcher.group(2);
            avatarID = matcher.group(3);
            fileType = matcher.group(4);
            group = matcher.group(5);

            ModelChatMsg chatMsg = new ModelChatMsg();
            chatMsg.setMyInfo(false);
            chatMsg.setContent(content);
            chatMsg.setChatObj(sendName);
            chatMsg.setUsername(ServerManager.getServerManager().getUsername());
            chatMsg.setGroup(group);

            chatMsg.setIconID(Integer.parseInt(avatarID));
//            AtyChatRoom.chatMsgList.add(chatMsg);
            ModelChatMsg.chatMsgList.add(chatMsg);
        }
    }
}
