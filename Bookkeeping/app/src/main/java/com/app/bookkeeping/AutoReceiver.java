package com.app.bookkeeping;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;


public class AutoReceiver extends BroadcastReceiver {
    private static final int NOTIFICATION_FLAG = 1;

    @SuppressLint("NewApi")
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("VIDEO_TIMER")) {
            String id="channel_1";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel channel = new NotificationChannel(id, "123", importance);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                    new Intent(context, MainActivity.class), 0);
            // 通过Notification.Builder来创建通知，注意API Level
            // API16之后才支持
            NotificationManager manager = (NotificationManager) context
                    .getSystemService(NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
            Log.w("AutoReceiver:","got it");
            Notification notify = new Notification.Builder(context,id)
                    .setSmallIcon(R.drawable.bank)
                    .setTicker("TickerText:" + "记账规划生活:")
                    .setContentTitle("进行每日的记账总结吧～")
                    .setContentText("进行每日的记账总结吧～")
                    .setContentIntent(pendingIntent).setNumber(1).build(); // 需要注意build()是在API
            // level16及之后增加的，API11可以使用getNotificatin()来替代
            notify.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。
            // 在Android进行通知处理，首先需要重系统哪里获得通知管理器NotificationManager，它是一个系统Service。

            manager.notify(NOTIFICATION_FLAG, notify);// 步骤4：通过通知管理器来发起通知。如果id不同，则每click，在status哪里增加一个提示
        }
    }

}

