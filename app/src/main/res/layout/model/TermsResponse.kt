package com.education.geno.model

data class TermsResponse(
    val `data`: String,
    val message: String,
    val status:  Boolean
) : SuperCastClass()

