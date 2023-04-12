package com.education.geno.model

data class OtpResponse(
    val `data`: OtpData,
    val message: String,
    val status: Boolean
) : SuperCastClass()
    data class OtpData(
    val reset_password_token: String
)