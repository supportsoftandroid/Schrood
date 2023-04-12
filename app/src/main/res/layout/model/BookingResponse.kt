package com.education.geno.model

data class BookingResponse(
    val status: Boolean,
    val message: String,
    val data: ArrayList<BookingItem>,

):SuperCastClass()

