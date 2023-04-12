package com.education.geno.model


import java.io.Serializable

data class TutorItem(
    val id: Int,
    val name: String,
    val l_name: String,
    val about: String,
    val amount: String,
    val exprience: String,
    val image: String,
    val grade_list: ArrayList<ClassItem>,
    val rating: String,
    val student: Int,
    val subject: ArrayList<String>,
    var subjects_list: ArrayList<SubjectItem>
):SuperCastClass(), Serializable
