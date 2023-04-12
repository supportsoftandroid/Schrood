package com.education.geno.model

data class TutorHomeResponse(
    val status: Boolean,
    val today_booking: String,
    val total_booking: String,
    val today_revenue: String,
    val total_revenue: String,
    val data: ArrayList<BookingItem>,


):SuperCastClass()

