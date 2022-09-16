package com.example.foreground1;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ForegroundService extends Service {
    private final int NOTIFICATION_ID=1;
    private final String CHANNEL_ID="100";
    public ForegroundService() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        startForeground(NOTIFICATION_ID,showNotification("This context"));
    }
    private Notification showNotification(String this_i_context) {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(
                    new NotificationChannel(CHANNEL_ID,"Foreground Notification",
                            NotificationManager.IMPORTANCE_HIGH));
        }
        CharSequence content="FOREGROUND RUNNING ";
        return new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("RUNNING SOME APP IN BACKGROUND")
                .setContentText(content)
                .setOnlyAlertOnce(true)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .build();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(context, "Starting service....", Toast.LENGTH_SHORT).show();

        PeriodicWorkRequest periodicSendDataWork =
                new PeriodicWorkRequest.Builder(DataWorker.class, 15, TimeUnit.MINUTES).build();

        WorkManager workManager = WorkManager.getInstance(this);
        workManager.enqueue(periodicSendDataWork);
        return super.onStartCommand(intent, flags, startId);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
