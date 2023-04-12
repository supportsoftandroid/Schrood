package com.education.geno.model

data class BookingDetailsResponse(
    val status: Boolean,
    val message: String,
    val data: BookingItem,

):SuperCastClass()

