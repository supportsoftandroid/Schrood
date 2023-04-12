package com.food.schrood.network.Repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.food.schrood.R
import com.food.schrood.model.LoginResponse
import com.food.schrood.network.RetrofitClient
import com.food.schrood.utility.Constants
import com.food.schrood.utility.DialogManager
import com.food.schrood.utility.StaticData.Companion.showToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(
    mContext: Context,
) {
    val modelRes = MutableLiveData<LoginResponse>()
    val progressDialog: DialogManager
    var context: Context

    init {
        context = mContext

        progressDialog = DialogManager(context)

    }

    fun userLogin(
        email: String,
        password: String,
        is_role: String,

        device_token: String): MutableLiveData<LoginResponse> {
        val  call = RetrofitClient.apiInterface.userLogin(email,password,is_role,
            Constants.DEVICE_TYPE,device_token)

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
                }else{
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


