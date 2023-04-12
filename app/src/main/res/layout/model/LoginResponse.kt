package com.education.geno.model

data class LoginResponse(

    var `data`: UserDetails,
    val message: String,
    val status: Boolean,

):SuperCastClass()