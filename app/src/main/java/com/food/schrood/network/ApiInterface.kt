package com.food.schrood.network


import com.food.schrood.model.*
import com.food.schrood.utility.Constants
import com.food.schrood.utility.Constants.SERVER_KEY
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


public interface ApiInterface {

    /* @GET(Constants.API_WELCOME_SCREEN)
     fun getWelcome(): Call<WelcomeResponse>*/

    @Multipart
    @POST(Constants.API_SIGNUP)
    fun signup(
        @Part("name") firstname: RequestBody,
        @Part("email") email: RequestBody?,
        @Part("country_code") countryCode: RequestBody?,
        @Part("country_name_code") countryNameCode: RequestBody?,
        @Part("mobile_number") mobile_number: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("device_type") device_type: RequestBody,
        @Part("push_token") device_id: RequestBody,
        @Part file_1: MultipartBody.Part?,

        ): Call<LoginResponse>?


    @Multipart
    @POST(Constants.API_EDIT_PROFILE)
    fun editProfile(
        @Header("Authorization") authToken: String,
        @Part("name") firstname: RequestBody,
        @Part("email") email: RequestBody?,
        @Part("country_code") countryCode: RequestBody?,
        @Part("country_name_code") countryNameCode: RequestBody?,
        @Part("mobile_number") mobile_number: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("device_type") device_type: RequestBody,
    ): Call<LoginResponse>?


    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_LOGIN)
    fun userLoginRow(
        @Body body: JsonObject,
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST(Constants.API_LOGIN)
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("is_role") is_role: String,
        @Field("device_type") device_type: String,
        @Field("push_token") push_token: String?
    ): Call<LoginResponse>


    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_FORGOT_PASS)
    fun request_otp(
        @Body email: JsonObject
    ): Call<CommonResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_VERIFY_OTP)
    fun verify_otp(
        @Body body: JsonObject
    ): Call<CommonResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_NEW_PASSWORD)
    fun reset_password(
        @Body body: JsonObject
    ): Call<CommonResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_PROFILE_DETAILS)
    fun getProfile(
        @Header("Authorization") authorization: String?
    ): Call<LoginResponse>

    @Multipart()
    @POST(Constants.API_UPLOAD_PROFILE_IMAGE)
    fun updateProfileImage(
        @Header("Authorization") authorization: String?,
        @Part file_1: MultipartBody.Part?,

        ): Call<LoginResponse>


    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_SEARCH_FILTER)
    fun searchFilter(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>


    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_ADD_CARD)
    fun addCard(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CardResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_DELETE_CARD)
    fun deleteCard(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CardResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_SAVED_CARD)
    fun getCardList(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CardListResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_ADD_RATTING)
    fun addRatting(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)

    @POST(Constants.API_RATTING_LIST)
    fun getRatting(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>

    @POST(Constants.API_NOTIFICATION_LIST)
    fun getNotifications(
        @Header("Authorization") authorization: String?
    ): Call<CommonResponse>

    @POST(Constants.API_NOTIFICATION_READ)
    fun readNotifications(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>

    @POST(Constants.API_NOTIFICATION_DELETE)
    fun deleteNotifications(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_CHANGE_PASSWORD)
    fun change_password(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>


    @GET(Constants.API_PRIVACY_POLICY)
    fun getPrivacyPolicy(): Call<TermsRespons>

    @GET(Constants.API_TERMS_CONDITION)
    fun getTermsCond(): Call<TermsRespons>

    @GET(Constants.API_ABOUT_APP)
    fun getAboutApp(): Call<TermsRespons>

    /* @GET(Constants.API_FAQ)
      fun getFAQPolicy(  ): Call<FaqResponse>
  */
    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_HELP_CENTER)
    fun helpCenter(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>


    /* @Headers("Content-Type:application/json")*/

    @Headers("Authorization: key=${SERVER_KEY}", "Content-Type: application/json")
    /*   @Headers(
           {
               "Content-Type:application/json",
               "Authorization:key=AAAAlfLYquE:APA91bEot3szy7Fxxj6npKaeyazjWrOR_gZGxhMMS51qNviPVMaMcQekj0xcSwdyFmxfEzv-e7L7Ql9XJ-sR0TD_UYNpM-ut-i35-OnnSDzJJaSnTLadBI5mFVsjgI1pUhtbLQt9QBpY"
           }
       )*/
    @POST("fcm/send")
    fun sendNotification(
        @Header("Authorization") server_key: String,
        @Body body: Sender
    ): Call<ResponseBody>

}