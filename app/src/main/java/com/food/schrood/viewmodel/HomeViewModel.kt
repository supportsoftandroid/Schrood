package com.food.schrood.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.food.schrood.model.CommonResponse
import com.food.schrood.network.repository.RequestOTPRepository


class HomeViewModel : ViewModel() {
    lateinit var commonResModal: MutableLiveData<CommonResponse>
    fun getHomeData(context: Context,country_code: String,
                    mobile_number: String,): LiveData<CommonResponse> {
        commonResModal = RequestOTPRepository(context).requestOTP(country_code,mobile_number)
        return commonResModal
    }

}