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

     @GET(Constants.API_WELCOME_SCREEN)
     fun getWelcome(): Call<WelcomeResponse>

    @Multipart
    @POST(Constants.API_SIGNUP)
    fun signup(
        @Body body: JsonObject,
        ): Call<LoginResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_LOGIN)
    fun userLogin(
        @Body body: JsonObject,
    ): Call<LoginResponse>

    @Multipart
    @POST(Constants.API_EDIT_PROFILE)
    fun editProfile(
        @Header("Authorization") authToken: String,
        @Part("name") firstname: RequestBody,
        @Part("email") email: RequestBody?,
        @Part("country_code") countryCode: RequestBody?,
        @Part("mobile_number") mobile_number: RequestBody?,
        @Part("password") password: RequestBody?,
        @Part("device_type") device_type: RequestBody,
    ): Call<LoginResponse>

    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_FORGOT_PASS)
    fun request_otp(
        @Body body: JsonObject
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
        @Part file_1: MultipartBody.Part?, ): Call<LoginResponse>


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
    fun getPrivacyPolicy(): Call<TermsResponse>

    @GET(Constants.API_TERMS_CONDITION)
    fun getTermsCond(): Call<TermsResponse>

    @GET(Constants.API_ABOUT_APP)
    fun getAboutApp(): Call<TermsResponse>

    /* @GET(Constants.API_FAQ)
      fun getFAQPolicy(  ): Call<FaqResponse>
  */
    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_HELP_CENTER)
    fun helpCenter(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<CommonResponse>



    @Headers(Constants.ACCEPT_JSON_HEADER)
    @POST(Constants.API_EDIT_PROFILE)
    fun updateProfile(
        @Header("Authorization") authorization: String?,
        @Body body: JsonObject
    ): Call<LoginResponse>

   /* @Multipart()
    @POST(Constants.API_EDIT_PROFILE)
    fun updateProfile(
        @Header("Authorization") authorization: String?,
        @Part("first_name") firstname: RequestBody,
        @Part("last_name") lastname: RequestBody?,
        @Part("country_code") countryCode: RequestBody?,
        @Part("mobile") mobile_number: RequestBody?,
        @Part("address") address: RequestBody?,
        @Part("landmark") landmark: RequestBody?,
        @Part("suburb") city: RequestBody?,
        @Part("state") state: RequestBody?,
        @Part("country") country: RequestBody?,
        @Part("country_name_code") country_name_code: RequestBody?,
        @Part("zipcode") zipcode: RequestBody?,
        @Part("latitude") latitude: RequestBody?,
        @Part("longitude") longitude: RequestBody?,
    ): Call<LoginResponse>*/
}