package com.example.a;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlertBrr extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Work work = (Work) intent.getSerializableExtra("work");

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "duong")
                .setContentTitle(work.getTitle())
                .setContentText(work.getContent())
                .setContentInfo(work.getDate())
                .setSmallIcon(R.drawable.done);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(100, builder.build());
    }
}
