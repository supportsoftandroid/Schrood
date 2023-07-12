package com.food.schrood.network.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.food.schrood.R
import com.food.schrood.model.WelcomeResponse
import com.food.schrood.network.RetrofitClient
import com.food.schrood.utility.Constants
import com.food.schrood.utility.DialogManager
import com.food.schrood.utility.StaticData
import com.food.schrood.utility.StaticData.Companion.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeRepository(
    mContext: Context
) {
    val welcomeResponse = MutableLiveData<WelcomeResponse>()
    val progressDialog: DialogManager
    var context: Context

    init {
        context = mContext
        progressDialog = DialogManager(context)
    }

    fun getWelcomeData(): MutableLiveData<WelcomeResponse> {
        val call = RetrofitClient.apiInterface.getWelcome()
        // setProgressDialog()
        call.enqueue(object : Callback<WelcomeResponse> {
            override fun onResponse(
                call: Call<WelcomeResponse>,
                response: Response<WelcomeResponse>
            ) {
                progressDialog.dismissDialog()
                val model: WelcomeResponse? = response.body()
                StaticData.printLog("welcome repository", model.toString())
                if (model != null) {
                    welcomeResponse.value = model!!
                } else {
                    showToast(context, Constants.SOMETHING_WENT_WRONG_ERROR)
                    showToast(
                        context, response.message(),
                    )
                }
            }

            override fun onFailure(call: Call<WelcomeResponse>, t: Throwable) {
                progressDialog.dismissDialog()
                showToast(context, t.message.toString())
            }
        })
        return welcomeResponse;
    }

    private fun setProgressDialog() {
        progressDialog.showProgressDialog(context.getString(R.string.loading))
    }
}


