package com.food.schrood.network.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.food.schrood.R
import com.food.schrood.model.TermsResponse
import com.food.schrood.network.RetrofitClient
import com.food.schrood.utility.Constants
import com.food.schrood.utility.DialogManager
import com.food.schrood.utility.StaticData.Companion.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TermsRepository(
    mContext: Context
) {
    val modelRes = MutableLiveData<TermsResponse>()
    val progressDialog: DialogManager
    var context: Context

    init {
        context = mContext
        progressDialog = DialogManager(context)

    }

    fun getAPIResData(type: String): MutableLiveData<TermsResponse> {
        var call: Call<TermsResponse>? = null
        if (type.equals("AboutApp")) {
            call = RetrofitClient.apiInterface.getAboutApp()
        } else if (type.equals("PrivacyPolicy")) {
            call = RetrofitClient.apiInterface.getPrivacyPolicy()
        } else {
            call = RetrofitClient.apiInterface.getTermsCond()
        }
        return callAPI(call)
    }

    fun callAPI(call: Call<TermsResponse>?): MutableLiveData<TermsResponse> {
        setProgressDialog()
        call?.enqueue(object : Callback<TermsResponse> {


            override fun onResponse(call: Call<TermsResponse>, response: Response<TermsResponse>) {

                progressDialog.dismissDialog()

                val model: TermsResponse? = response.body()

                if (model != null) {
                    modelRes.value = model!!

                } else {
                    showToast(context, Constants.SOMETHING_WENT_WRONG_ERROR)
                    showToast(
                        context, response.message(),
                    )
                }


            }

            override fun onFailure(call: Call<TermsResponse>, t: Throwable) {

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


