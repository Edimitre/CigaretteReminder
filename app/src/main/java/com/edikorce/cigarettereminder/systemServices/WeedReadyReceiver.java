package com.edikorce.cigarettereminder.systemServices;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.edikorce.cigarettereminder.R;
import com.edikorce.cigarettereminder.activities.MainActivity;

public class WeedReadyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        notify(context, "WeedReminder", "Behu Dru Prap");

    }

    public void notify(Context context, String title, String message){


        long[] vibration = {500,1000};

        Intent mainActivity = new Intent(context, MainActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pi = PendingIntent.getActivity(context, 0, mainActivity, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(context, "cigarette_reminder_channel");
        notificationbuilder.setSmallIcon(R.drawable.weed_button);
        notificationbuilder.setContentIntent(pi);
        notificationbuilder.setContentTitle(title);
        notificationbuilder.setContentText(message);
        notificationbuilder.setVibrate(vibration);
        notificationbuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationbuilder.setAutoCancel(true);



        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notificationbuilder.build());

    }
}