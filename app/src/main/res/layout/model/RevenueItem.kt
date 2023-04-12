package com.education.geno.model

data class RevenueItem(
    val amount: String,
    var cancel_by: String,
    var cancel_reason: String,
    val contact: String,
    val date: String,
    val day: String,
    val id: String,
    val image: String,
    var is_cancel: String,
    val name: String,
    val push_token: String,
    val rating: String,
    val comment: String,
    val subject: ArrayList<SubjectItem>,
    val time: ArrayList<Time>,
    val type: String,
    val user_id: String
)