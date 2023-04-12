package com.education.geno.model

data class SlotDayDetailsItem(
    val status: Boolean,
    val day: String,
    val date: String,
    var mark_same_day: String,
    var contact_type: String,
    val `days`: ArrayList<DayofMonthItem>,
    var `data`: ArrayList<TimeShift>,
)