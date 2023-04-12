package com.education.geno.model

data class CardResponse(
    val status: Boolean,
    val message: String,
    val STRIPE_KEY: String,
    val STRIPE_SECRET: String,
    val data: CardData,

):SuperCastClass()

