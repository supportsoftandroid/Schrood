package com.food.schrood.viewmodel


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.food.schrood.model.CommonResponse
import com.food.schrood.network.repository.RequestOTPRepository
import com.food.schrood.network.repository.ResetPasswordRepository
import com.food.schrood.network.repository.VerifyOTPRepository

class ForgotPasswordViewModel : ViewModel() {

    lateinit var commonResModal: MutableLiveData<CommonResponse>
    lateinit var otpResModal: MutableLiveData<CommonResponse>
    lateinit var resetPassword: MutableLiveData<CommonResponse>


    fun requestOTP(context: Context, country_code: String, phone: String): LiveData<CommonResponse> {
        commonResModal = RequestOTPRepository(context).requestOTP(country_code,phone)
        return commonResModal
    }

    fun verifyOTP(context: Context, email: String, otp: String): LiveData<CommonResponse> {
        otpResModal = VerifyOTPRepository(context).verifyOTP(email, otp)
        return otpResModal
    }

    fun newPassword(context: Context, email: String, password: String): LiveData<CommonResponse> {
        resetPassword = ResetPasswordRepository(context).resetPassword(email, password, password)
        return resetPassword
    }
}