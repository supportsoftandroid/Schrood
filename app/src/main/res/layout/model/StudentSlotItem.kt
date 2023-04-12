package com.education.geno.model

import java.io.Serializable
data class StudentSlotItem(
    var id: String,
    var day: String,
    var status: Boolean,
    var is_book: Boolean,
    var time: String,
    var changetime: String,
    var type: String
):SuperCastClass(), Serializable