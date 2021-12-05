package com.edikorce.cigarettereminder.systemServices;


import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.edikorce.cigarettereminder.activities.MainActivity;
import com.edikorce.cigarettereminder.R;


public class CigaretteReadyReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {

        notify(context, "CigaretteReminder", "ju mund ta ndizni nje cigare");
    }


    public void notify(Context context, String title, String mesagge){


        Intent mainActivity = new Intent(context, MainActivity.class);
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pi = PendingIntent.getActivity(context, 0, mainActivity, PendingIntent.FLAG_UPDATE_CURRENT);



        NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(context, "cigarette_reminder_channel");
        notificationbuilder.setSmallIcon(R.drawable.ic_packet);
        notificationbuilder.setContentIntent(pi);
        notificationbuilder.setContentTitle(title);
        notificationbuilder.setContentText(mesagge);
        notificationbuilder.setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 });
        notificationbuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationbuilder.setAutoCancel(true);



        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1, notificationbuilder.build());

    }

}
