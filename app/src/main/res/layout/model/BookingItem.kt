package com.education.geno.model

 data class BookingItem(
    val amount: String,
    val address: String,
    var cancel_by: String,
    var cancel_reason: String,
    val contact: String,
    val date: String,
    val day: String,
    var id: String,
    val image: String,
    var is_cancel: String,
    val name: String,
    val device_type: String,
    val push_token: String,
    val rating: String,
    val comment: String,
    val subject: ArrayList<SubjectItem>,
    val time: ArrayList<Time>,
    val type: String,
    val user_id: String
):SuperCastClass(),java.io.Serializable {

    constructor() : this("","","","","","","","","","","",
        "","","","", ArrayList(),ArrayList(),"","", )
 }

data class Time(
    val timevalue: String
):SuperCastClass(),java.io.Serializable