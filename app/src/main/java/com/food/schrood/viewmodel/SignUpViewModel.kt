package com.food.schrood.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.food.schrood.model.CommonResponse
import com.food.schrood.model.LoginResponse
import com.food.schrood.network.repository.RequestOTPRepository
import com.food.schrood.network.repository.SignUpRepository
import com.food.schrood.network.repository.VerifyOTPRepository


class SignUpViewModel : ViewModel() {


    lateinit var loginResModal: MutableLiveData<LoginResponse>
    lateinit var verifyResModal: MutableLiveData<CommonResponse>
    lateinit var resendOTPResModal: MutableLiveData<CommonResponse>

    fun signUpUser(
        context: Context,
        name: String,   email: String, countryCode: String,
        mobileNo: String, password: String,  device_token: String,
    ): LiveData<LoginResponse> {
        loginResModal = SignUpRepository(context).signUp(name,email,countryCode,mobileNo,password,device_token)
        return loginResModal
    }

    fun verifyUser(context: Context, email: String, otp: String  ): LiveData<CommonResponse> {
        verifyResModal = VerifyOTPRepository(context).verifyOTP(email,otp)
        return verifyResModal
    }

    fun reSendOTP(context: Context, country_code: String, mobile: String): LiveData<CommonResponse> {
        resendOTPResModal = RequestOTPRepository(context).requestOTP(country_code,mobile )
        return resendOTPResModal
    }
}