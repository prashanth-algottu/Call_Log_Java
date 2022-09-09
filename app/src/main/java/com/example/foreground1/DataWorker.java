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

        public DataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }


//    public DataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
//        super(context, workerParams);
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Result doWork() {
        List<CallLogModel> callLogsArrayList = new ArrayList<>();
        Context context = getApplicationContext();
        FetSendData fetSendData = new FetSendData(context);
        MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.pikachu);
        mediaPlayer.start();
        final Date date = new Date();
        System.out.println("Dateeeeeeeeeee"+ date);

        sp = context.getSharedPreferences("first",Context.MODE_PRIVATE);
        String z = sp.getString("nam",null);
        System.out.println("ZZZZZZZZZZZZZZZZZ__  " + z);

        if(z==null){
            System.out.println("First time --------------");
            callLogsArrayList = fetSendData.fetchCallLogs();

        }else {
            System.out.println("Second time -------------");
            callLogsArrayList = fetSendData.fetchsecondCallLogs();
        }
        z = fetSendData.sendDataToServer(callLogsArrayList);
        System.out.println("Donr");
        if(z!=null){
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("nam","sucees");
        editor.commit();
        }





//        if(firstTime==1){
//            System.out.println("If condition true fetAll");
//            firstTime =10;
//
//
//        }else if(firstTime!=1){
//            System.out.println("If condition false");
//        }
        
        // Shared Prefernce
//        sp = context.getSharedPreferences("first",Context.MODE_PRIVATE);
//        String fir = "D";
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("name",fir);
//        editor.commit();
//        f1 = sp.getString("name","");
//        System.out.println("please find this-----"+f1);







// First time fetch all the records
//        FetSendData fetSendClass = new FetSendData(context);
//        l = fetSendClass.fetchCallLogs();
//
//        for (CallLogModel c:l) {
//
//        }
//            fetSendClass.sendDataToServer(l);

////        boolean firstTime = mPreferences.getBoolean("firstTime", false);
//        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(context);
//        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
//
//
////        SharedPreferences.Editor editor = mPreferences.edit();
////        editor.putBoolean("firstTime", true);
////        editor.commit();
//
//        if (isFirstRun) {
//            System.out.println("First run Bolck");
////            fetSendClass.getingCursor(cursor);
//            l = fetSendClass.fetchCallLogs();
//            System.out.println("1........");
//
////            Toast.makeText(context, "First time hitted", Toast.LENGTH_SHORT).show();
//        }else {
//                            System.out.println("Else Block");
////            Toast.makeText(context, "--second time hitted", Toast.LENGTH_SHORT).show();
//            l = fetSendClass.fetchsecondCallLogs();
//            System.out.println("2........");
//
////            Toast.makeText(context, "second time hitted", Toast.LENGTH_SHORT).show();
//        }


        return Result.success();
    }

}
