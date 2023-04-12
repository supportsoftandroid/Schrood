package com.food.schrood.model

data class CommonResponse(
    val message: String,
    val status: Boolean
) : SuperCastClass()