package com.dynamicminds.ffwsafetyalert;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

public class Speaker extends Service implements TextToSpeech.OnInitListener{

    public static TextToSpeech mTts;

    public Speaker(){
    }

    @Override
    public void onCreate(){
        mTts = new TextToSpeech(this, this);
        super.onCreate();
    }
    @Override
    public void onStart(Intent intent, int startId){

        super.onStart(intent, startId);
    }
    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
            } else {
                mTts.setSpeechRate(0.92f);
            }
        } else {
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
    }

}