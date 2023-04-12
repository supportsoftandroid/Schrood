package com.education.geno.model

import java.io.Serializable

data class DayofMonthItem(
    val id: String,
    var day: String,
    val date: String,
):SuperCastClass(), Serializable