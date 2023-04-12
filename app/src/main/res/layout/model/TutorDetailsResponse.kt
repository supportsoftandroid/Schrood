package com.education.geno.model

data class TutorDetailsResponse(
    val status:  Boolean,
    val message: String,
    val `data`: TutorItem,
) : SuperCastClass()

