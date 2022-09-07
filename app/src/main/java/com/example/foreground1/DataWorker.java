package com.example.foreground1;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataWorker extends Worker {
    SharedPreferences mPreferences;

    public DataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        List<CallLogModel> l=null;
        Context context = getApplicationContext();
        MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.pikachu);
        mediaPlayer.start();
        System.out.println("Before MainActivity----");
        FetSendData ma = new FetSendData();

//        boolean firstTime = mPreferences.getBoolean("firstTime", false);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(context);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);


//        SharedPreferences.Editor editor = mPreferences.edit();
//        editor.putBoolean("firstTime", true);
//        editor.commit();

        if (isFirstRun) {
//            Toast.makeText(context, "--First time hitted", Toast.LENGTH_SHORT).show();
            l = ma.fetchCallLogs();
            System.out.println("1........");

//            Toast.makeText(context, "First time hitted", Toast.LENGTH_SHORT).show();
        }else {
//            Toast.makeText(context, "--second time hitted", Toast.LENGTH_SHORT).show();
//            l = ma.fetchsecondCallLogs();
            System.out.println("2........");

//            Toast.makeText(context, "second time hitted", Toast.LENGTH_SHORT).show();
        }
            ma.sendDataToServer(l);
        System.out.println("Doing work........");

        return null;
    }

}