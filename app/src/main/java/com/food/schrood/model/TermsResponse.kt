package com.food.schrood.model

data class TermsResponse(
    val `data`: String,
    val message: String,
    val status: Boolean
) : SuperCastClass()

