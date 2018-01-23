package com.cx.hellorecoder;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {


    private Button btnStartRecord;
    private Button btnEndRecord;
    private Button btnPlayRecord;

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStartRecord = (Button) findViewById(R.id.btn_startRecord);
        btnEndRecord = (Button) findViewById(R.id.btn_endRecord);
        btnPlayRecord = (Button) findViewById(R.id.btn_playRecord);

        btnStartRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record();
                Toast.makeText(MainActivity.this, "开始录音了...", Toast.LENGTH_SHORT).show();
            }
        });

        btnEndRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                Toast.makeText(MainActivity.this, "结束录音了...", Toast.LENGTH_SHORT).show();
            }
        });

        btnPlayRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
                Toast.makeText(MainActivity.this, "播放录音了...", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 开始录制
     */
    private void record(){
        mediaRecorder=new MediaRecorder();
        //指定声音资源
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //定义输出格式
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //指定录音的音频编码
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        //定义输出文件的位置
        mediaRecorder.setOutputFile("file:///data/myvideo/a.3pg");

        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放录音
     */
    private void play(){
        mediaPlayer =new MediaPlayer();
        try {
            mediaPlayer.setDataSource("file:///data/myvideo/a.3pg");
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaRecorder =null;
        mediaPlayer =null;
    }
}
