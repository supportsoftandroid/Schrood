package com.food.schrood.model

data class FaqItem(

    var id: String,
    var question: String,
    var answer: String,
    val created_at: String,
    val updated_at: String
) : SuperCastClass()