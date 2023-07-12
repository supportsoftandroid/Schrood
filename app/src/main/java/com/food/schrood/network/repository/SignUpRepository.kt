package com.food.schrood.network.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.food.schrood.R
import com.food.schrood.model.LoginResponse
import com.food.schrood.network.RetrofitClient
import com.food.schrood.utility.Constants
import com.food.schrood.utility.DialogManager
import com.food.schrood.utility.StaticData.Companion.showToast
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRepository(
    mContext: Context,
) {
    val modelRes = MutableLiveData<LoginResponse>()
    val progressDialog: DialogManager
    var context: Context

    init {
        context = mContext

        progressDialog = DialogManager(context)

    }

    fun signUp(
        name: String,
        email: String,
        mobile: String,
        country_code: String,
        password: String,
        device_token: String

    ): MutableLiveData<LoginResponse> {
        val jsonObject = JsonObject()
        jsonObject.addProperty("name", name)
        jsonObject.addProperty("email", email)
        jsonObject.addProperty("country_code", country_code)
        jsonObject.addProperty("mobile_number", mobile)
        jsonObject.addProperty("password", password)
        jsonObject.addProperty("device_type",  Constants.DEVICE_TYPE)
        jsonObject.addProperty("push_token", device_token)
        val call = RetrofitClient.apiInterface.signup(
            jsonObject

        )

        setProgressDialog()
        call.enqueue(object : Callback<LoginResponse> {


            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                progressDialog.dismissDialog()
                if (response.isSuccessful) {
                    val model: LoginResponse? = response.body()
                    if (model != null) {
                        modelRes.value = model!!
                    } else {
                        showToast(context, Constants.SOMETHING_WENT_WRONG_ERROR)
                        showToast(
                            context, response.message(),
                        )
                    }
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


