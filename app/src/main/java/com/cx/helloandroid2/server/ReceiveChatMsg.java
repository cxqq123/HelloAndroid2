package com.cx.helloandroid2.server;

/**
 * Created by Administrator on 2017/11/8.
 */

public class ReceiveChatMsg {

//    public void delChatMsg(String msg){
//        String sendName = null;
//        String content = null;
//        String avatarID = null;
//        String fileType = null;
//        String group = null;
//
//        ServerManager.getServerManager().setMessage(null);
//        String p = "\\[GETCHATMSG\\]:\\[(.*), (.*), (.*), (.*), (.*)\\]";
//        Pattern pattern = Pattern.compile(p);
//        Matcher matcher = pattern.matcher(msg);
//        if (matcher.find()) {
//            sendName = matcher.group(1);
//            content = matcher.group(2);
//            avatarID = matcher.group(3);
//            fileType = matcher.group(4);
//            group = matcher.group(5);
//
//            ModelChatMsg modelChatMsg = new ModelChatMsg();
//            modelChatMsg.setMyInfo(false);
//            modelChatMsg.setContent(content);
//            modelChatMsg.setChatObj(sendName);
//            modelChatMsg.setUsername(ServerManager.getServerManager().getUsername());
//            modelChatMsg.setGroup(group);
//
//            modelChatMsg.setIconID(Integer.parseInt(avatarID));
////            AtyChatRoom.modelChatMsgList.add(modelChatMsg);
//            TalkActivity.modelChatMsgList.add(modelChatMsg);
//            ModelChatMsg.modelChatMsgList.add(modelChatMsg);
//            Log.v("cx" , "modelChatMsg :" + modelChatMsg.toString());
//        }
//    }
}
