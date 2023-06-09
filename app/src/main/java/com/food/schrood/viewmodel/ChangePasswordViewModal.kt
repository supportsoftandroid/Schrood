package com.food.schrood.viewmodel


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.food.schrood.model.CommonResponse

 import com.food.schrood.network.repository.ChangePasswordRepository


class ChangePasswordViewModal : ViewModel() {
    lateinit var resModal: MutableLiveData<CommonResponse>
      fun changePassword(context: Context, old_password: String, new_password: String, token: String): LiveData<CommonResponse> {
         resModal = ChangePasswordRepository(context).changePassword(token,old_password,new_password)
         return resModal
     }


}