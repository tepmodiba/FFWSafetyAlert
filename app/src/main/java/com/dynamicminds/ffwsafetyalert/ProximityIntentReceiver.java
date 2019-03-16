package com.dynamicminds.ffwsafetyalert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import static com.google.android.gms.cast.CastRemoteDisplayLocalService.startService;

public class ProximityIntentReceiver extends BroadcastReceiver {
    public ProximityIntentReceiver(){

    }
    @Override
    public void onReceive(Context context, Intent intent) {

        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Boolean entering = intent.getBooleanExtra(key, false);
        if (entering) {
            String body = "Please be alert you are approaching hijacking spot area";
            NotificationHandler nf = new NotificationHandler(context);
            context.startService(new Intent(context, Speaker.class));
            TextToSpeech tts = Speaker.mTts;

            nf.show("Alert", body);
            if(tts != null) {
                if(!tts.isSpeaking())
                    tts.speak(body, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        else {

            context.startService(new Intent(context, Speaker.class));
            TextToSpeech tts = Speaker.mTts;

            if(tts != null) {
                if(!tts.isSpeaking())
                    tts.speak("You are safely driving away from hotspot area", TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }
}
