package com.food.schrood.model

data class CardResponse(
    val status: Boolean,
    val message: String,
    val STRIPE_KEY: String,
    val STRIPE_SECRET: String,
    val data: CardData,

):SuperCastClass()

