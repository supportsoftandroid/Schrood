package com.food.schrood.network;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FCMAPIService {
    @Headers({"Content-Type:application/json", "Authorization: key=${SERVER_KEY}"})
    @POST("fcm/send")
    Call<ResponseBody> sendNotification(@Body JsonObject body);
}
