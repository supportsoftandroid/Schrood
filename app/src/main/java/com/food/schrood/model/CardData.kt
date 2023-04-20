package com.food.schrood.model

data class CardData(
    val brand: String,
    val name: String,
    val exp_month: Int,
    val exp_year: Int,
    val id: Int,
    val card_token: String,
    val status: String,
    val last4: Int,
    var isSelected: Boolean = false
)