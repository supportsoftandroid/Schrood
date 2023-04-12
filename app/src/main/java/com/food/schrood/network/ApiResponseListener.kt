package com.food.schrood.network

import com.food.schrood.model.SuperCastClass

interface ApiResponseListener {
    fun onSuccess(fromApi: String?, superCastClass: SuperCastClass?)
    fun onFailure(message: String?)
    fun onException(message: String?)
}