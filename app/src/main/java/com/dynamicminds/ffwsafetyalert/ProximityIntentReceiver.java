package com.dynamicminds.ffwsafetyalert;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class ProximityIntentReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("INTENT: ", "Got Intent");
        String key = LocationManager.KEY_PROXIMITY_ENTERING;
        Boolean entering = intent.getBooleanExtra(key, false);
        if (entering) {
            Toast.makeText(context,"entering!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "exiting!", Toast.LENGTH_SHORT).show();
        }
    }
}
