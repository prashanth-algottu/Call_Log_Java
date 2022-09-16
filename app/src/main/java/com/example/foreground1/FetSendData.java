package com.example.foreground1;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import androidx.annotation.RequiresApi;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import retrofit2.Retrofit;

public class FetSendData extends Application {
    private ArrayList<CallLogModel> callLogModelArrayList = new ArrayList<>();

    public String str_number,str_contact_name,str_call_type,str_call_full_date,str_call_duration,userNumber;
    Context context;

    public FetSendData(Context context) {
        this.context = context.getApplicationContext();
    }

    @SuppressLint("Range")
    public ArrayList<CallLogModel> fetchCallLogs() {

        String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";
        // this is the cursor query
        Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                sortOrder);

        System.out.println("Cursor count "+cursor.getCount());
        // Looping the cursor we will get call logs from the mobile
        while (cursor.moveToNext()) {
                str_call_full_date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                str_number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                str_contact_name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                str_contact_name = str_contact_name == null || str_contact_name.equals("") ? "Unknown" : str_contact_name;
                str_call_type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                str_call_duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                userNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_ID));
                str_call_duration = DurationFormat(str_call_duration);
                Date dateAndTime = new Date(Long.parseLong(str_call_full_date));
            switch (Integer.parseInt(str_call_type)) {
                    case CallLog.Calls.INCOMING_TYPE:
                        str_call_type = "INCOMING";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        str_call_type = "OUTGOING";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        str_call_type = "MISSED";
                        break;

                    case CallLog.Calls.REJECTED_TYPE:
                        str_call_type = "REJECTED";
                        break;

                    default:
                        str_call_type = "NA";
                }

//            System.out.println("prasha--"+str_number+" "+str_contact_name+" "+dateAndTime+" "+str_call_type+" "+userNumber+" "+str_call_duration);
            CallLogModel callLogItem = new CallLogModel(str_number, str_contact_name, str_call_type,
                    dateAndTime,  str_call_duration,userNumber);
            callLogModelArrayList.add(callLogItem);
        }
//        for (CallLogModel l:callLogModelArrayList) {
//            System.out.println("calllogitem-----" + l.getCallPhoneNumber() + " " + l.getContactName() + " " + l.getCallTypeCode()
//                    + " " + l.getCallDate() + " " +  " " +
//                    l.getCallDuration() + " " + l.getUserNumber());
//        }
        return callLogModelArrayList;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("Range")
    public List<CallLogModel> fetchUpdatedCallLogs() {
        // Taking present time
        Date presentTime = new Date();
        System.out.println(presentTime);
        Calendar cal = Calendar.getInstance();
        cal.setTime(presentTime);
        // Minus 15 minutes from presentTime
        cal.add(Calendar.MINUTE, -15);
        Date Minutes_15_Back = cal.getTime();
        System.out.println(Minutes_15_Back);

        String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                sortOrder);
        //looping through the cursor to add data into arraylist
        while (cursor.moveToNext()) {
            str_call_full_date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
            // Taking new_record date and time and compare this time with the past 15 minutes time
            Date new_record = new Date(Long.parseLong(str_call_full_date));
                str_number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                str_contact_name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                str_contact_name = str_contact_name == null || str_contact_name.equals("") ? "Unknown" : str_contact_name;
                str_call_type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                str_call_duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                userNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_ID));
                // Converting String to Date
                Date dateAndTime = new Date(Long.parseLong(str_call_full_date));
                str_call_duration = DurationFormat(str_call_duration);

                switch (Integer.parseInt(str_call_type)) {
                    case CallLog.Calls.INCOMING_TYPE:
                        str_call_type = "INCOMING";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        str_call_type = "OUTGOING";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        str_call_type = "MISSED";
                        break;

                    case CallLog.Calls.REJECTED_TYPE:
                        str_call_type = "REJECTED";
                        break;

                    default:
                        str_call_type = "NA";
                }

            CallLogModel callLogItem = new CallLogModel(str_number, str_contact_name, str_call_type,
                        dateAndTime,str_call_duration,userNumber);

            // Here I am doing sorting based on the time and date(Date)
            if(new_record.after(Minutes_15_Back)) {
                // Adding here are Updated call logs
                callLogModelArrayList.add(callLogItem);
            }
        }
        return callLogModelArrayList;
    }

    public String sendDataToServer(List<CallLogModel> callLogItem) {
        for (CallLogModel l:callLogItem) {
            System.out.println("sendinggg-----" + l.getCallPhoneNumber() + " " + l.getContactName() + " " + l.getCallTypeCode()
                    + " " + l.getCallDate() + " " + " " +
                    l.getCallDuration() + " " + l.getUserNumber());
        }

        // Here I need to send call logs to the API

        callLogModelArrayList.clear();
        return "sucess";
    }

//    =============================================
    private String DurationFormat(String duration) {
        String durationFormatted=null;
        if(Integer.parseInt(duration) < 60){
            durationFormatted = duration+" sec";
        } else{
            int min = Integer.parseInt(duration)/60;
            int sec = Integer.parseInt(duration)%60;
            if(sec==0)
                durationFormatted = min + " min" ;
            else
                durationFormatted = min + " min " + sec + " sec";
        }
        return durationFormatted;
    }
}
