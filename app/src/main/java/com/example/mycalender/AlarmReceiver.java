package com.example.mycalender;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.net.URISyntaxException;

public class AlarmReceiver extends BroadcastReceiver {


    private static final int MY_NOTIFICATION_ID=1;
    NotificationManager notificationManager;
    Notification myNotification;

    /* private final String myBlog = "http://android-er.blogspot.com/"; */

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Alarm received!", Toast.LENGTH_LONG).show();


       /* Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
        myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                myIntent,
                0);*/


        myNotification = new NotificationCompat.Builder(context,"gfhf")
                .setContentTitle("Event Reminder!!!")
                .setContentText("")
                .setTicker("Notification!")
                .setWhen(System.currentTimeMillis())
                //.setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.alarm)
                .build();

        notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    }
}
