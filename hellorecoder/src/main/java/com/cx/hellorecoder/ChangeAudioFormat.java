package com.cx.hellorecoder;

import java.io.File;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;

/**
 * Created by Administrator on 2017/10/25.
 */

public class ChangeAudioFormat {

    public static void changeToMp3(String sourcePath ,String targetPath){
        File source =new File(sourcePath);
        File target =new File(targetPath);


        AudioAttributes audio =new AudioAttributes();
        Encoder encoder =new Encoder();
        audio.setCodec("libmp3lame");
        EncodingAttributes attrs =new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);

        try {
            if(source!=null && target!=null){
                encoder.encode(source,target,attrs);
            }
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }
}
