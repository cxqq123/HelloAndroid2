package com.cx.hellorecoder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.fmod.FMOD;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener{

    private Button start;
    private Button stop;
    private ListView listView;
    private MediaPlayer myPlayer;
    private MediaRecorder myRecorder;
    private String path;
    private String paths = path;
    private File saveFilePath;
    String[] listFile = null;

    ShowRecorderAdpter showRecord;
    AlertDialog aler = null;

    private int type;
    private ExecutorService fixedThreadPool;
    private PlayThread playThread;
    private String recordPath;
    private String amrPath;
    private String mp3Path;

    private int pos;
    private String pathRecord ="file:///android_asset/hensen.mp3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        initView();

        fixedThreadPool= Executors.newFixedThreadPool(1);
        FMOD.init(this);

        myPlayer = new MediaPlayer();
        myRecorder = new MediaRecorder();
        myRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        myRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        myRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);
        showRecord = new ShowRecorderAdpter();

        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            try {
                path = Environment.getExternalStorageDirectory()
                        .getCanonicalPath().toString()
                        + "/cx";
                Log.i("cx path",path);
                File files = new File(path);
                if (!files.exists()) {
                    files.mkdir();
                }
                listFile = files.list();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (listFile != null) {
            listView.setAdapter(showRecord);
        }

    }


    public void initView(){
        start = (Button) findViewById(R.id.start);
        stop = (Button) findViewById(R.id.stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    class PlayThread implements Runnable{

        @Override
        public void run() {
            Utils.fix(pathRecord,type);
            Log.i("cx fix",pathRecord+":"+type);
        }
    }

    class ShowRecorderAdpter extends BaseAdapter implements View.OnClickListener{

        @Override
        public int getCount() {
            return listFile.length;
        }

        @Override
        public Object getItem(int arg0) {
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;

        }

        @Override
        public View getView(final int postion, View arg1, ViewGroup arg2) {
            pos =postion;
            View views = LayoutInflater.from(RecordActivity.this).inflate(
                    R.layout.list_show_filerecorder, null);
            TextView filename = (TextView) views
                    .findViewById(R.id.show_file_name);
            ImageView plays = (ImageView) views.findViewById(R.id.bt_list_play);
            ImageView stop = (ImageView) views.findViewById(R.id.bt_list_stop);
            LinearLayout normal = (LinearLayout) views.findViewById(R.id.normal);
            LinearLayout luoli = (LinearLayout) views.findViewById(R.id.luoli);
            LinearLayout dashu = (LinearLayout) views.findViewById(R.id.dashu);
            LinearLayout jingsong = (LinearLayout) views.findViewById(R.id.jingsong);
            LinearLayout gaoguai = (LinearLayout) views.findViewById(R.id.gaoguai);
            LinearLayout kongling = (LinearLayout) views.findViewById(R.id.kongling);

            normal.setOnClickListener(this);
            luoli.setOnClickListener(this);
            dashu.setOnClickListener(this);
            jingsong.setOnClickListener(this);
            gaoguai.setOnClickListener(this);
            kongling.setOnClickListener(this);

            filename.setText(listFile[postion]);
            plays.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    try {
                        myPlayer.reset();
                        myPlayer.setDataSource(path + "/" + listFile[postion]);

                        if (!myPlayer.isPlaying()) {

                            myPlayer.prepare();
                            myPlayer.start();
                        } else {
                            myPlayer.pause();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (myPlayer.isPlaying()) {
                        myPlayer.stop();
                    }
                }
            });
            return views;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.normal:
                    type = Utils.MODE_NORMAL;
                    break;
                case R.id.luoli:
                    type = Utils.MODE_LUOLI;
                    break;
                case R.id.dashu:
                    type = Utils.MODE_DASHU;
                    break;
                case R.id.jingsong:
                    type = Utils.MODE_JINGSONG;
                    break;
                case R.id.gaoguai:
                    type = Utils.MODE_GAOGUAI;
                    break;
                case R.id.kongling:
                    type = Utils.MODE_KONGLING;
                    break;
            }
            recordPath =path+"/"+listFile[pos];
            mp3Path=recordPath.substring(0,recordPath.indexOf("."))+".mp3";

            Log.i("cx amrPath",recordPath);
            Log.i("cx mp3Path",mp3Path);
//            ChangeAudioFormat.changeToMp3(recordPath,mp3Path);
            playThread =new PlayThread();
            fixedThreadPool.execute(playThread);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                final EditText filename = new EditText(this);
                AlertDialog.Builder alerBuidler = new AlertDialog.Builder(this);
                alerBuidler
                        .setTitle("是否开始录音")
                        .setView(filename)
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        String text = filename.getText().toString();
                                        try {
                                            paths = path
                                                    + "/"
                                                    + text
                                                    + new SimpleDateFormat(
                                                    "yyyyMMddHHmmss").format(System
                                                    .currentTimeMillis())
                                                    + ".mp3";
                                            saveFilePath = new File(paths);
                                            Log.i("cx paths",paths);
                                            Toast.makeText(RecordActivity.this ,paths, Toast.LENGTH_SHORT).show();
                                            myRecorder.setOutputFile(saveFilePath
                                                    .getAbsolutePath());
                                            saveFilePath.createNewFile();
                                            myRecorder.prepare();
                                            myRecorder.start();
                                            start.setText("开始录音");
                                            start.setEnabled(false);
                                            aler.dismiss();
                                            File files = new File(path);
                                            listFile = files.list();
                                            showRecord.notifyDataSetChanged();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                aler = alerBuidler.create();
                aler.setCanceledOnTouchOutside(false);
                aler.show();
                break;
            case R.id.stop:
                if (saveFilePath.exists() && saveFilePath != null) {
                    myRecorder.stop();
                    myRecorder.release();
                    new AlertDialog.Builder(this)
                            .setTitle("是否停止录音")
                            .setPositiveButton("确定", null)
                            .setNegativeButton("取消",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            saveFilePath.delete();
                                            File files = new File(path);
                                            listFile = files.list();
                                            showRecord.notifyDataSetChanged();
                                        }
                                    }).show();

                }
                start.setText("开始录音");
                start.setEnabled(true);
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        if (myPlayer.isPlaying()) {
            myPlayer.stop();
            myPlayer.release();
        }
        myPlayer.release();
        myRecorder.release();
        FMOD.close();
        super.onDestroy();
    }

}
