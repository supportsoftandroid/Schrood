package com.food.schrood.viewmodel


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.food.schrood.model.WelcomeResponse

import com.food.schrood.network.repository.WelcomeRepository


class WelcomeViewModal : ViewModel() {
      lateinit var resModal: MutableLiveData<WelcomeResponse>
      fun getBannerData(context: Context): LiveData<WelcomeResponse> {
          resModal = WelcomeRepository(context).getWelcomeData()
          return resModal
      }


}