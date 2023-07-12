package com.food.schrood.model

data class WelcomeResponse(
    val `data`: ArrayList<SliderItem>,
    val status: Boolean
) : SuperCastClass()

