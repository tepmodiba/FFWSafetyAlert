package com.dynamicminds.ffwsafetyalert;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

public class NotificationHandler {

    Context context;
    public NotificationHandler(Context context){
        this.context = context;
    }

    public void show(String title, String body){
        NotificationManager notificationManager;

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, "notify_001");
        Intent ii = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.icon);
        builder.setContentTitle(title);
        builder.setContentText(body);
        builder.setPriority(Notification.PRIORITY_MAX);
        builder.setStyle(bigText);

        notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "YOUR_CHANNEL_ID";
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
            builder.setChannelId(channelId);
        }

        notificationManager.notify(0, builder.build());
    }
}
