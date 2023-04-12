package com.education.geno.model

data class RevenueResponse(
    val status: Boolean,
    val message: String,
    val data: ArrayList<RevenueItem>,

):SuperCastClass()

