package com.example.foreground1;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("log-list")
    Call<CallLogModel> postData(@Body CallLogModel callLogItemList);
}
