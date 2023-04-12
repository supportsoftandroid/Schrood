package com.education.geno.model

data class NotificationResponse(
    val status: Boolean,
    val message: String,
    val data: ArrayList<NotificationItem>,

):SuperCastClass()

