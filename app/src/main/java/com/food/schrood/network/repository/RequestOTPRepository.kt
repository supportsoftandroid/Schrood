package com.food.schrood.network.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.food.schrood.R
import com.food.schrood.model.CommonResponse
import com.food.schrood.network.RetrofitClient
import com.food.schrood.utility.Constants
import com.food.schrood.utility.DialogManager
import com.food.schrood.utility.StaticData.Companion.showToast
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestOTPRepository(mContext: Context) {
    val modelRes = MutableLiveData<CommonResponse>()
    val progressDialog: DialogManager
    var context: Context

    init {
        context = mContext
        progressDialog = DialogManager(context)
    }

    fun requestOTP(
        country_code: String,
        mobile_number: String,
    ): MutableLiveData<CommonResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("country_code", country_code)
        jsonObject.addProperty("mobile_number", mobile_number)
        val call = RetrofitClient.apiInterface.request_otp(jsonObject)
        setProgressDialog()
        call.enqueue(object : Callback<CommonResponse> {
            override fun onResponse(
                call: Call<CommonResponse>,
                response: Response<CommonResponse>
            ) {
                progressDialog.dismissDialog()
                if (response.isSuccessful) {
                    val model: CommonResponse  = response.body()!!
                    modelRes.value = model
                }else  if (response.code()==401) {
                    showToast(context, response.message(),)
                }else{
                    showToast(context, Constants.SOMETHING_WENT_WRONG_ERROR)
                    showToast(context, response.message(),)
                }


            }

            override fun onFailure(call: Call<CommonResponse>, t: Throwable) {
                progressDialog.dismissDialog()
                showToast(context, t.message.toString())

            }

        }
        )
        return modelRes
    }


    private fun setProgressDialog() {

        progressDialog.showProgressDialog(context.getString(R.string.loading))
    }
}


