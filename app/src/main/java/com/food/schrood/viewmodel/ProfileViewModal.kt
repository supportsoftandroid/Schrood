package com.food.schrood.viewmodel


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.food.schrood.model.LoginResponse

 import com.food.schrood.network.repository.ProfileRepository


class ProfileViewModal : ViewModel() {
    lateinit var resModal: MutableLiveData<LoginResponse>
    lateinit var resUpdateModal: MutableLiveData<LoginResponse>
     fun getProfile(context: Context, token: String): LiveData<LoginResponse> {
        resModal = ProfileRepository(context).getProfile(token,"get","")
        return resModal
    }
    fun updateProfileImage(context: Context, token: String, imagePath: String): LiveData<LoginResponse> {
        resUpdateModal = ProfileRepository(context).getProfile(token,"update",imagePath)
        return resUpdateModal
    }


}