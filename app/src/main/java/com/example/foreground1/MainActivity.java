package com.example.foreground1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CODE = 999;
    Intent foregroundServiceIntent;
//    private ArrayList<CallLogModel> callLogModelArrayList;
//    public static String userNumber;
//    public String str_number, str_contact_name, str_call_type, str_call_full_date,
//            str_call_date, str_call_time, str_call_time_formatted, str_call_duration;
    String[] appPermissions = {
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.READ_PHONE_STATE
    };
//    String past_full_date_time;
//    -----------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Prashanth");
        if(checkPermission()){
            foregroundServiceIntent = new Intent(this,ForegroundService.class);
            getApplicationContext().startService(foregroundServiceIntent);
            System.out.println("Permissiongd done");

            String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";
            Cursor cursor = getContentResolver().query(
                    CallLog.Calls.CONTENT_URI,
                    null,
                    null,
                    null,
                    sortOrder);

            FetSendData sendingCursorToFetSendData = new FetSendData();
            sendingCursorToFetSendData.getingCursor(cursor);
        }else {
            System.out.println("Faillterefsejkfhsjkdf-----------");
        }
    }

    private boolean checkPermission() {
//        ActivityCompat.requestPermissions(this,new String[](Manifest.permission.READ_CALL_LOG),PackageManager.PERMISSION_GRANTED);
        List<String> listPermissionNeeded = new ArrayList<>();
        for (String item : appPermissions) {
            if (ContextCompat.checkSelfPermission(this, item) != PackageManager.PERMISSION_GRANTED)
                listPermissionNeeded.add(item);
        }
        //Ask for non-granted permissions
        if (!listPermissionNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionNeeded.toArray(new String[listPermissionNeeded.size()]),
                    PERMISSIONS_REQUEST_CODE);
            return false;
        }
        //App has all permissions. Proceed ahead
        return true;
    }

}