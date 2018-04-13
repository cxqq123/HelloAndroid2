package com.cx.helloandroid2.server;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by cx on 2017/11/8.
 */

public class ServerManager extends Thread{

    private static final String IP = "192.168.1.174";
    private static final int POST = 30001;
    private Socket socket;
    private String username;
    private int iconID;
    private String message = null;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private ReceiveChatMsg receiveChatMsg;
    private static final ServerManager serverManager = new ServerManager();

    public static ServerManager getServerManager() {
        return serverManager;
    }

    private ServerManager() {
        receiveChatMsg = new ReceiveChatMsg();
    }

    public void run() {
        try {
            socket = new Socket(IP,POST);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));

            String m = null;
            String line;
            while((line = bufferedReader.readLine()) != null){
                if(!line.equals("-1")){
                    m += line;
                }else{
                    Log.i("cx","receive:" + m);
                    //解析接收到的消息
                    if(ParaseData.getAction(m).equals("GETCHATMSG")){
                        receiveChatMsg.delChatMsg(m);
                    }else {
                        message = m;
                    }
                    m = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                bufferedWriter.close();
                bufferedReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //发送消息
    public void sendMessage(Context context , final String msg){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (socket == null);
                        if(bufferedWriter != null){
                        Log.i("cx","send :" + msg);
                        try {
                            bufferedWriter.write(msg + "\n");
                            bufferedWriter.flush();
                            bufferedWriter.write("-1\n");
                            bufferedWriter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        }).start();
    }

    //获取消息
    public String getMessage(){
        for(int i = 0; i < 5; i++){
            if(message != null){
                break;
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }
}
