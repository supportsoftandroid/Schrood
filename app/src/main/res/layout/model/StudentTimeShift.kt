package com.education.geno.model

data class StudentTimeShift(
    val name: String,
    var timeslotlist: ArrayList<StudentSlotItem>
)