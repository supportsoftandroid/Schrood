package com.food.schrood.viewmodel


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.food.schrood.model.CommonResponse
import com.food.schrood.model.LoginResponse
import com.food.schrood.network.repository.LoginRepository
import com.food.schrood.network.repository.RequestOTPRepository
import com.food.schrood.network.repository.ResetPasswordRepository
import com.food.schrood.network.repository.VerifyOTPRepository

class LoginViewModel : ViewModel() {
    lateinit var loginResModal: MutableLiveData<LoginResponse>
    lateinit var commonResModal: MutableLiveData<CommonResponse>
    lateinit var otpResModal: MutableLiveData<CommonResponse>
    lateinit var resetPassword: MutableLiveData<CommonResponse>
    fun getLogInData(
        context: Context,
        mobile: String,
        country_code: String,
        password: String,

        device_token: String
    ): LiveData<LoginResponse> {
        loginResModal = LoginRepository(context).userLogin(mobile, country_code, password, device_token)
        return loginResModal
    }

    fun forgotPassword(context: Context, country_code: String, mobile: String): LiveData<CommonResponse> {
        commonResModal = RequestOTPRepository(context).requestOTP(country_code,mobile)
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