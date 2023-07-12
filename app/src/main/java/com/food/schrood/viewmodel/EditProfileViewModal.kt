package com.food.schrood.viewmodel


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.food.schrood.model.LoginResponse
import com.food.schrood.network.repository.ProfileRepository

class EditProfileViewModal : ViewModel() {

    lateinit var resUpdateModal: MutableLiveData<LoginResponse>
    fun updateProfile(
        context: Context, token: String, fName: String, countryCode: String,
        mobileNo: String, email: String ): LiveData<LoginResponse> {
        resUpdateModal = ProfileRepository(context).updateProfile(
            token, fName, countryCode, mobileNo, email)
        return resUpdateModal
    }

}