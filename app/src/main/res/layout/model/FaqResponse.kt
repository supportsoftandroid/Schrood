package com.education.geno.model

data class FaqResponse(
    val `data`: ArrayList<FaqItem>,
    val message: String,
    val status:  Boolean
) : SuperCastClass()

