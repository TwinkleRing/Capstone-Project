package com.example.capstone_ui_1.Alarm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.capstone_ui_1.MainActivity;
import com.example.capstone_ui_1.R;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    String CHANNEL_ID = "Capstone";

    @Override
    public void onReceive(Context context, Intent intent) {
        int req = intent.getExtras().getInt("req", 0);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, req, new Intent(context, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.alarm_clock2).setContentTitle("알단지").setContentText("강의가 곧 시작합니다.")
                .setWhen(Calendar.getInstance().getTimeInMillis())
                .setContentIntent(pendingIntent).setAutoCancel(true);

        notificationManager.notify(0, builder.build());
    }

}