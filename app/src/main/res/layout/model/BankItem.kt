package com.education.geno.model

 data class BankItem(
    var account_holder_name: String,
    var bank_name: String,
    var account_number: String,
    var routing_number: String,
    var id_number: String,
    val custom_account_id: String,
    val last4: String,
    val country: String,
    val address_line1: String,
    val address_line2: String,
    val city: String,
    val state: String,
    val postal_code: String,
    val added_on: String
):SuperCastClass()