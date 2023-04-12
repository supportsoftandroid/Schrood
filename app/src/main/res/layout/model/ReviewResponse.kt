package com.education.geno.model

data class ReviewResponse(
    val status: Boolean,
    val message: String,
    val data: ArrayList<ReviewItem>,

):SuperCastClass()

