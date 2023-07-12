package com.food.schrood.network.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.food.schrood.R
import com.food.schrood.model.LoginResponse
import com.food.schrood.network.RetrofitClient
import com.food.schrood.utility.Constants
import com.food.schrood.utility.DialogManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.StaticData.Companion.prepareFilePartFromUri
import com.food.schrood.utility.StaticData.Companion.showToast
import com.google.gson.JsonObject

class ProfileRepository(mContext: Context) {
    val modelRes = MutableLiveData<LoginResponse>()
    val progressDialog: DialogManager
    var context: Context

    init {
        context = mContext
        progressDialog = DialogManager(context)

    }

    fun getProfile(
        userToken: String,
        type: String?,
        imageFilePath: String?
    ): MutableLiveData<LoginResponse> {
        var callApiService: Call<LoginResponse>? = null
        if (type == "get") {
            callApiService = RetrofitClient.apiInterface.getProfile(userToken)
            return callApi(callApiService)
        } else {
            val imageFile: MultipartBody.Part? =
                imageFilePath?.let { prepareFilePartFromUri("image", it) }
            callApiService = RetrofitClient.apiInterface.updateProfileImage(
                userToken,
                imageFile
            )
            return callApi(callApiService)
        }

    }

    fun updateProfile(
        userToken: String,
        name: String,   country_code: String, mobile: String, email: String): MutableLiveData<LoginResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("mobile", mobile)
        jsonObject.addProperty("country_code", country_code)
        jsonObject.addProperty("email", email)


        val callApiService = RetrofitClient.apiInterface.updateProfile(
            userToken,
           jsonObject
        )
        return callApi(callApiService)


    }
/*
fun updateProfile(
        userToken: String,
        fName: String, lName: String, countryCode: String,
        mobileNo: String, address: String, landmark: String, city: String,
        state: String, country: String,country_name_code: String, zipcode: String, latitude: String, longitude: String,
    ): MutableLiveData<LoginResponse> {

        val callApiService = RetrofitClient.apiInterface.updateProfile(
            userToken,
            StaticData.createRequestBody(fName),
            StaticData.createRequestBody(lName),
            StaticData.createRequestBody(countryCode),
            StaticData.createRequestBody(mobileNo),
            StaticData.createRequestBody(address),
            StaticData.createRequestBody(landmark),
            StaticData.createRequestBody(city),
            StaticData.createRequestBody(state),
            StaticData.createRequestBody(country),
            StaticData.createRequestBody(country_name_code),
            StaticData.createRequestBody(zipcode),
            StaticData.createRequestBody(latitude),
            StaticData.createRequestBody(longitude)
        )
        return callApi(callApiService)


    }
*/

    private fun callApi(callApiService: Call<LoginResponse>): MutableLiveData<LoginResponse> {

        setProgressDialog()
        callApiService.enqueue(object : Callback<LoginResponse> {

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                progressDialog.dismissDialog()
                if (response.isSuccessful) {
                    val model: LoginResponse? = response.body()
                    if (model != null) {
                        modelRes.value = model!!
                    } else {
                        showToast(context, Constants.SOMETHING_WENT_WRONG_ERROR)
                        showToast(context, response.message())
                    }
                } else if (response.code() == 401) {
                    StaticData.InvalidSession(context, response.message())

                } else {
                    showToast(
                        context, response.message(),
                    )

                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

                progressDialog.dismissDialog()
                showToast(context, t.message.toString())

            }

        }
        )
        return modelRes;
    }

    private fun setProgressDialog() {

        progressDialog.showProgressDialog(context.getString(R.string.loading))
    }
}


