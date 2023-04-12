package com.education.geno.model

data class CommonResponse(
    val message: String,
    val status: Boolean
) : SuperCastClass()