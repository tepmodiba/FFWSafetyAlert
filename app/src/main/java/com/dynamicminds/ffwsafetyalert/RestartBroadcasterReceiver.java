package com.dynamicminds.ffwsafetyalert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class RestartBroadcasterReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        context.startService(new Intent(context, SafetyAlert.class));;
    }
}
