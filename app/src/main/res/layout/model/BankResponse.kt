package com.education.geno.model

data class BankResponse(
    val status: Boolean,
    val message: String,
    val `data`: BankItem,
) : SuperCastClass()
