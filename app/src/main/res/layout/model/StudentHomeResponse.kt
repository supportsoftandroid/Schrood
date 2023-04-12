package com.education.geno.model

data class StudentHomeResponse(
    val status: Boolean,
    val subjects: ArrayList<SubjectItem>,
    val near_by_tutor: ArrayList<TutorItem>,
):SuperCastClass()

