package com.food.schrood.model

data class CardListResponse(
    val status: Boolean,
    val message: String,
    val STRIPE_KEY: String,
    val STRIPE_SECRET: String,
    val data: ArrayList<CardData>,

    ) : SuperCastClass()

