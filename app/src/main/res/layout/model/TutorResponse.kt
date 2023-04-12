package com.education.geno.model

data class TutorResponse(
    val `data`: ArrayList<TutorItem>,
    val message: String,
    val status:  Boolean
) : SuperCastClass()

