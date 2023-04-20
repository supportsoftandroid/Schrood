package com.food.schrood.model

data class LoginResponse(

    var `data`: UserDetails,
    val message: String,
    val status: Boolean,

    )