package com.example.foreground1;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FetSendData extends Application {
    String past_full_date_time;
    private ArrayList<CallLogModel> callLogModelArrayList = new ArrayList<>();
    public static String userNumber;
    public String str_number;
    public String str_contact_name;
    public String str_call_type;
    public String str_call_full_date;
    public SimpleDateFormat str_call_date;
    public String str_call_time;
    public String str_call_time_formatted;
    public String str_call_duration;
    public Date sd;
    Context context;

    public FetSendData(Context context) {
        this.context = context.getApplicationContext();
    }
    public int number()
    {
        return 1;
    }

    @SuppressLint("Range")
    public ArrayList<CallLogModel> fetchCallLogs() {
        CallLogModel callLogItem;
        String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";
        Cursor cursor = context.getContentResolver().query(
                CallLog.Calls.CONTENT_URI,
                null,
                null,
                null,
                sortOrder);

        System.out.println("Cursor ======== "+cursor.getCount());
//        Cursor cursor = getcur();
        while (cursor.moveToNext()) {
            str_call_full_date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                str_number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                str_contact_name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                str_contact_name = str_contact_name == null || str_contact_name.equals("") ? "Unknown" : str_contact_name;
                str_call_type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
//            Date d = new Date(str_call_full_date);
                System.out.println("sffln--" + str_call_full_date);
//                SimpleDateFormat sd = new SimpleDateFormat(str_call_full_date);

//                System.out.println("date and tine " + sd);

                str_call_duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                userNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_ID));
                str_call_duration = DurationFormat(str_call_duration);

                switch (Integer.parseInt(str_call_type)) {
                    case CallLog.Calls.INCOMING_TYPE:
                        str_call_type = "Incoming";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        str_call_type = "Outgoing";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        str_call_type = "Missed";
                        break;

                    case CallLog.Calls.REJECTED_TYPE:
                        str_call_type = "Rejected";
                        break;

                    default:
                        str_call_type = "NA";
                }

            System.out.println("prasha--"+str_number+" "+str_contact_name+" "+sd+" "+str_call_type+" "+userNumber+" "+str_call_duration);
            callLogItem = new CallLogModel(str_number, str_contact_name, str_call_type,
                    sd, null, str_call_duration,userNumber);
            callLogModelArrayList.add(callLogItem);
        }
//        for (CallLogModel l:callLogModelArrayList) {
//            System.out.println("calllogitem-----" + l.getCallPhoneNumber() + " " + l.getContactName() + " " + l.getCallTypeCode()
//                    + " " + l.getCallDate() + " " + l.getCallTime() + " " +
//                    l.getCallDuration() + " " + l.getUserNumber());
//        }
        return callLogModelArrayList;
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("Range")
    public List<CallLogModel> fetchsecondCallLogs() {

        Date presentTime = new Date();
        System.out.println(presentTime);
        Calendar cal = Calendar.getInstance();
// remove next line if you're always using the current time.
        cal.setTime(presentTime);
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
        CallLogModel callLogItem;

        //looping through the cursor to add data into arraylist
        while (cursor.moveToNext()) {
            str_call_full_date = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
            System.out.println("Strign full date and tiem ="+str_call_full_date);
            Date new_record = new Date(Long.parseLong(str_call_full_date));
            System.out.println("Datefgej=="+new_record);
            System.out.println("Minutes_15_Back=="+Minutes_15_Back);

                str_number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                str_contact_name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                str_contact_name = str_contact_name == null || str_contact_name.equals("") ? "Unknown" : str_contact_name;
                str_call_type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
                str_call_duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                userNumber = cursor.getString(cursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_ID));
                Date sd = new Date(Long.parseLong(str_call_full_date));

                str_call_duration = DurationFormat(str_call_duration);

                switch (Integer.parseInt(str_call_type)) {
                    case CallLog.Calls.INCOMING_TYPE:
                        str_call_type = "Incoming";
                        break;
                    case CallLog.Calls.OUTGOING_TYPE:
                        str_call_type = "Outgoing";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        str_call_type = "Missed";
                        break;

                    case CallLog.Calls.REJECTED_TYPE:
                        str_call_type = "Rejected";
                        break;

                    default:
                        str_call_type = "NA";
                }

                callLogItem = new CallLogModel(str_number, str_contact_name, str_call_type,
                        sd, null, str_call_duration,userNumber);

            if(new_record.after(Minutes_15_Back)) {
                callLogModelArrayList.add(callLogItem);
            }


        }

        return callLogModelArrayList;
    }


    Retrofit retrofit;
    public String sendDataToServer(List<CallLogModel> callLogItem) {
        System.out.println("Sendkkkkkkkkk");
        for (CallLogModel l:callLogItem) {
            System.out.println("sendinggg-----" + l.getCallPhoneNumber() + " " + l.getContactName() + " " + l.getCallTypeCode()
                    + " " + l.getCallDate() + " " + l.getCallTime() + " " +
                    l.getCallDuration() + " " + l.getUserNumber());
        }
        callLogModelArrayList.clear();
        return "sucsses";
//        System.out.println("cdate="+callLogItem.getCallDate()+";ctime="+callLogItem.getCallTime()+";phn="+callLogItem.get+";cname="+callLogItem.getContactName()+";ctype="+callLogItem.getCallType()+";userNo: "+callLogItem.getUserNumber());
//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://apwc.tectoro.com/rest/plugins/calllog/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//
//        Call<CallLogModel> call = apiInterface.postData((CallLogModel) callLogItem);
//        call.enqueue(new Callback<CallLogModel>() {
//            @Override
//            public void onResponse(Call<CallLogModel> call, Response<CallLogModel> response) {
//                if(response.isSuccessful())
//                {
//                    Log.d("Code: ", String.valueOf(response.code()));
////                    Toast.makeText(getApplicationContext(), "on success"+response.code(), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                CallLogModel callLogRequest1 = response.body();
//
//                Log.d("Callog","list" + callLogRequest1.getCallDate());
//            }
//
//            @Override
//            public void onFailure(Call<CallLogModel> call, Throwable t) {
//
//                Log.d("Code: ",t.getMessage());
////                Toast.makeText(getApplicationContext(), "on failure"+t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//

    }

//    =============================================
    private String getFormatedDateTime(String dateStr, String strInputFormat, String strOutputFormat) {
    String formattedDate = dateStr;
    DateFormat inputFormat = new SimpleDateFormat(strInputFormat, Locale.getDefault());
    DateFormat outputFormat = new SimpleDateFormat(strOutputFormat, Locale.getDefault());
    Date date = null;
    try {
        date = inputFormat.parse(dateStr);
    } catch (ParseException e) {
    }

    if (date != null) {
        formattedDate = outputFormat.format(date);
    }
    return formattedDate;
}
    private String DurationFormat(String duration) {
        String durationFormatted=null;
        if(Integer.parseInt(duration) < 60){
            durationFormatted = duration+" sec";
        }
        else{
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

//    String durationFormatted=null;
//        if(Integer.parseInt(duration) < 60){
//        durationFormatted = duration+" sec";
//        }
//        else{
//        int min = Integer.parseInt(duration)/60;
//        int sec = Integer.parseInt(duration)%60;
//
//        if(sec==0)
//        durationFormatted = min + " min" ;
//        else
//        durationFormatted = min + " min " + sec + " sec";
//
//        }
//        return durationFormatted;